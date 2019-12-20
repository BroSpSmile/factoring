/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener.audit;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import com.smile.start.model.project.Project;
import com.smile.start.model.wechat.AccessToken;
import com.smile.start.service.project.ProjectService;

/**
 * 审核通知监听器
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AuditNoticeListener
 * @date Date : 2019年12月19日 11:07
 */
@Service
public class AuditNoticeListener extends AbstractListener implements AuditListener {

    /** 项目服务 */
    @Resource
    private ProjectService projectService;

    /**
     * 监听方法
     *
     * @param event
     */
    @Override
    public void listener(AuditEvent event) {
        Audit audit = event.getAudit();
        List<User> users = userInfoService.getUserByRoles(audit.getRole());
        Project project = projectService.getProject(audit.getProject().getId());
        this.sendText(users, project, audit);
        this.sendNotice(users, project, audit);
    }

    /**
     * 发送文本消息
     * @param users
     * @param project
     * @param audit
     */
    private void sendText(List<User> users, Project project, Audit audit) {
        AccessToken token = accessTokenService.getToken(AgentEnum.APP);
        StringBuilder builder = new StringBuilder();
        builder.append("审核到达通知 \n 您有一项新的审核工单 \n").append("审核项目:").append(project.getProjectId() + project.getProjectName()).append(" \n").append("审核类型:")
            .append(audit.getAuditType().getDesc());
        TextMessage message = getTexMessage(builder, token, users);
        WechatResponse response = wechatClient.sendMessage(token, message);
        LoggerUtils.info(logger, "企业微信推送结果:{}", FastJsonUtils.toJSONString(response));
    }

    /**
     * 发送微信通知消息
     * @param users
     * @param project
     * @param audit
     */
    private void sendNotice(List<User> users, Project project, Audit audit) {
        AccessToken token = accessTokenService.getToken(AgentEnum.MINIPROGRAM);
        MiniMessage message = getMiniMessage(token, users);
        Notice notice = new Notice();
        notice.setTitle("审核到达通知");
        notice.setDescription(DateUtil.getChineseDateString(new Date()));
        notice.setEmphasis(false);
        notice.addItem(new Content("审核项目", project.getProjectId() + project.getProjectName()));
        notice.addItem(new Content("审核类型", audit.getAuditType().getDesc()));
        message.setNotice(notice);
        WechatResponse response = wechatClient.sendMessage(token, message);
        LoggerUtils.info(logger, "企业微信推送结果:{}", FastJsonUtils.toJSONString(response));
    }

}
