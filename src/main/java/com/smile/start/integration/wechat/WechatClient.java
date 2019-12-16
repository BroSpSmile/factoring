/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.wechat;

import com.smile.start.integration.wechat.model.AccessTokenResponse;
import com.smile.start.integration.wechat.model.WechatMessage;
import com.smile.start.integration.wechat.model.WechatResponse;
import com.smile.start.model.wechat.AccessToken;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: WechatClient
 * @date Date : 2019年12月16日 20:34
 */
public interface WechatClient {

    /**
     * 获取token
     * @param token
     * @return
     */
    AccessTokenResponse getToken(AccessToken token);

    /**
     * 发送消息
     * @param token
     * @param message
     * @return
     */
    WechatResponse sendMessage(AccessToken token, WechatMessage message);

}
