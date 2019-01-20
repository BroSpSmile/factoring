/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 会议状态枚举
 * @author smile.jing
 * @version $Id: MeetingStatus.java, v 0.1 Jan 17, 2019 6:55:41 PM smile.jing Exp $
 */
public enum MeetingStatus {
                           /** 未开始 */
                           PLAN("PLAN", "未开始"),
                           /** 进行中 */
                           MEETING("MEETING", "进行中"),
                           /** 已结束 */
                           END("END", "已结束"),
                           /** 已取消 */
                           CANCELLED("CANCELLED", "已取消");

    MeetingStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public MeetingStatus getByCode(String code) {
        MeetingStatus[] values = MeetingStatus.values();
        for (MeetingStatus value : values) {
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
