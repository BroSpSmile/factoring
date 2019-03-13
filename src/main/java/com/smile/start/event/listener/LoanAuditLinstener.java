/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.event.listener;

import org.springframework.stereotype.Service;

import com.smile.start.event.AuditEvent;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.project.Audit;

/**
 * 放款审核监听器
 * @author smile.jing
 * @version $Id: LoanAuditLinstener.java, v 0.1 Mar 12, 2019 9:58:59 PM smile.jing Exp $
 */
@Service
public class LoanAuditLinstener implements AuditListener {

    /** 
     * @see com.smile.start.event.listener.AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (AuditType.LOAN == audit.getAuditType()) {

        }
    }

}
