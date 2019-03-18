package com.smile.start.model.enums;

/**
 * 合同用印状态枚举
 * @author Joseph
 * @version v1.0 2019/3/17 21:22, SealStatusEnum.java
 * @since 1.8
 */
public enum SealStatusEnum {
    NOT_STAMPED(0, "未用印"),
    STAMPED(1, "已用印");

    private int value;
    private String desc;

    SealStatusEnum(int value, String desc) {
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
