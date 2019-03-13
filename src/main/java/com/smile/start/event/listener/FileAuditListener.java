package com.smile.start.event.listener;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.ProjectDao;
import com.smile.start.event.AuditEvent;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 归档审核监听器
 * @author smile.jing
 * @version $Id: FileAuditListener.java, v 0.1 Mar 12, 2019 9:50:44 PM smile.jing Exp $
 */
@Component
public class FileAuditListener implements AuditListener {

    /**
     * logger
     */
    public Logger      logger = LoggerFactory.getLogger(getClass());

    /**
     * 项目DAO
     */
    @Resource
    private ProjectDao projectDao;

    /** 
     * @see com.smile.start.event.listener.AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    @EventListener
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        //注册监听，变更项目表
        if (AuditType.FILE == audit.getAuditType() && 0 == audit.getStep()) {
            Project project = audit.getProject();
            Optional<Project> opt = Optional.ofNullable(audit.getProject());
            if (opt.isPresent()) {
                long updateProjectEffect = projectDao.updateProjectProgress(project.getId(), Progress.last(project.getProgress()).getCode());
                LoggerUtils.info(logger, "更新项目状态，影响行effect={}", updateProjectEffect);
            }
        }
    }

}
