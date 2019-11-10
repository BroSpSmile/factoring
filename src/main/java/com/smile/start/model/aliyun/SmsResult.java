/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.aliyun;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: SmsResult
 * @date Date : 2019年11月04日 16:40
 */
public class SmsResult {

    /** 返回码 */
    @JSONField(name = "Code")
    private String code;

    /** 返回内容 */
    @JSONField(name = "Message")
    private String message;

    /** 业务ID */
    @JSONField(name = "BizId")
    private String bizId;

    /** 请求ID */
    @JSONField(name = "RequestId")
    private String requestId;

    @Override
    public String toString() {
        return "{\"SmsResult\":{" + "\"code\":\"" + code + '\"' + ",\"message\":\"" + message + '\"' + ",\"bizId\":\"" + bizId + '\"' + ",\"requestId\":\"" + requestId + '\"'
               + "}}";

    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property  code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message value to be assigned to property  message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter method for property <tt>bizId</tt>.
     *
     * @return property value of bizId
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * Setter method for property <tt>bizId</tt>.
     *
     * @param bizId value to be assigned to property  bizId
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    /**
     * Getter method for property <tt>requestId</tt>.
     *
     * @return property value of requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Setter method for property <tt>requestId</tt>.
     *
     * @param requestId value to be assigned to property  requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
