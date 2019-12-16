package com.smile.start.model.enums;

/**
 * 流程类型枚举
 * @author Joseph
 * @version v1.0 2019/2/22 16:25, FlowTypeEnum.java
 * @since 1.8
 */
public enum FlowTypeEnum {
                          /**  */
                          DRAWUP(1, "保理合同审核流程 "),

                          /**  */
                          TUNEUP(2, "保理尽调审核流程"),

                          /**  */
                          LOAN(3, "保理放款审核流程"),

                          /**  */
                          FILE(4, "保理归档审核流程"),

                          /** 直投部门内核 */
                          INNERAUTH(5, "直投部门内核流程"),

                          /** 直投部门内核 */
                          CONTRACT_SIGN(6, "直投合同审核流程"),

                          /** 直投放款审核流程 */
                          PAYMENT(7, "直投放款审核流程"),

                          /** 直投归档审核流程 */
                          FUND_FILE(8, "直投归档审核流程");

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
