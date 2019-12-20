/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.tianyan.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 基本返回
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: TianyanResult
 * @date Date : 2019年12月18日 18:59
 */
public class TianyanResult<T> {

    /** 错误码 */
    @JSONField(name = "error_code")
    private int    errorCode;

    /** 错误内容 */
    private String reason;

    /** 返回结果 */
    private T      result;

    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode</tt>.
     *
     * @param errorCode value to be assigned to property  errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>reason</tt>.
     *
     * @return property value of reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Setter method for property <tt>reason</tt>.
     *
     * @param reason value to be assigned to property  reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public T getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property  result
     */
    public void setResult(T result) {
        this.result = result;
    }
}
