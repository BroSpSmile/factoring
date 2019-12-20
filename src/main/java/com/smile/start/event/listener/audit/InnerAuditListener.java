/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener.audit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.smile.start.event.AuditEvent;
import com.smile.start.mapper.ProjectMapper;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.BaseProject;
import com.smile.start.service.fund.FundService;

/**
 * 直投风控审核监听器
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: InnerAuditListener
 * @date Date : 2019年11月03日 22:06
 */
@Component
public class InnerAuditListener implements AuditListener {

    /** logger */
    public Logger       logger = LoggerFactory.getLogger(getClass());

    /** fundService */
    @Resource
    private FundService fundService;

    /**
     * 监听方法
     *
     * @param event
     */
    @Override
    @EventListener
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (AuditType.INNERAUTH == audit.getAuditType()) {
            if (audit.getStep() == -1) {
                BaseProject<FundTarget> project = ProjectMapper.mapper(audit.getProject(), FundTarget.class);
                FundTarget target = new FundTarget();
                target.setProjectStep(FundStatus.AUDIT_MEETING);
                project.setDetail(target);
                fundService.modifyTarget(project);
            }
        }
    }
}
