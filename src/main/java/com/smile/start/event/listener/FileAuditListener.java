package com.smile.start.event.listener;

import com.smile.start.dao.FilingDao;
import com.smile.start.dao.ProjectDao;
import com.smile.start.event.AuditEvent;
import com.smile.start.model.contract.ContractInfo;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.enums.Step;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.Project;
import com.smile.start.service.engine.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 归档审核监听器
 *
 * @author smile.jing
 * @version $Id: FileAuditListener.java, v 0.1 Mar 12, 2019 9:50:44 PM smile.jing Exp $
 */
@Component
public class FileAuditListener implements AuditListener {

    /**
     * logger
     */
    public Logger      logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ProcessEngine processEngine;

    /**
     * @see com.smile.start.event.listener.AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    @EventListener
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (AuditType.FILE == audit.getAuditType()) {
            //办公室专员审核完成，直接到完结状态
            if(audit.getStep() == -1) {
                //TODO 回款进度判断
//                Project project = audit.getProject();
//                project.setStep(Step.END.getIndex());
//                processEngine.next(project, false);
            }
        }
    }

}
