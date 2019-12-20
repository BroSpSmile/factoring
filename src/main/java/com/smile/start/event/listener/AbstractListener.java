/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smile.start.integration.wechat.WechatClient;
import com.smile.start.integration.wechat.model.MessageType;
import com.smile.start.integration.wechat.model.MiniMessage;
import com.smile.start.integration.wechat.model.Text;
import com.smile.start.integration.wechat.model.TextMessage;
import com.smile.start.model.auth.User;
import com.smile.start.model.wechat.AccessToken;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.wechat.AccessTokenService;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AbstractListener
 * @date Date : 2019年12月19日 12:22
 */
public abstract class AbstractListener {
    /** logger */
    protected Logger             logger = LoggerFactory.getLogger(getClass());

    /** token */
    @Resource
    protected AccessTokenService accessTokenService;

    /** 微信client */
    @Resource
    protected WechatClient       wechatClient;

    /** 用户服务 */
    @Resource
    protected UserInfoService    userInfoService;

    /**
     * getTexMessage
     * @param builder
     * @param token
     * @param user
     * @return
     */
    protected TextMessage getTexMessage(StringBuilder builder, AccessToken token, User user) {
        TextMessage message = new TextMessage();
        message.setMsgtype(MessageType.text);
        message.setTouser(user.getWechatNo());
        message.setAgentid(token.getAgentId());
        Text text = new Text();
        text.setContent(builder.toString());
        message.setText(text);
        return message;
    }

    /**
     * getTexMessage
     * @param builder
     * @param token
     * @param users
     * @return
     */
    protected TextMessage getTexMessage(StringBuilder builder, AccessToken token, List<User> users) {
        TextMessage message = new TextMessage();
        message.setMsgtype(MessageType.text);
        message.setTouser(touser(users));
        message.setAgentid(token.getAgentId());
        Text text = new Text();
        text.setContent(builder.toString());
        message.setText(text);
        return message;
    }

    /**
     * 
     * @param token
     * @param user
     * @return
     */
    protected MiniMessage getMiniMessage(AccessToken token, User user) {
        MiniMessage message = new MiniMessage();
        message.setMsgtype(MessageType.miniprogram_notice);
        message.setTouser(user.getWechatNo());
        return message;
    }

    /**
     *
     * @param token
     * @param users
     * @return
     */
    protected MiniMessage getMiniMessage(AccessToken token, List<User> users) {
        MiniMessage message = new MiniMessage();
        message.setMsgtype(MessageType.miniprogram_notice);
        message.setTouser(touser(users));
        return message;
    }

    /**
     * 推送对象转换
     * @param users
     * @return
     */
    private String touser(List<User> users) {
        StringBuilder builder = new StringBuilder();
        for (User user : users) {
            if (StringUtils.isNotBlank(user.getWechatNo())) {
                builder.append("|").append(user.getWechatNo());
            }
        }
        String touser = builder.toString();
        if (StringUtils.isNotBlank(touser)) {
            touser = touser.replaceFirst("|", "");
        }
        return touser;
    }
}
