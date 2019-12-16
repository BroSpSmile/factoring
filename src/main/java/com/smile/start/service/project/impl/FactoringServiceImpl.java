/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.smile.start.commons.DateUtil;
import com.smile.start.dao.FactoringDetailDao;
import com.smile.start.dao.InstallmentDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.InstallmentType;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.enums.ProjectKind;
import com.smile.start.model.project.*;
import com.smile.start.service.AbstractService;
import com.smile.start.service.project.FactoringService;
import com.smile.start.service.project.ProjectService;

/**
 * 保理项目服务
 * @author smile.jing
 * @version $Id: FactoringServiceImpl.java, v 0.1 Feb 24, 2019 7:28:26 PM smile.jing Exp $
 */
@Service
public class FactoringServiceImpl extends AbstractService implements FactoringService {

    /** 项目服务 */
    @Resource
    private ProjectService     projectService;

    /** factoringDetailDao */
    @Resource
    private FactoringDetailDao factoringDetailDao;

    /** installmentDao */
    @Resource
    private InstallmentDao     installmentDao;

    /** 
     * @see com.smile.start.service.project.FactoringService#create(com.smile.start.model.project.FactoringDetail)
     */
    @Override
    @Transactional
    public BaseResult create(FactoringDetail detail) {
        Project project = detail.getProject();
        project.setKind(ProjectKind.FACTORING);
        project.setProgress(Progress.INIT);
        BaseResult result = projectService.initProject(detail.getProject());
        if (result.isSuccess()) {
            factoringDetailDao.insert(detail);
            result = updateInstallments(detail, detail.getFactoringInstallments());
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.project.FactoringService#modify(com.smile.start.model.project.FactoringDetail)
     */
    @Override
    @Transactional
    public BaseResult modify(FactoringDetail detail) {
        BaseResult result = projectService.updateProject(detail.getProject());
        if (result.isSuccess()) {
            factoringDetailDao.update(detail);
            result = updateInstallments(detail, detail.getFactoringInstallments());
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.project.FactoringService#get(java.lang.Long)
     */
    @Override
    public FactoringDetail get(Long projectId) {
        FactoringDetail detail = factoringDetailDao.getByProject(projectId);
        this.setInstallmentsDetail(detail);
        return detail;
    }

    /** 
     * @see com.smile.start.service.project.FactoringService#getInfos()
     */
    @Override
    public List<FactoringsInfo> getInfos() {
        return factoringDetailDao.factoringsInfos();
    }

    /** 
     * @see com.smile.start.service.project.FactoringService#getProfit(java.lang.String)
     */
    @Override
    public Profit getProfit(String month) {
        Profit profit = new Profit();
        Double monthDouble = factoringDetailDao.monthProfit(month);
        profit.setMonthProfit(monthDouble == null ? 0.00 : monthDouble);
        Double yearDouble = factoringDetailDao.yearProfit(month.split("-")[0]);
        profit.setYearProfit(yearDouble == null ? 0.00 : yearDouble);
        return profit;
    }

    /**
     * @see com.smile.start.service.project.FactoringService#getAssets(java.lang.String)
     */
    @Override
    public List<Assets> getAssets(String month) {
        List<Assets> assetsList = Lists.newArrayListWithCapacity(3);
        Assets receivable = new Assets();
        receivable.setZcamount(formatDouble(factoringDetailDao.getNowReceivable(getYear(), getMonthLastDay(month))));
        //  receivable.setZctotalamount(formatDouble(factoringDetailDao.getLastReceivable(getYear())));
        assetsList.add(receivable);
        Assets shotFactoring = new Assets();
        shotFactoring.setZcamount(formatDouble(factoringDetailDao.getShotNowFactoringFee(getYear(), getMonthLastDay(month))));
        shotFactoring.setZctotalamount(formatDouble(factoringDetailDao.getShotLastFactoringFee(DateUtil.format(getYear(), "yyyy-MM-dd"))));
        assetsList.add(shotFactoring);
        Assets longFactoring = new Assets();
        longFactoring.setZcamount(formatDouble(factoringDetailDao.getLongNowFactoringFee(getYear(), getMonthLastDay(month))));
        longFactoring.setZctotalamount(formatDouble(factoringDetailDao.getLongLastFactoringFee(getYear())));
        assetsList.add(longFactoring);
        return assetsList;
    }

    private double formatDouble(Double amount) {
        if (null == amount) {
            return 0.0d;
        }
        return amount;
    }

    /** 
     * @see com.smile.start.service.project.FactoringService#
     */
    @Override
    public void setInstallments(FactoringDetail detail) {
        if (null != detail) {
            List<Installment> installments = installmentDao.queryByDetail(detail.getId(), ProjectKind.FACTORING);
            for (Installment installment : installments) {
                if (InstallmentType.FACTORING.equals(installment.getType())) {
                    detail.addFactoringInstallment(installment);
                }
                if (InstallmentType.RETURN.equals(installment.getType())) {
                    detail.addReturnInstallment(installment);
                }
                if (InstallmentType.LOAN.equals(installment.getType())) {
                    detail.addLoanInstallment(installment);
                }
            }
        }
    }

    /** 
     * @see com.smile.start.service.project.FactoringService#setInstallmentsDetail(com.smile.start.model.project.FactoringDetail)
     */
    @Override
    public void setInstallmentsDetail(FactoringDetail detail) {
        if (null != detail) {
            List<Installment> installments = installmentDao.queryByDetail(detail.getId(), ProjectKind.FACTORING);
            for (Installment installment : installments) {
                InstallmentItem installmentItem = installmentDao.getInstallmentItem(installment);
                installment.setItem(installmentItem);
                installment.setDetailList(installmentDao.getInstallmentDetail(installment.getId()));
                for (InstallmentDetail installmentDetail : installment.getDetailList()) {
                    installmentDetail.setItem(installmentDao.getInstallmentDetailItem(installmentDetail));
                }
                if (InstallmentType.FACTORING.equals(installment.getType())) {
                    detail.addFactoringInstallment(installment);
                }
                if (InstallmentType.RETURN.equals(installment.getType())) {
                    detail.addReturnInstallment(installment);
                }
                if (InstallmentType.LOAN.equals(installment.getType())) {
                    detail.addLoanInstallment(installment);
                }
            }
        }
    }

    private BaseResult updateInstallments(FactoringDetail detail, List<Installment> installments) {
        if (!CollectionUtils.isEmpty(installments)) {
            Installment query = new Installment();
            query.setDetail(detail);
            query.setType(installments.get(0).getType());
            installmentDao.deleteByType(query);
            for (Installment item : installments) {
                item.setDetail(detail);
                installmentDao.insert(item);
            }
        }
        return new BaseResult();
    }
}
