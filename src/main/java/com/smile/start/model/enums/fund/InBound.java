/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.fund;

import org.apache.commons.lang3.StringUtils;

/**
 * 区内地区
 * @author smile.jing
 * @version $Id: ProjectItemType.java, v 0.1 Jan 28, 2019 3:49:43 PM smile.jing Exp $
 */
public enum InBound {
                     /** 高铁新城 */
                     GTXC("GTXC", "高铁新城"),

                     /** 阳澄湖度假区 */
                     YCHDJQ("YCHDJQ", "阳澄湖度假区"),

                     /** 阳澄湖镇 */
                     YCHZ("YCHZ", "阳澄湖镇"),

                     /** 望亭镇 */
                     WTZ("WTZ", "望亭镇"),

                     /** 黄埭镇 */
                     HDZ("HDZ", "黄埭镇"),

                     /** 漕湖街道 */
                     CHJD("CHJD", "漕湖街道"),

                     /** 北桥街道 */
                     BQJD("BQJD", "北桥街道"),

                     /** 元和街道 */
                     YHJD("YHJD", "元和街道"),

                     /** 黄桥街道 */
                     HQJD("HQJD", "黄桥街道"),

                     /** 渭塘镇 */
                     WET("WET", "渭塘镇"),

                     /** 太平街道 */
                     TPJD("TPJD", "太平街道"),;

    InBound(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public static InBound getByCode(String code) {
        InBound[] values = InBound.values();
        for (InBound value : values) {
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
