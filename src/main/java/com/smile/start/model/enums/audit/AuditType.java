/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.audit;

import org.apache.commons.lang3.StringUtils;

/**
 * 审核类型
 * @author smile.jing
 * @version $Id: AuditType.java, v 0.1 Mar 2, 2019 8:55:15 PM smile.jing Exp $
 */
public enum AuditType {
                       /**  */
                       TUNEUP(1, "TUNEUP", "保理尽调审核"),

                       /**  */
                       DRAWUP(2, "DRAWUP", "保理合同审核"),

                       /**  */
                       LOAN(3, "LOAN", "保理放款审核"),

                       /**  */
                       FILE(4, "FILE", "保理归档审核"),

                       /** 直投尽调审核 */
                       DEEP_TUNING(5, "DEEP_TUNING", "直投尽调审核"),

                       /** 直投合同审核 */
                       CONTRACT_SIGN(6, "CONTRACT_SIGN", "直投合同审核流程"),

                       /** 直投放款审核流程 */
                       PAYMENT(7, "PAYMENT", "直投放款审核流程"),

                       /** 直投归档审核流程 */
                       FUND_FILE(8, "FUND_FILE", "直投归档审核流程");

    /** 类型值 */
    private int    value;

    /** 状态码 */
    private String code;

    /** 状态描述 */
    private String desc;

    AuditType(int value, String code, String desc) {
        this.value = value;
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据value获取枚举
     * @param value
     * @return
     */
    public static AuditType fromValue(int value) {
        AuditType[] types = AuditType.values();
        for (AuditType type : types) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public static AuditType getByCode(String code) {
        AuditType[] values = AuditType.values();
        for (AuditType value : values) {
            if (StringUtils.equals(code, value.code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */
    public int getValue() {
        return value;
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
     * Getter method for property <tt>desc</tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }
}
