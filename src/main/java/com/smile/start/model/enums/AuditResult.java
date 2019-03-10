/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 审核结果
 * @author smile.jing
 * @version $Id: AuditResult.java, v 0.1 Mar 2, 2019 9:12:40 PM smile.jing Exp $
 */
public enum AuditResult {

                         /**  */
                         APPLY("APPLY", "申请"),

                         /**  */
                         PASS("PASS", "通过"),

                         /**  */
                         REJECTED("REJECTED", "驳回"),

                         /**  */
                         WAIT("WAIT", "待审核"),
                         /**  */
                         COMPLETE("COMPLETE", "完成");

    AuditResult(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public AuditResult getByCode(String code) {
        AuditResult[] values = AuditResult.values();
        for (AuditResult value : values) {
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
