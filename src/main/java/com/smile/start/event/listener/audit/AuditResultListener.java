/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener.audit;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.smile.start.commons.DateUtil;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.event.AuditEvent;
import com.smile.start.event.listener.AbstractListener;
import com.smile.start.integration.wechat.model.*;
import com.smile.start.model.auth.User;
import com.smile.start.model.enums.wechat.AgentEnum;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.model.project.Project;
import com.smile.start.model.wechat.AccessToken;
import com.smile.start.service.project.ProjectService;

/**
 * 审核结果监听器
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AuditResultListener
 * @date Date : 2019年12月19日 11:21
 */
@Service
public class AuditResultListener extends AbstractListener implements AuditListener {

    /** 项目服务 */
    @Resource
    private ProjectService projectService;

    /**
     * 监听方法
     *
     * @param event
     */
    @Override
    @Async
    @EventListener
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        if (audit.getStep() != 1) {
            Project project = projectService.getProject(audit.getProject().getId());
            User user = userInfoService.getUserById(project.getUser().getId());
            this.sendText(user, project, audit);
            this.sendNotice(user, project, audit);
        }
    }

    /**
     * 发送文本消息
     * @param user
     * @param project
     * @param audit
     */
    private void sendText(User user, Project project, Audit audit) {
        AccessToken token = accessTokenService.getToken(AgentEnum.APP);
        StringBuilder builder = new StringBuilder();
        AuditRecord record = audit.getRecords().get(audit.getRecords().size() - 1);
        builder.append("审核结果通知\n项目:").append(project.getProjectId()).append(project.getProjectName()).append("\n").append(audit.getAuditType().getDesc()).append("\n结果:")
            .append(record.getType()).append(record.getResult().getDesc()).append("\n审核人:").append(record.getAuditor().getUsername());
        TextMessage message = getTexMessage(builder, token, user);
        WechatResponse response = wechatClient.sendMessage(token, message);
        LoggerUtils.info(logger, "企业微信推送结果:{}", FastJsonUtils.toJSONString(response));
    }

    /**
     * 发送微信通知消息
     * @param user
     * @param project
     * @param audit
     */
    private void sendNotice(User user, Project project, Audit audit) {
        AccessToken token = accessTokenService.getToken(AgentEnum.MINIPROGRAM);
        AuditRecord record = audit.getRecords().get(audit.getRecords().size() - 1);
        MiniMessage message = getMiniMessage(token, user);
        Notice notice = new Notice();
        notice.setTitle("审核结果通知");
        notice.setDescription(DateUtil.getChineseDateString(new Date()));
        notice.setEmphasis(true);
        notice.addItem(new Content("审核结果", record.getResult().getDesc()));
        notice.addItem(new Content("项目", project.getProjectId() + project.getProjectName()));
        notice.addItem(new Content("审核类型", audit.getAuditType().getDesc()));
        notice.addItem(new Content("审核步骤", record.getType()));
        notice.addItem(new Content("审核人", record.getAuditor().getUsername()));
        message.setNotice(notice);
        WechatResponse response = wechatClient.sendMessage(token, message);
        LoggerUtils.info(logger, "企业微信推送结果:{}", FastJsonUtils.toJSONString(response));
    }
}
