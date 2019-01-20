package com.smile.start.commons;

import com.smile.start.exception.ValidateException;

/**
 * @author Joseph
 * @version v1.0 2019/1/13 14:20, Asserts.java
 * @since 1.8
 */
public final class Asserts {
    private Asserts() {}

    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new ValidateException(message);
        }
    }

    public static void notTrue(boolean result, String message) {
        if(!result) {
            throw new ValidateException(message);
        }
    }

    public static void notEmpty(String value, String message) {
        if(value == null || value.isEmpty()) {
            throw new ValidateException(message);
        }
    }
}
