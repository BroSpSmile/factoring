/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.finance.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.dao.FactoringDetailDao;
import com.smile.start.dao.InstallmentDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.*;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.FactoringDetail;
import com.smile.start.model.project.Installment;
import com.smile.start.model.project.InstallmentDetail;
import com.smile.start.model.project.Project;
import com.smile.start.service.AbstractService;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.finance.FinanceService;
import com.smile.start.service.fund.FundService;
import com.smile.start.service.project.FactoringService;
import com.smile.start.service.project.ProjectService;

/**
 * 实现
 *
 * @author Jacky
 * @version $Id: FinanceServiceImpl.java, v 0.1 2019年3月6日 下午3:26:32 Jacky Exp $
 */
@Service
public class FinanceServiceImpl extends AbstractService implements FinanceService {

    /**  */
    @Resource
    private InstallmentDao     installmentDao;

    /** factoringService */
    @Resource
    private FactoringService   factoringService;

    @Resource
    private ProjectService     projectService;

    /** 直投服务 */
    @Resource
    private FundService        fundService;

    /**
     * 项目DAO
     */
    @Resource
    private FactoringDetailDao factoringDetailDao;

    /**
     * 流程引擎
     */
    @Resource
    private ProcessEngine      processEngine;

    /**
     * 保存放款信息
     *
     * @param installment
     * @return
     */
    @Override
    public Long saveLoanInstallment(Installment installment) {
        installment.setType(InstallmentType.LOAN);
        return installmentDao.insert(installment);
    }

    /**
     * @see com.smile.start.service.project.ProjectService#queryPage(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<Project> queryPageProject(PageRequest<Project> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), "id desc");
        PageInfo<Project> projects = projectService.queryPage(page);
        projects.getList().stream().forEach(project -> factoringService.setInstallments(project.getDetail()));
        //4. 根据返回的集合，创建PageInfo对象
        return projects;
    }

    /**
     * 保存分期信息
     *
     * @param project
     * @return
     */
    @Override
    @Transactional
    public BaseResult saveInstallments(Project project, InstallmentType installmentType) {
        FactoringDetail detail = Optional.of(project.getDetail()).orElseThrow(() -> new RuntimeException("保存分期信息失败，获取项目详情败!"));

        //放款操作中
        if (project.getStep() == Step.LOANEN.getIndex()) {
            processEngine.changeStatus(project, StepStatus.PROCESSING);
        }

        //回款操作中
        if (project.getStep() == Step.END.getIndex()) {
            processEngine.changeStatus(project, StepStatus.PROCESSING);
        }

        List<Installment> installments = null;
        if (installmentType == InstallmentType.LOAN) {
            installments = detail.getLoanInstallments();
        } else if (installmentType == InstallmentType.RETURN) {
            installments = detail.getReturnInstallments();
        } else if (installmentType == InstallmentType.FACTORING) {
            installments = detail.getFactoringInstallments();
        }
        BaseResult result = new BaseResult();
        for (Installment installment : installments) {
            installment.setType(installmentType);
            installment.setDetail(project.getDetail());
            installment.setKind(project.getKind());
            result = this.save(installment);
            if (result.isSuccess() && installmentType == InstallmentType.FACTORING) {
                List<InstallmentDetail> detailList = installment.getDetailList();
                if (!CollectionUtils.isEmpty(detailList) && detailList.size() > 0) {
                    result = saveInstallmentDetails(detailList, installment.getId());
                }
            }
            if (!result.isSuccess()) {
                throw new RuntimeException("保存" + installmentType.getDesc() + "信息失败!");
            }
        }

        if (result.isSuccess()) {
            financeOperate(project);
        }
        return result;
    }

    /**
     * 保存直投放款信息
     *
     * @param target
     * @return
     */
    @Override
    public BaseResult saveFundInstallments(FundTarget target) {
        BaseResult result = new BaseResult();
        Double paid = target.getLoanInstallments().stream().mapToDouble(Installment::getAmount).sum();
        //放款金额大于投资金额转换状态
        if (paid >= target.getInvestment()) {
            target.setProjectStep(FundStatus.FILE);
            result = fundService.modifyTarget(target);
        }
        if (result.isSuccess()) {
            for (Installment installment : target.getLoanInstallments()) {
                installment.setType(InstallmentType.LOAN);
                FactoringDetail detail = new FactoringDetail();
                detail.setId(target.getId());
                installment.setDetail(detail);
                installment.setKind(ProjectKind.INVESTMENT);
                result = save(installment);
            }
        }
        return result;
    }

