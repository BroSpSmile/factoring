/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author smile.jing
 * @version $Id: LoanType.java, v 0.1 Feb 25, 2019 4:19:25 PM smile.jing Exp $
 */
public enum LoanType {
                      /** 线上  */
                      ONLINE("ONLINE", "线上"),
                      /** 线下 */
                      OFFLINE("OFFLINE", "线下");

    LoanType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public LoanType getByCode(String code) {
        LoanType[] values = LoanType.values();
        for (LoanType value : values) {
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
