package com.smile.start.model.enums;

/**
 * 合同状态枚举
 * @author Joseph
 * @version v1.0 2019/2/22 15:46, ContractStatusEnum.java
 * @since 1.8
 */
public enum ContractStatusEnum {
    APPLY(1, "提出申请"),
    DEPARTMENT_AUDIT(2, "部门初审"),
    LEGAL_AUDIT(3, "法务风控审核"),
    VICE_GENERAL_MANAGER_AUDIT(4, "集团副总审核"),
    GENERAL_MANAGER_AUDIT(5, "集团正总审核"),
    FINISH(6, "完成"),
    NOTIFY_OFFICE(7, "通知办公室"),
    SIGN(8, "签署"),
    SIGN_FINISH(9, "签署完成");

    private int value;
    private String desc;

    ContractStatusEnum(int value, String desc) {
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
