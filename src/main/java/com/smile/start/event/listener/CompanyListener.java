/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.event.CompanyEvent;
import com.smile.start.integration.wechat.model.*;
import com.smile.start.model.auth.User;
import com.smile.start.model.enums.wechat.AgentEnum;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.wechat.AccessToken;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: CompanyListener
 * @date Date : 2019年12月23日 18:03
 */
@Component
public class CompanyListener extends AbstractListener {

    /**
     * 监听方法
     * @param event
     */
    @Async
    @EventListener
    public void listener(CompanyEvent event) {
        FundTarget target = event.getTarget();
        List<User> users = Lists.newArrayList();
        if (null != target.getMemberA()) {
            users.add(userInfoService.getUserById(target.getMemberA().getId()));
        }
        if (null != target.getMemberB()) {
            users.add(userInfoService.getUserById(target.getMemberB().getId()));
        }
        this.sendNotice(users, target, event.getDef());
        this.sendText(users, target, event.getDef());
    }

    /**
     * 发送文本消息
     * @param users
     * @param target
     * @param def
     */
    private void sendText(List<User> users, FundTarget target, Map<String, String> def) {
        AccessToken token = accessTokenService.getToken(AgentEnum.APP);
        StringBuilder builder = new StringBuilder();
        builder.append("企业信息变动提醒\n项目:").append(target.getProjectId()).append(target.getProjectName()).append("\n变动内容:\n");
        for (Map.Entry<String, String> entry : def.entrySet()) {
            builder.append(entry.getValue()).append("  ");
        }
        TextMessage message = getTexMessage(builder, token, users);
        WechatResponse response = wechatClient.sendMessage(token, message);
        LoggerUtils.info(logger, "企业微信推送结果:{}", FastJsonUtils.toJSONString(response));
    }

    /**
     * 发送微信通知消息
     * @param users
     * @param target
     * @param def
     */
    private void sendNotice(List<User> users, FundTarget target, Map<String, String> def) {
        AccessToken token = accessTokenService.getToken(AgentEnum.MINIPROGRAM);
        MiniMessage message = getMiniMessage(token, users);
        Notice notice = new Notice();
        notice.setTitle("企业信息变动提醒");
        notice.setDescription(DateUtil.getChineseDateString(new Date()));
        notice.addItem(new Content("项目", target.getProjectId() + target.getProjectName()));
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : def.entrySet()) {
            builder.append(entry.getValue()).append("  ");
        }
        notice.addItem(new Content("变动内容", builder.toString()));
        message.setNotice(notice);
        WechatResponse response = wechatClient.sendMessage(token, message);
        LoggerUtils.info(logger, "企业微信推送结果:{}", FastJsonUtils.toJSONString(response));
    }

}
