/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.wechat.impl;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.smile.start.commons.Constants;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.integration.wechat.WechatClient;
import com.smile.start.integration.wechat.model.AccessTokenResponse;
import com.smile.start.integration.wechat.model.WechatMessage;
import com.smile.start.integration.wechat.model.WechatResponse;
import com.smile.start.model.wechat.AccessToken;

/**
 * 实现
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: WechatClientImpl
 * @date Date : 2019年12月16日 21:11
 */
@Service
public class WechatClientImpl implements WechatClient {

    /** http */
    @Resource
    private RestTemplate restTemplate;

    /**
     * 获取token
     *
     * @param token
     * @return
     */
    @Override
    public AccessTokenResponse getToken(AccessToken token) {
        ResponseEntity<String> res = restTemplate.getForEntity(Constants.WECHAT_TOKEN_URL, String.class, token.getAppId(), token.getAgentSecret());
        AccessTokenResponse response = FastJsonUtils.fromJSONString(res.getBody(), AccessTokenResponse.class);
        return response;
    }

    /**
     * 发送消息
     * @param token
     * @param message
     * @return
     */
    @Override
    public WechatResponse sendMessage(AccessToken token, WechatMessage message) {
        ResponseEntity<String> res = restTemplate.postForEntity(Constants.WECHAT_MESSAGE_URL, FastJsonUtils.toJSONString(message), String.class, token.getAccessToken());
        WechatResponse response = FastJsonUtils.fromJSONString(res.getBody(), WechatResponse.class);
        return response;
    }

}
