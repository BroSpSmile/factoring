/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.loan.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smile.start.commons.DateUtil;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.LoanDao;
import com.smile.start.dao.ProjectItemDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.common.FileInfo;
import com.smile.start.model.enums.LoanType;
import com.smile.start.model.enums.ProjectItemType;
import com.smile.start.model.loan.Loan;
import com.smile.start.model.loan.LoanGroup;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.common.FileService;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.loan.LoanService;
import com.smile.start.service.project.ProjectService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: LoanServiceImpl.java, v 0.1 Feb 27, 2019 9:08:51 PM smile.jing Exp $
 */
@Service
public class LoanServiceImpl extends AbstractService implements LoanService {

    /** loadDao */
    @Resource
    private LoanDao        loanDao;

    /** projectService */
    @Resource
    private ProjectService projectService;

    /** processEngine */
    @Resource
    private ProcessEngine  processEngine;

    /** auditService */
    @Resource
    private AuditService   auditService;

    /** projectItemDao */
    @Resource
    private ProjectItemDao projectItemDao;

    /** fileService */
    @Resource
    private FileService    fileService;

    /** 
     * @see com.smile.start.service.loan.LoanService#save(com.smile.start.model.loan.Loan)
     */
    @Override
    @Transactional
    public BaseResult save(Loan loan) {
        Loan old = loanDao.getByProject(loan.getProject().getId());
        if (null == old) {
            loanDao.insert(loan);
        } else {
            loan.setId(old.getId());
            loanDao.update(loan);
        }
        //线下保存附件
        if (LoanType.OFFLINE.equals(loan.getType())) {
            projectItemDao.deleteItems(loan.getProject().getId(), ProjectItemType.LOAN);
            for (ProjectItem item : loan.getProject().getItems()) {
                projectItemDao.insert(item);
            }
        } else {
            loanDao.deleteItem(loan.getId());
            for (LoanGroup group : loan.getGroups()) {
                group.setLoanId(loan.getId());
                loanDao.insertItem(group);
            }
            try {
                saveFile(loan);
            } catch (IOException e) {
                LoggerUtils.error(logger, "生成放款文件失败", e);
            }
        }
        return new BaseResult();
    }

    /** 
     * @see com.smile.start.service.loan.LoanService#commit(com.smile.start.model.loan.Loan)
     */
    @Override
    @Transactional
    public BaseResult commit(Loan loan) {
        BaseResult result = this.save(loan);
        if (result.isSuccess()) {
            Project project = loan.getProject();
            project.setItems(Collections.emptyList());
            project.setStep(7);
            processEngine.next(project, true);
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.loan.LoanService#getLoan(com.smile.start.model.project.Project)
     */
    @Override
    public Loan getLoan(Project project) {
        Loan loan = loanDao.getByProject(project.getId());
        if (null != loan) {
            List<LoanGroup> groups = loanDao.getByLoan(loan.getId());
            loan.setGroups(groups);
        }
        return loan;
    }

    /**
     * 创建线上申请文件
     * @param loan
     * @return
     * @throws IOException 
     */
    private BaseResult saveFile(Loan loan) throws IOException {
        Project project = projectService.getProject(loan.getProject().getId());
        String fileName = project.getProjectId() + "保理项目线上放款申请";
        XWPFDocument document = new XWPFDocument();
        //文档标题
        createParagraph(document, ParagraphAlignment.CENTER, "项目付款申请表", 20, true);
        document.createParagraph();
        createParagraph(document, ParagraphAlignment.RIGHT, "日期:" + DateUtil.getWebDateString(loan.getCreateTime()), 12, true);
        //移交清单表格
        XWPFTable comTable = document.createTable();

        XWPFTableRow first = comTable.createRow();
        first.getCell(0).setText("申请部门");
        first.addNewTableCell().setText(loan.getDepartment());
        first.addNewTableCell().setText("申请人");
        first.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        first.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        first.getCell(3).setText(loan.getUser());
        XWPFTableRow second = comTable.createRow();
        second.getCell(0).setText("项目名称");
        second.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        second.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        second.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        second.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        second.getCell(1).setText(project.getProjectName());
        document.createParagraph();
        XWPFTableRow row3 = comTable.createRow();
        row3.getCell(0).setText("认缴投资总额");
        row3.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        row3.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row3.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row3.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row3.getCell(1).setText("￥"+loan.getSubscriptionAmount()+"元");
        XWPFTableRow row4 = comTable.createRow();
        row4.getCell(0).setText("本次付款金额");
        row4.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        row4.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row4.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row4.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row4.getCell(1).setText("￥"+loan.getPayments()+"元");
        XWPFTableRow row5 = comTable.createRow();
        row5.getCell(0).setText("金额（大写）人民币");
        row5.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        row5.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row5.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row5.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row5.getCell(1).setText("￥"+loan.getChineseAmount()+"元");
        XWPFTableRow row6 = comTable.createRow();
        row6.getCell(0).setText("累计付款金额");
        row6.addNewTableCell().setText(loan.getAccumulativeyments()+"元");
        row6.addNewTableCell().setText("未付款金额");
        row6.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        row6.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row6.getCell(3).setText(loan.getUnpaid()+"元");
        XWPFTableRow row7 = comTable.createRow();
        row7.getCell(0).setText("付款用途");
        row7.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        row7.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row7.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row7.addNewTableCell().getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        row7.getCell(1).setText(loan.getPaymentPurpose());
        for (LoanGroup group : loan.getGroups()) {
            document.createParagraph();
            createParagraph(document, "收款方", 14);
            createParagraph(document, "单位名称:" + group.getPayeeName());
            createParagraph(document, "开户银行:" + group.getPayeeBankName());
            createParagraph(document, "银行账号:" + group.getPayeeAccountNo());
            createParagraph(document, "出资方", 14);
            createParagraph(document, "单位名称:" + group.getPayerName());
            createParagraph(document, "开户银行:" + group.getPayerBankName());
            createParagraph(document, "银行账号:" + group.getPayerAccountNo());
            createParagraph(document, "付款金额:" + group.getPayments());
        }

        File tempFile = File.createTempFile("temp", fileName);
        FileOutputStream out = new FileOutputStream(tempFile);
        document.write(out);
        out.close();
        upload(tempFile, fileName + ".docx", project.getId(), ProjectItemType.LOAN);
        return new BaseResult();
    }

    private void createParagraph(XWPFDocument document, String doc) {
        createParagraph(document, ParagraphAlignment.LEFT, doc);
    }

    private void createParagraph(XWPFDocument document, String doc, int size) {
        createParagraph(document, ParagraphAlignment.LEFT, doc, size, false);
    }

    private void createParagraph(XWPFDocument document, ParagraphAlignment alignment, String doc) {
        createParagraph(document, alignment, doc, 12, false);
    }

    private void createParagraph(XWPFDocument document, ParagraphAlignment alignment, String doc, int size, boolean bold) {
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(alignment);
        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setBold(bold);
        titleParagraphRun.setText(doc);
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(size);
    }

    /**
     * 标准合同文件上传并保存数据库
     * @param file
     * @param fileName
     */
    private void upload(File file, String fileName, Long projectId, ProjectItemType projectItemType) {
        if (file.exists()) {
            projectItemDao.deleteItems(projectId, projectItemType);
            final FileInfo upload = fileService.upload(file, fileName);
            ProjectItem projectItem = new ProjectItem();
            projectItem.setProjectId(projectId);
            projectItem.setItemType(projectItemType);
            projectItem.setItemName(fileName);
            projectItem.setItemValue(upload.getFileId());
            projectItemDao.insert(projectItem);
        }
    }
}
