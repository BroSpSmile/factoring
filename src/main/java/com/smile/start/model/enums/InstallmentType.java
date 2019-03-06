/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 分期类型
 * @author smile.jing
 * @version $Id: AuditResult.java, v 0.1 Mar 2, 2019 9:12:40 PM smile.jing Exp $
 */
public enum InstallmentType {
                             /**  */
                             LOAN("LOAN", "放款分期"),

                             /**  */
                             FACTORING("FACTORING", "保理费分期"),

                             /**  */
                             RETURN("RETURN", "回款分期");

    InstallmentType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public InstallmentType getByCode(String code) {
        InstallmentType[] values = InstallmentType.values();
        for (InstallmentType value : values) {
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
