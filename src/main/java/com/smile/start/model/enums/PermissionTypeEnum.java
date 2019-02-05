package com.smile.start.model.enums;

/**
 * @author Joseph
 * @version v1.0 2019/1/29 21:09, PermissionTypeEnum.java
 * @since 1.8
 */
public enum  PermissionTypeEnum {
    ENUM(1, "菜单"), BUTTON(2, "按钮");

    private int value;
    private String desc;

    PermissionTypeEnum(int value, String desc) {
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
