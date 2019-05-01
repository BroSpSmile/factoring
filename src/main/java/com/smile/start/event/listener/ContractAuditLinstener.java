/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.event.listener;

import com.smile.start.model.contract.ContractInfo;
import com.smile.start.service.contract.ContractInfoService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.smile.start.event.AuditEvent;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.project.Audit;

import javax.annotation.Resource;

/**
 * 
 * @author smile.jing
 * @version $Id: ContractAuditLinstener.java, v 0.1 Mar 12, 2019 10:01:03 PM smile.jing Exp $
 */
@Service
public class ContractAuditLinstener implements AuditListener {

    @Resource
    private ContractInfoService contractInfoService;

    /** 
     * @see com.smile.start.event.listener.AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    @EventListener
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (AuditType.DRAWUP == audit.getAuditType()) {
            if(audit.getStep() == 0) {
                ContractInfo contractInfo = contractInfoService.getBaseInfo(audit.getProject().getId());
                contractInfo.setStatus(0);
                contractInfoService.updateBaseInfo(contractInfo);
            }
        }
    }

}
