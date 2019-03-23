/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.finance.impl;

import com.smile.start.dao.InstallmentDao;
import com.smile.start.event.AuditEvent;
import com.smile.start.event.InstallmentEvent;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.*;
import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.project.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.smile.start.service.AbstractService;
import com.smile.start.service.finance.FinanceService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private ApplicationContext applicationContext;

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
        List<Installment> installments = null;
        if (installmentType == InstallmentType.LOAN) {
            installments = detail.getLoanInstallments();
        } else if (installmentType == InstallmentType.RETURN) {
            installments = detail.getLoanInstallments();
        } else if (installmentType == InstallmentType.FACTORING) {
            installments = detail.getLoanInstallments();
        }
        BaseResult result = new BaseResult();
        for (Installment installment : installments) {
            installment.setType(installmentType);
            installment.setDetail(project.getDetail());
            long effect = 0;
            long itemEffect = 0;
            if (installment.getId().equals(new Long(-1L))) {
                //ADD
                effect = installmentDao.insert(installment);
                if (null != installment.getItem()) {
                    installment.getItem().setInstallmentId(installment.getId());
                    itemEffect = installmentDao.insertInstallmentItem(installment.getItem());
                }
            } else {
                //UPDATE
                effect = installmentDao.update(installment);
                if (null != installment.getItem()) {
                    installment.getItem().setInstallmentId(installment.getId());
                    itemEffect = installmentDao.deleteInstallmentItem(installment.getItem());
                    if (itemEffect >= 0) {
                        itemEffect = installmentDao.insertInstallmentItem(installment.getItem());
                    }
                }
            }
            if (installmentType == InstallmentType.FACTORING) {
                List<InstallmentDetail> detailList = installment.getDetailList();
                if (!CollectionUtils.isEmpty(detailList) && detailList.size() > 0) {
                    result = saveInstallmentDetails(detailList);
                }
            }

            if (effect < 0 || itemEffect < 0 || !result.isSuccess()) {
                throw new RuntimeException("保存" + installmentType.getDesc() + "信息失败!");
            }
        }

        if (result.isSuccess()) {
            applicationContext.publishEvent(new InstallmentEvent(this, project));
        }
        return result;
    }

    /**
     * 保存分期明细信息
     *
     * @param detailList
     * @return
     */
    private BaseResult saveInstallmentDetails(List<InstallmentDetail> detailList) {
        if (!CollectionUtils.isEmpty(detailList) && detailList.size() > 0) {
            for (InstallmentDetail detail : detailList) {
                long effect = 0;
                long itemEffect = 0;
                if (detail.getId().equals(new Long(-1L))) {
                    //ADD
                    effect = installmentDao.insertInstallmentDetail(detail);
                    detail.getItem().setInstallmentDetailId(detail.getId());
                    itemEffect = installmentDao.insertInstallmentDetailItem(detail.getItem());
                } else {
                    //UPDATE
                    effect = installmentDao.updateInstallmentDetail(detail);
                    detail.getItem().setInstallmentDetailId(detail.getId());
                    itemEffect = installmentDao.deleteInstallmentDetailItem(detail.getItem());
                    if (itemEffect > 0) {
                        itemEffect = installmentDao.insertInstallmentDetailItem(detail.getItem());
                    }
                }

                if (effect <= 0 || itemEffect <= 0) {
                    throw new RuntimeException("保存分期明细信息失败!");
                }
            }
        }
        return new BaseResult();
    }
}
