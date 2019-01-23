package com.smile.start.model.enums;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, DeleteFlagEnum.java
 * @since 1.8
 */
public enum DeleteFlagEnum {
    DLETED(1, "已删除"),
    UNDELETED(0, "未删除");

    private int value;
    private String desc;

    DeleteFlagEnum(int value, String desc) {
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
