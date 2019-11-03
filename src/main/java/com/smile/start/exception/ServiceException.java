/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.exception;

/**
 * 服务异常
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: ServiceException
 * @date Date : 2019年11月03日 17:45
 */
public class ServiceException extends VPException {
    /**
     * 构造函数
     *
     * @param error 错误对象
     */
    public ServiceException(VipError error) {
        super(error);
    }

    /**
     * 构造函数
     *
     * @param error 错误对象
     * @param cause 异常对象
     */
    public ServiceException(VipError error, Throwable cause) {
        super(error, cause);
    }

    /**
     * 构造函数
     *
     * @param cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
