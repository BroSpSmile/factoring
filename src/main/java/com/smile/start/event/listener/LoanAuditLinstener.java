/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.event.listener;

import com.smile.start.dao.AuditRecordDao;
import com.smile.start.dao.FactoringDetailDao;
import com.smile.start.dao.ProjectDao;
import com.smile.start.model.project.AuditRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.smile.start.event.AuditEvent;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.project.Audit;

import javax.annotation.Resource;
import java.util.List;

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
    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 项目DAO
     */
    @Resource
    private ProjectDao projectDao;

    @Resource
    private AuditRecordDao auditRecordDao;

    /**
     * 项目DAO
     */
    @Resource
    private FactoringDetailDao factoringDetailDao;

    /**
     * @see com.smile.start.event.listener.AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    @EventListener
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (AuditType.LOAN == audit.getAuditType()) {
            if (audit.getStep() == -1) {
                updateLoanAuditTime(audit);
            }
        }
    }

    private void updateLoanAuditTime(Audit audit) {
        if (null == audit.getRecords()) {
            List<AuditRecord> records = auditRecordDao.query(audit);
            audit.setRecords(records);
        }
        if (null != audit.getRecords()) {
            AuditRecord record = audit.getRecords().stream().max((a, b) -> a.getId().compareTo(b.getId())).get();
            if (null == audit.getProject().getDetail()) {
                audit.getProject().setDetail(factoringDetailDao.getByProject(audit.getProject().getId()));
            }
            if (null != audit.getProject().getDetail()) {
                factoringDetailDao.updateProjectLoanAuditTime(audit.getProject().getDetail().getId(),
                    record.getAuditTime());
            }
        }
    }
}
