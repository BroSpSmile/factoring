/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.event.listener;

import com.smile.start.dao.FactoringDetailDao;
import com.smile.start.dao.ProjectDao;
import com.smile.start.model.project.AuditRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smile.start.event.AuditEvent;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.project.Audit;
import javax.annotation.Resource;

/**
 * 放款审核监听器
 *
 * @author smile.jing
 * @version $Id: LoanAuditLinstener.java, v 0.1 Mar 12, 2019 9:58:59 PM smile.jing Exp $
 */
@Service
public class LoanAuditLinstener implements AuditListener {

    /**
     * logger
     */
    public Logger              logger = LoggerFactory.getLogger(getClass());

    /**
     * 项目DAO
     */
    @Resource
    private ProjectDao         projectDao;

    /**
     * 项目DAO
     */
    @Resource
    private FactoringDetailDao factoringDetailDao;

    /**
     * @see com.smile.start.event.listener.AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (AuditType.LOAN == audit.getAuditType()) {
            if (audit.getStep() == -1) {
                updateLoanAuditTime(audit);
            }
        }
    }

    private void updateLoanAuditTime(Audit audit) {
        AuditRecord record = audit.getRecords().stream().max((a, b) -> a.getId().compareTo(b.getId())).get();
        factoringDetailDao.updateProjectLoanAuditTime(audit.getProject().getDetail().getId(), record.getAuditTime());
    }
}
