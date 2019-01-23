package com.smile.start.exception;

/**
 * 校验异常
 * @author Joseph
 * @version v1.0 2019/1/13 14:22, ValidateException.java
 * @since 1.8
 */
public class ValidateException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 4057845546665065297L;

    public ValidateException() {
        super();
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
