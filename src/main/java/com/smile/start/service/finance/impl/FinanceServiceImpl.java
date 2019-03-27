/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.finance.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.DateUtil;
import com.smile.start.dao.FactoringDetailDao;
import com.smile.start.dao.InstallmentDao;
import com.smile.start.event.AuditEvent;
import com.smile.start.event.InstallmentEvent;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.*;
import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.project.*;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.smile.start.service.AbstractService;
import com.smile.start.service.finance.FinanceService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 实现
 *
 * @author Jacky
 * @version $Id: FinanceServiceImpl.java, v 0.1 2019年3月6日 下午3:26:32 Jacky Exp $
 */
@Service
public class FinanceServiceImpl extends AbstractService implements FinanceService {

    @Resource
    private InstallmentDao installmentDao;

    @Resource
    private ProjectService projectService;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 项目DAO
     */
    @Resource
    private FactoringDetailDao factoringDetailDao;

    /**
     * 流程引擎
     */
    @Resource
    private ProcessEngine processEngine;

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
        List<Project> projects = installmentDao.findByParamProject(page.getCondition());
        projects.stream().forEach(project -> projectService.setDetail(project));
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<Project> result = new PageInfo<>(projects);
        return result;
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
        FactoringDetail detail =
            Optional.of(project.getDetail()).orElseThrow(() -> new RuntimeException("保存分期信息失败，获取项目详情败!"));

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
            installments = detail.getLoanInstallments();
        } else if (installmentType == InstallmentType.FACTORING) {
            installments = detail.getFactoringInstallments();
        }
        BaseResult result = new BaseResult();
        for (Installment installment : installments) {
            installment.setType(installmentType);
            installment.setDetail(project.getDetail());
            long effect = 0;
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
            if (installmentType == InstallmentType.FACTORING) {
                List<InstallmentDetail> detailList = installment.getDetailList();
                if (!CollectionUtils.isEmpty(detailList) && detailList.size() > 0) {
                    result = saveInstallmentDetails(detailList, installment.getId());
                }
            }

            if (effect < 0 || itemEffect < 0 || !result.isSuccess()) {
                throw new RuntimeException("保存" + installmentType.getDesc() + "信息失败!");
            }
        }

        if (result.isSuccess()) {
            //applicationContext.publishEvent(new InstallmentEvent(this, project));
            financeOperate(project);
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
        FactoringDetail detail =
            Optional.ofNullable(project.getDetail()).orElseThrow(() -> new RuntimeException("保存分期信息失败，获取项目详情败!"));
        //放款金额总计
        double totalLoanAmount =
            detail.getLoanInstallments().stream().map(installment -> installment.getAmount()).count();

        //回款金额总计
        double totalReturnAmount =
            detail.getReturnInstallments().stream().map(installment -> installment.getAmount()).count();

        //detail表里没有回款金额，不更新
        detail.setDropAmount(totalLoanAmount);
        factoringDetailDao.update(detail);

        List<Installment> factoringInstallments = detail.getFactoringInstallments();

        //保理分期金额总计
        double totalFactoringAmount =
            factoringInstallments.stream().map(installment -> installment.getAmount()).count();
        boolean isAllPaied = true;
        for (Installment installment : factoringInstallments) {
            double totalInvoiceAmount = installment.getDetailList()
                .stream()
                .map(installmentDetail -> installmentDetail.getType() == InstallmentDetailType.INVOICE ?
                    installmentDetail.getDetailAmount() :
                    0)
                .count();
            if (totalInvoiceAmount >= installment.getAmount()) {
                installment.setInvoiced(true);
            }
            double totalPaymentAmount = installment.getDetailList()
                .stream()
                .map(installmentDetail -> installmentDetail.getType() == InstallmentDetailType.PAYMENT ?
                    installmentDetail.getDetailAmount() :
                    0)
                .count();
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
        if (project.getStep() == Step.END.getIndex()) {
            if (totalReturnAmount >= totalLoanAmount && isAllPaied) {
                //回款完成，更新step表,更新项目表
                processEngine.changeStatus(project, StepStatus.COMPLETED);
            }
        }
    }
}
