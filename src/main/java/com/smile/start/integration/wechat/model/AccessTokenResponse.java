/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AccessTokenResponse
 * @date Date : 2019年12月16日 20:36
 */
public class AccessTokenResponse extends WechatResponse {

    /** accessToken */
    @JSONField(name = "access_token")
    private String accessToken;

    /**  */
    @JSONField(name = "expires_in")
    private long   expiresIn;

    /**
     * Getter method for property <tt>accessToken</tt>.
     *
     * @return property value of accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Setter method for property <tt>accessToken</tt>.
     *
     * @param accessToken value to be assigned to property  accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Getter method for property <tt>expiresIn</tt>.
     *
     * @return property value of expiresIn
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Setter method for property <tt>expiresIn</tt>.
     *
     * @param expiresIn value to be assigned to property  expiresIn
     */
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
