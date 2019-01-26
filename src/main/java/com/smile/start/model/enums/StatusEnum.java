package com.smile.start.model.enums;

/**
 * 有效无效状态枚举
 * @author Joseph
 * @version v1.0 2019/1/26 10:34, StatusEnum.java
 * @since 1.8
 */
public enum StatusEnum {
    VALID(1, "有效"),
    INVALID(0, "无效");

    private int value;
    private String desc;

    StatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
