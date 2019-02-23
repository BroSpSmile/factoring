package com.smile.start.model.common;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 16:23, StatusInfo.java
 * @since 1.8
 */
public class StatusInfo implements Serializable {
    private static final long serialVersionUID = 311042515663034872L;

    /**
     * 状态值
     */
    private Integer value;

    /**
     * 状态描述
     */
    private String desc;

    public StatusInfo(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
