/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener.meeting;

import java.util.Date;
import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.smile.start.commons.Constants;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.event.MeetingRemindEvent;
import com.smile.start.event.listener.AbstractListener;
import com.smile.start.integration.wechat.model.*;
import com.smile.start.model.auth.User;
import com.smile.start.model.meeting.MeetingExt;
import com.smile.start.model.wechat.AccessToken;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: MeetingRemindListener
 * @date Date : 2019年12月16日 22:06
 */
@Service
public class MeetingRemindListener extends AbstractListener {

    /**
     * 监听器
     * @param event
     */
    @Async
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
                StringBuilder builder = new StringBuilder();
                builder.append("您参与的").append(meeting.getTheme()).append("将于").append(meeting.getRemind()).append("分钟后在").append(meeting.getPlace()).append("举行,请注意准时出席");
                TextMessage message = getTexMessage(builder, token, user);
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
                MiniMessage message = getMiniMessage(token, user);
                Notice notice = new Notice();
                notice.setAppid(Constants.MINI_PROGRAME_APP_ID);
                notice.setTitle("会议开始提醒");
                notice.setDescription(DateUtil.getChineseDateString(new Date()));
                notice.setEmphasis(false);
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
