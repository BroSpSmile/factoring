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
public enum InstallmentDetailType {
                      /** 开票  */
                      INVOICE("INVOICE", "开票"),
                      /** 收款 */
                      PAYMENT("PAYMENT", "收款");

    InstallmentDetailType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public InstallmentDetailType getByCode(String code) {
        InstallmentDetailType[] values = InstallmentDetailType.values();
        for (InstallmentDetailType value : values) {
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
