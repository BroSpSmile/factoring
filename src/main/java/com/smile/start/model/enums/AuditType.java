/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 审核类型
 * @author smile.jing
 * @version $Id: AuditType.java, v 0.1 Mar 2, 2019 8:55:15 PM smile.jing Exp $
 */
public enum AuditType {
                       /**  */
                       TUNEUP("TUNEUP", "尽调审核"),

                       /**  */
                       CONTRACT("CONTRACT", "合同审核"),

                       /**  */
                       LOAN("LOAN", "放款审核"),

                       /**  */
                       FILE("FILE", "归档审核");

    AuditType(String code, String desc) {
        this.code = code;
        this.desc = desc;
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
