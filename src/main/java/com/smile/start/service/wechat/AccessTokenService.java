/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.service.wechat;

import com.smile.start.model.wechat.AccessToken;

import java.util.List;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AccessTokenService
 * @date Date : 2019年12月16日 20:26
 */
public interface AccessTokenService {

    /**
     * token同步
     */
    void synchronousToken();

    /**
     * 获取token
     * @param token
     * @return
     */
    AccessToken getToken(AccessToken token);

    /**
     * 获取全部token
     * @return
     */
    List<AccessToken> getTokens();
}
