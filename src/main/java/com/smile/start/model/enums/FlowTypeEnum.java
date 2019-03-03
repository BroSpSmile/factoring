package com.smile.start.model.enums;

/**
 * 流程类型枚举
 * @author Joseph
 * @version v1.0 2019/2/22 16:25, FlowTypeEnum.java
 * @since 1.8
 */
public enum FlowTypeEnum {
                          /**  */
                          CONTRACT(1, "合同审核流程 "),
                          /**  */
                          TUNEUP(2, "尽调审核流程"),

                          LOAN(3, "放款审核流程"),

                          FILING(4, "归档审核流程");

    private int    value;
    private String desc;

    FlowTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static FlowTypeEnum fromValue(int flowType) {
        for (FlowTypeEnum flowTypeEnum : FlowTypeEnum.values()) {
            if (flowTypeEnum.getValue() == flowType) {
                return flowTypeEnum;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
