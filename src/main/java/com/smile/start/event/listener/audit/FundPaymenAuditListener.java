/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener.audit;

import javax.annotation.Resource;

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
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundPaymenAuditLinstener
 * @date Date : 2019年11月10日 20:07
 */
@Component
public class FundPaymenAuditListener implements AuditListener {
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
        if (AuditType.PAYMENT == audit.getAuditType()) {
            if (audit.getStep() == -1) {
                BaseProject<FundTarget> project = ProjectMapper.mapper(audit.getProject(), FundTarget.class);
                FundTarget target = new FundTarget();
                target.setProjectStep(FundStatus.FUND_LOAN);
                project.setDetail(target);
                fundService.modifyTarget(project);
            }
        }
    }
}
