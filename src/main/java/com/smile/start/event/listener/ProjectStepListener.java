/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.event.listener;

import java.util.Collections;

import javax.annotation.Resource;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.smile.start.event.AuditEvent;
import com.smile.start.event.listener.audit.AuditListener;
import com.smile.start.model.enums.StepStatus;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.Project;
import com.smile.start.service.engine.ProcessEngine;

/**
 * 项目状态监听器,根据审核结果变更项目状态
 * @author smile.jing
 * @version $Id: ProjectStepLinstener.java, v 0.1 Mar 12, 2019 9:20:23 PM smile.jing Exp $
 */
@Service
public class ProjectStepListener implements AuditListener {

    /**  */
    @Resource
    private ProcessEngine processEngine;

    /** 
     * @see AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    @EventListener
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (audit.getStep() != 1) {
            Project project = audit.getProject();
            if (null != project) {
                project.setItems(Collections.emptyList());
                project.setStep(getStep(audit.getAuditType()));
                if (audit.getStep() == -1) {//审核完结流转下一节点
                    processEngine.changeStatus(project, StepStatus.COMPLETED, audit);
                    processEngine.next(project, false);
                } else if (audit.getStep() == 0 || audit.getStep() == -2) {//审核驳回流转
                    processEngine.prev(project);
                }

            }
        }

    }

    private Integer getStep(AuditType type) {
        Integer step = 0;
        switch (type) {
            case TUNEUP:
                step = 2;
                break;
            case DRAWUP:
                step = 5;
                break;
            case LOAN:
                step = 8;
                break;
            case FILE:
                step = 11;
                break;
            default:
                break;
        }
        return step;
    }

}
