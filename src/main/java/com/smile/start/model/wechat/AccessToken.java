/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.wechat;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AccessToken
 * @date Date : 2019年12月16日 20:08
 */
public class AccessToken {

    /** 编号 */
    private long   id;

    /** 企业标识 */
    private String appId;

    /** 应用编号 */
    private long   agentId;

    /** 应用秘钥 */
    private String agentSecret;

    /** token */
    private String accessToken;

    /** 超时时间 */
    private long   expires;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property  id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>appId</tt>.
     *
     * @return property value of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter method for property <tt>appId</tt>.
     *
     * @param appId value to be assigned to property  appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Getter method for property <tt>agentId</tt>.
     *
     * @return property value of agentId
     */
    public long getAgentId() {
        return agentId;
    }

    /**
     * Setter method for property <tt>agentId</tt>.
     *
     * @param agentId value to be assigned to property  agentId
     */
    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    /**
     * Getter method for property <tt>agentSecret</tt>.
     *
     * @return property value of agentSecret
     */
    public String getAgentSecret() {
        return agentSecret;
    }

    /**
     * Setter method for property <tt>agentSecret</tt>.
     *
     * @param agentSecret value to be assigned to property  agentSecret
     */
    public void setAgentSecret(String agentSecret) {
        this.agentSecret = agentSecret;
    }

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
     * Getter method for property <tt>expires</tt>.
     *
     * @return property value of expires
     */
    public long getExpires() {
        return expires;
    }

    /**
     * Setter method for property <tt>expires</tt>.
     *
     * @param expires value to be assigned to property  expires
     */
    public void setExpires(long expires) {
        this.expires = expires;
    }
}
