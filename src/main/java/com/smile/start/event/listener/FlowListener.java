package com.smile.start.event.listener;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.ProjectDao;
import com.smile.start.event.FlowEvent;
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

@Component
public class FlowListener {

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
     * 注册监听实现方法
     *
     * @param event 主流程变更事件
     */
    @EventListener
    public void register(FlowEvent event) {
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


        //TODO FILING表也需要相应变更
    }

}
