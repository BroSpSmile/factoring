package com.smile.start.model.enums;

/**
 * 合同附件类型枚举
 * @author Joseph
 * @version v1.0 2019/2/28 20:17, ContractAttachTypeEnum.java
 * @since 1.8
 */
public enum ContractAttachTypeEnum {
    STANDARD(1, "标准"),
    USER_DEFINED(2, "自定义");

    private int value;
    private String desc;

    ContractAttachTypeEnum(int value, String desc) {
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
