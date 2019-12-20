/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.event.listener.audit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smile.start.event.AuditEvent;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.project.Audit;
import com.smile.start.service.engine.ProcessEngine;

/**
 * 尽调审核监听器
 * @author smile.jing
 * @version $Id: TuneupAuditListener.java, v 0.1 Mar 12, 2019 9:18:00 PM smile.jing Exp $
 */
@Service
public class TuneupAuditListener implements AuditListener {

    /** processEngine */
    @Resource
    private ProcessEngine processEngine;

    /** 
     * @see AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (AuditType.TUNEUP == audit.getAuditType()) {

        }
    }

}
