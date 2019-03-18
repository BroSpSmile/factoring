/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.event.listener;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.ProjectDao;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.enums.StepStatus;
import com.smile.start.model.project.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smile.start.event.AuditEvent;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.project.Audit;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;

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

    /**
     * @see com.smile.start.event.listener.AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (AuditType.LOAN == audit.getAuditType()) {

            //Progress 需要使用，判断项目状态，归档申请页面查询需要使用
            if (audit.getStep() == -1) {
                Project project = audit.getProject();
                Optional<Project> opt = Optional.ofNullable(audit.getProject());
                if (opt.isPresent()) {
                    long updateProjectEffect =
                        projectDao.updateProjectProgress(project.getId(), Progress.LOANED.getCode());
                    LoggerUtils.info(logger, "更新项目状态，影响行effect={}", updateProjectEffect);
                }
            }
        }
    }

}
