/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.service.wechat.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.wechat.AccessTokenDao;
import com.smile.start.integration.wechat.WechatClient;
import com.smile.start.integration.wechat.model.AccessTokenResponse;
import com.smile.start.model.enums.wechat.AgentEnum;
import com.smile.start.model.wechat.AccessToken;
import com.smile.start.service.AbstractService;
import com.smile.start.service.wechat.AccessTokenService;

/**
 * 实现
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AccessTokenServiceImpl
 * @date Date : 2019年12月16日 20:29
 */
@Service
public class AccessTokenServiceImpl extends AbstractService implements AccessTokenService {

    /** accessTokenDao */
    @Resource
    private AccessTokenDao accessTokenDao;

    /** 微信客户端 */
    @Resource
    private WechatClient   wechatClient;

    /**
     * token同步
     */
    @Override
    public void synchronousToken() {
        List<AccessToken> tokens = accessTokenDao.getAllToken();
        for (AccessToken token : tokens) {
            AccessTokenResponse response = wechatClient.getToken(token);
            token.setAccessToken(response.getAccessToken());
            token.setExpires(response.getExpiresIn());
            LoggerUtils.info(logger, "更新token:{}", FastJsonUtils.toJSONString(token));
            accessTokenDao.update(token);
        }
    }

    /**
     * 获取token
     *
     * @param agent
     * @return
     */
    @Override
    public AccessToken getToken(AgentEnum agent) {
        return accessTokenDao.getToken(agent.getValue());
    }

    /**
     * 获取全部token
     *
     * @return
     */
    @Override
    public List<AccessToken> getTokens() {
        return accessTokenDao.getAllToken();
    }
}
