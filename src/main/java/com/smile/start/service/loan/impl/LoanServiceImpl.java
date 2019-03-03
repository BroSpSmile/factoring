/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.loan.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smile.start.dao.LoanDao;
import com.smile.start.dao.ProjectItemDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.LoanType;
import com.smile.start.model.loan.Loan;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.AbstractService;
import com.smile.start.service.loan.LoanService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: LoanServiceImpl.java, v 0.1 Feb 27, 2019 9:08:51 PM smile.jing Exp $
 */
@Service
public class LoanServiceImpl extends AbstractService implements LoanService {

    /** loadDao */
    @Resource
    private LoanDao        loadDao;

    /** projectItemDao */
    @Resource
    private ProjectItemDao projectItemDao;

    /** 
     * @see com.smile.start.service.loan.LoanService#save(com.smile.start.model.loan.Loan)
     */
    @Override
    public BaseResult save(Loan loan) {
        //线下保存附件
        if (LoanType.OFFLINE.equals(loan.getType())) {
            for (ProjectItem item : loan.getProject().getItems()) {
                projectItemDao.insert(item);
            }
        }
        long effect = loadDao.insert(loan);
        return toResult(effect);
    }

    /** 
     * @see com.smile.start.service.loan.LoanService#commit(com.smile.start.model.loan.Loan)
     */
    @Override
    public BaseResult commit(Loan loan) {
        BaseResult result = this.save(loan);
        return result;
    }

}
