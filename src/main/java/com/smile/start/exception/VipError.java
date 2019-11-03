/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.exception;

import java.io.Serializable;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: VipError
 * @date Date : 2019年11月03日 17:33
 */
public class VipError implements Serializable {

    /** 错误码 */
    private String code;

    /**消息文本 */
    private String message;

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
}
