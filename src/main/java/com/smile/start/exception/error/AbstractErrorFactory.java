/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.exception.error;

import com.smile.start.exception.VipError;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AbstractErrorFactory
 * @date Date : 2019年11月03日 17:48
 */
public class AbstractErrorFactory {

    /**
     * 创建错误对象
     * @param code 错误码
     * @param message 错误描述
     * @return 错误对象
     */
    public static VipError createStaticError(String code, String message) {
        VipError error = new VipError();
        error.setCode(code);
        error.setMessage(message);
        return error;
    }

    /**
     * 创建错误对象
     * @param code 错误码
     * @param message 错误描述
     * @return 错误对象
     */
    protected VipError createError(String code, String message) {
        return createStaticError(code, message);
    }
}
