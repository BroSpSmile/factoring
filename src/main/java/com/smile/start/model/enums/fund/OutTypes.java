/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.fund;

import org.apache.commons.lang3.StringUtils;

/**
 * 退出类型
 * @author smile.jing
 * @version $Id: ProjectItemType.java, v 0.1 Jan 28, 2019 3:49:43 PM smile.jing Exp $
 */
public enum OutTypes {
                      /** 未退出 */
                      NOT_OUT("NOT_OUT", "未退出"),

                      /** 并购退出 */
                      MERGER_OUT("MERGER_OUT", "并购退出"),

                      /** 回购退出 */
                      BUYBACK("BUYBACK", "回购退出"),

                      /** 上市退出 */
                      LIST_OUT("LIST_OUT", "上市退出");

    OutTypes(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public static OutTypes getByCode(String code) {
        OutTypes[] values = OutTypes.values();
        for (OutTypes value : values) {
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
