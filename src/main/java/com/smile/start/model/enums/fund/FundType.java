/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.fund;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目类型
 * @author smile.jing
 * @version $Id: ProjectItemType.java, v 0.1 Jan 28, 2019 3:49:43 PM smile.jing Exp $
 */
public enum FundType {
                      /** TMT */
                      TMT("TMT", "TMT"),

                      /** 智能制造 */
                      ZNZ("ZNZ", "智能制造"),

                      /** 生物医药 */
                      SWY("SWY", "生物医药");

    FundType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public static FundType getByCode(String code) {
        FundType[] values = FundType.values();
        for (FundType value : values) {
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
