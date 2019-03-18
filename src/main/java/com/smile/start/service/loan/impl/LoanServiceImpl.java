/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.loan.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smile.start.dao.LoanDao;
import com.smile.start.dao.ProjectItemDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.LoanType;
import com.smile.start.model.loan.Loan;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditService;
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

    /** 
     * @see com.smile.start.service.loan.LoanService#save(com.smile.start.model.loan.Loan)
     */
    @Override
    @Transactional
    public BaseResult save(Loan loan) {
        //线下保存附件
        if (LoanType.OFFLINE.equals(loan.getType())) {
            for (ProjectItem item : loan.getProject().getItems()) {
                projectItemDao.insert(item);
            }
        }
        long effect = loanDao.insert(loan);
        return toResult(effect);
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
            project.setStep(7);
            project.setUser(loan.getUser());
            processEngine.next(project, true);
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.loan.LoanService#getLoan(com.smile.start.model.project.Project)
     */
    @Override
    public Loan getLoan(Project project) {
        return loanDao.getByProject(project.getId());
    }

}
