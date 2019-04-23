/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 会议类型
 * @author smile.jing
 * @version $Id: MeetingKind.java, v 0.1 Feb 24, 2019 9:21:41 PM smile.jing Exp $
 */
/**
 * 
 * @author smile.jing
 * @version $Id: MeetingKind.java, v 0.1 Mar 7, 2019 10:25:58 PM smile.jing Exp $
 */
public enum MeetingKind {
                         /** 立项会议 */
                         APPROVAL("APPROVAL", "立项会议"),
                         /** 董事会决议 */
                         DIRECTORS("DIRECTORS", "三重一大会议");

    MeetingKind(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public MeetingKind getByCode(String code) {
        MeetingKind[] values = MeetingKind.values();
        for (MeetingKind value : values) {
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
