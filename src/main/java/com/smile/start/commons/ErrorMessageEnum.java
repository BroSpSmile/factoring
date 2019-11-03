/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.commons;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: ErrorMessageEnum
 * @date Date : 2019年11月03日 17:57
 */
public enum ErrorMessageEnum {
                              DEFUALT("VP00011001", "系统异常");

    /** */
    private String code;

    /** */
    private String message;

    /**
     * 构造函数
     * @param code 错误码
     * @param message 错误描述
     */
    ErrorMessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
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
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }
}
