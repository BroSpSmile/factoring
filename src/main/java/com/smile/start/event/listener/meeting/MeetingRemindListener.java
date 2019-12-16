/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener.meeting;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.smile.start.commons.Constants;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.event.MeetingRemindEvent;
import com.smile.start.integration.wechat.WechatClient;
import com.smile.start.integration.wechat.model.*;
import com.smile.start.model.auth.User;
import com.smile.start.model.meeting.MeetingExt;
import com.smile.start.model.wechat.AccessToken;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.wechat.AccessTokenService;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: MeetingRemindListener
 * @date Date : 2019年12月16日 22:06
 */
@Service
public class MeetingRemindListener {

    /** logger */
    private Logger             logger = LoggerFactory.getLogger(getClass());

    /** 用户服务 */
    @Resource
    private UserInfoService    userInfoService;

    /** token */
    @Resource
    private AccessTokenService accessTokenService;

    /** 微信client */
    @Resource
    private WechatClient       wechatClient;

    /**
     * 监听器
     * @param event
     */
    @EventListener
    public void listener(MeetingRemindEvent event) {
        MeetingExt meeting = event.getMeeting();
        List<AccessToken> tokens = accessTokenService.getTokens();
        for (String idString : meeting.getParticipantNoList().split(",")) {
            User notice = userInfoService.getUserById(Long.valueOf(idString));
            sendNotice(meeting, notice, tokens);
            sendText(meeting, notice, tokens);
        }
    }

    /**
     * 发送文本消息
     * @param meeting
     * @param user
     */
    public void sendText(MeetingExt meeting, User user, List<AccessToken> tokens) {
        for (AccessToken token : tokens) {
            if (token.getAgentId() == 1000003L) {
                TextMessage message = new TextMessage();
                message.setMsgtype(MessageType.text);
                message.setTouser(user.getWechatNo());
                message.setAgentid(token.getAgentId());
                Text text = new Text();
                StringBuilder builder = new StringBuilder();
                builder.append("您参与的").append(meeting.getTheme()).append("将于").append(meeting.getRemind()).append("分钟后在").append(meeting.getPlace()).append("举行,请注意准时出席");
                text.setContent(builder.toString());
                message.setText(text);
                WechatResponse response = wechatClient.sendMessage(token, message);
                LoggerUtils.info(logger, "企业微信推送结果:{}", FastJsonUtils.toJSONString(response));
            }
        }

    }

    /**
     * 发送小程序消息
     * @param meeting
     * @param user
     */
    public void sendNotice(MeetingExt meeting, User user, List<AccessToken> tokens) {
        for (AccessToken token : tokens) {
            if (token.getAgentId() == 1000002L) {
                MiniMessage message = new MiniMessage();
                message.setMsgtype(MessageType.miniprogram_notice);
                message.setTouser(user.getWechatNo());
                Notice notice = new Notice();
                notice.setAppid(Constants.MINI_PROGRAME_APP_ID);
                notice.setTitle("会议开始提醒");
                notice.setDescription(DateUtil.getChineseDateString(new Date()));
                notice.setEmphasis(true);
                notice.addItem(new Content("会议提醒", "您参与的会议即将于" + meeting.getRemind() + "分钟后举行"));
                notice.addItem(new Content("会议室", meeting.getPlace()));
                notice.addItem(new Content("会议主题", meeting.getTheme()));
                message.setNotice(notice);
                WechatResponse response = wechatClient.sendMessage(token, message);
                LoggerUtils.info(logger, "企业微信推送结果:{}", FastJsonUtils.toJSONString(response));
            }
        }
    }
}
