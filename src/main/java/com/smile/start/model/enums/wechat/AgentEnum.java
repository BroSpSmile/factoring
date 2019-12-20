/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.wechat;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AgentEnum
 * @date Date : 2019年12月19日 11:40
 */
public enum AgentEnum {
                       /** APP应用*/
                       APP(1000003L, "APP应用"),

                       /**小程序应用 */
                       MINIPROGRAM(1000002L, "小程序应用");

    /** 类型值 */
    private long   value;

    /** 状态描述 */
    private String desc;

    AgentEnum(long value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */
    public long getValue() {
        return value;
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
