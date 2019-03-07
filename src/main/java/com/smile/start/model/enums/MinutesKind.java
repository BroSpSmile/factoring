/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 会议模板枚举
 * 
 * @author smile.jing
 * @version $Id: MinutesKind.java, v 0.1 Jan 8, 2019 10:45:18 PM smile.jing Exp
 *          $
 */
public enum MinutesKind {
                         /** 党总支会议模板 */
                         PARTYMODEL("PARTYMODEL", "党总支会议模板"),
                         /** 董事会会议纪要模板 */
                         SHAREMODEL("SHAREMODEL", "董事会会议纪要模板"),
                         /** 董事会决议模板 */
                         RESOLUTIONMODEL("RESOLUTIONMODEL", "董事会决议模板"),
                         /** 自定义模板 */
                         CUSTOM("CUSTOM", "自定义模板");

    MinutesKind(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public MinutesKind getByCode(String code) {
        MinutesKind[] values = MinutesKind.values();
        for (MinutesKind value : values) {
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
