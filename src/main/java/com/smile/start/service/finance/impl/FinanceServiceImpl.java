/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.finance.impl;

import com.smile.start.dao.InstallmentDao;
import com.smile.start.model.enums.InstallmentType;
import com.smile.start.model.project.Installment;
import org.springframework.stereotype.Service;

import com.smile.start.service.AbstractService;
import com.smile.start.service.finance.FinanceService;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * 实现
 * @author Jacky
 * @version $Id: FinanceServiceImpl.java, v 0.1 2019年3月6日 下午3:26:32 Jacky Exp $
 */
@Service
public class FinanceServiceImpl extends AbstractService implements FinanceService {

    @Resource
    private InstallmentDao installmentDao;

    /**
     * 保存放款信息
     * @param installment
     * @return
     */
    @Override
    public Long saveLoanInstallment(Installment installment) {
        installment.setType(InstallmentType.LOAN);
        return installmentDao.insert(installment);
    }
}
