/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.wechat.model;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: WechatResponse
 * @date Date : 2019年12月16日 20:35
 */
public class WechatResponse {

    /** 错误码 */
    private long   errcode;

    /** 错误描述 */
    private String errmsg;

    /**
     * Getter method for property <tt>errcode</tt>.
     *
     * @return property value of errcode
     */
    public long getErrcode() {
        return errcode;
    }

    /**
     * Setter method for property <tt>errcode</tt>.
     *
     * @param errcode value to be assigned to property  errcode
     */
    public void setErrcode(long errcode) {
        this.errcode = errcode;
    }

    /**
     * Getter method for property <tt>errmsg</tt>.
     *
     * @return property value of errmsg
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     * Setter method for property <tt>errmsg</tt>.
     *
     * @param errmsg value to be assigned to property  errmsg
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