    /**
     * 保存
     * @param installment
     * @return
     */
    private BaseResult save(Installment installment) {
        long effect = 0L;
        long itemEffect = 0;
        if (null != installment.getId() && installment.getId() <= -1L) {
            //ADD
            effect = installmentDao.insert(installment);
            if (null != installment.getItem()) {
                installment.getItem().setInstallmentId(installment.getId());
                itemEffect = installmentDao.insertInstallmentItem(installment.getItem());
            }
        } else {
            //UPDATE
            effect = installmentDao.update(installment);
            itemEffect = installmentDao.deleteInstallmentItem(installment);

            if (null != installment.getItem()) {
                installment.getItem().setInstallmentId(installment.getId());
                if (itemEffect > 0) {
                    itemEffect = installmentDao.insertInstallmentItem(installment.getItem());
                }
            }
        }
        BaseResult result = new BaseResult();
        if (effect < 0 || itemEffect < 0) {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 保存分期明细信息
     *
     * @param detailList
     * @return
     */
    private BaseResult saveInstallmentDetails(List<InstallmentDetail> detailList, Long id) {
        if (!CollectionUtils.isEmpty(detailList) && detailList.size() > 0) {
            for (InstallmentDetail detail : detailList) {
                detail.setInstallmentId(id);
                long effect = 0;
                long itemEffect = 0;
                if (null != detail.getId() && detail.getId() <= -1L) {
                    //ADD
                    effect = installmentDao.insertInstallmentDetail(detail);
                    if (null != detail.getItem()) {
                        detail.getItem().setInstallmentDetailId(detail.getId());
                        itemEffect = installmentDao.insertInstallmentDetailItem(detail.getItem());
                    }
                } else {
                    //UPDATE
                    effect = installmentDao.updateInstallmentDetail(detail);
                    itemEffect = installmentDao.deleteInstallmentDetailItem(detail);
                    if (null != detail.getItem()) {
                        if (itemEffect >= 0) {
                            detail.getItem().setInstallmentDetailId(detail.getId());
                            itemEffect = installmentDao.insertInstallmentDetailItem(detail.getItem());
                        }
                    }
                }

                if (effect < 0 || itemEffect < 0) {
                    throw new RuntimeException("保存分期明细信息失败!");
                }
            }
        }
        return new BaseResult();
    }

    /**
     * 财务流程流转
     *
     * @param project
     * @return
     */
    private void financeOperate(Project project) {
        FactoringDetail detail = Optional.ofNullable(project.getDetail()).orElseThrow(() -> new RuntimeException("保存分期信息失败，获取项目详情败!"));
        //放款金额总计
        double totalLoanAmount = detail.getLoanInstallments().stream().mapToDouble(installment -> installment.getAmount()).sum();

        //回款金额总计
        double totalReturnAmount = detail.getReturnInstallments().stream().mapToDouble(installment -> installment.getAmount()).sum();

        //detail表里没有回款金额，不更新
        detail.setDropAmount(totalLoanAmount);
        factoringDetailDao.update(detail);

        List<Installment> factoringInstallments = detail.getFactoringInstallments();

        factoringInstallments.stream().mapToDouble(installment -> installment.getAmount()).sum();
        boolean isAllPaied = true;
        for (Installment installment : factoringInstallments) {
            double totalInvoiceAmount = installment.getDetailList().stream()
                .mapToDouble(installmentDetail -> installmentDetail.getType() == InstallmentDetailType.INVOICE ? installmentDetail.getDetailAmount() : 0).sum();
            if (totalInvoiceAmount >= installment.getAmount()) {
                installment.setInvoiced(true);
            }
            double totalPaymentAmount = installment.getDetailList().stream()
                .mapToDouble(installmentDetail -> installmentDetail.getType() == InstallmentDetailType.PAYMENT ? installmentDetail.getDetailAmount() : 0).sum();
            if (totalPaymentAmount >= installment.getAmount()) {
                installment.setPaied(true);
            }
            isAllPaied = isAllPaied & installment.isPaied();
            installmentDao.update(installment);
        }

        //放款操作
        if (project.getStep() == Step.LOANEN.getIndex()) {
            if (totalLoanAmount >= detail.getReceivable()) {
                //放款完成，更新step表,更新项目表
                processEngine.next(project, false);
            }
        }

        //回款操作
        if (project.getStep() == Step.BACK.getIndex()) {
            if (totalReturnAmount >= totalLoanAmount && isAllPaied) {
                //回款完成，更新step表,更新项目表
                processEngine.next(project, false);
            }
        }
    }
}
