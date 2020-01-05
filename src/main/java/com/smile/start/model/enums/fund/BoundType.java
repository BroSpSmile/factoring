/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.fund;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: BoundType
 * @date Date : 2020年01月05日 22:55
 */
public enum BoundType {

                       /** 区内 */
                       INBOUND("INBOUND", "区内"),

                       /** 区外 */
                       OUTBOUND("OUTBOUND", "区外");

    BoundType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 状态码 */
    private String code;

    /** 状态描述 */
    private String desc;

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }
}
