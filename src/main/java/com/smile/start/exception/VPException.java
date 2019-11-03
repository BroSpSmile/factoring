/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: VPException
 * @date Date : 2019年11月03日 17:13
 */
public class VPException extends Exception {

    /** 默认错误码 */
    private String   DEFAULT_ERROR_CODE = "VP00001001";

    /**错误消息对象 */
    private VipError error;

    /**
     * 构造函数
     * @param error 错误对象
     */
    public VPException(VipError error) {
        super();
        this.error = error;
    }

    /**
     * 构造函数
     * @param error 错误对象
     * @param cause 异常对象
     */
    public VPException(VipError error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    /**
     * 构造函数
     * @param cause
     */
    public VPException(Throwable cause) {
        super(cause);
        if (null != cause) {
            VipError error = new VipError();
            error.setCode(DEFAULT_ERROR_CODE);
            error.setMessage(cause.getMessage());
        }
    }

    /**
     * 获取错误码
     * @return 错误码
     */
    public String getCode() {
        if (null != error) {
            return error.getCode();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取错误描述
     * @return 异常描述
     */
    public String getMessage() {
        if (null != error) {
            return error.getMessage();
        }
        return StringUtils.EMPTY;
    }
}
