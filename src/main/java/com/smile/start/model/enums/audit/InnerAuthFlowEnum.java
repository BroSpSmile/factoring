package com.smile.start.model.enums.audit;

/**
 * 尽调审核流程
 * @author smile.jing
 * @version $Id: TuneUpFlowEnum.java, v 0.1 Mar 5, 2019 12:30:14 PM smile.jing Exp $
 */
public enum InnerAuthFlowEnum {
                               APPLY(0, "提出申请") {
                                   @Override
                                   public InnerAuthFlowEnum getNextStatus() {
                                       return FINANCIAL_AUDIT;
                                   }

                                   @Override
                                   public InnerAuthFlowEnum getDefaultRejectStatus() {
                                       return null;
                                   }
                               },
                               DEPARTMENT_AUDIT(1, "直投部门负责人审核") {
                                   @Override
                                   public InnerAuthFlowEnum getNextStatus() {
                                       return FINANCIAL_AUDIT;
                                   }

                                   @Override
                                   public InnerAuthFlowEnum getDefaultRejectStatus() {
                                       return APPLY;
                                   }
                               },
                               FINANCIAL_AUDIT(3, "财务风控审核") {
                                   @Override
                                   public InnerAuthFlowEnum getNextStatus() {
                                       return DEPARTMENT_AUDIT;
                                   }

                                   @Override
                                   public InnerAuthFlowEnum getDefaultRejectStatus() {
                                       return APPLY;
                                   }
                               },
                               FORENSIC_AUDIT(4, "法务风控审核") {
                                   @Override
                                   public InnerAuthFlowEnum getNextStatus() {
                                       return null;
                                   }

                                   @Override
                                   public InnerAuthFlowEnum getDefaultRejectStatus() {
                                       return APPLY;
                                   }
                               };
    /** */
    private int    value;

    /** */
    private String desc;

    /**
     * 构造函数
     * @param value
     * @param desc
     */
    InnerAuthFlowEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static InnerAuthFlowEnum fromValue(int value) {
        for (InnerAuthFlowEnum statusEnum : InnerAuthFlowEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum;
            }
        }
        return null;
    }

    public abstract InnerAuthFlowEnum getDefaultRejectStatus();

    public abstract InnerAuthFlowEnum getNextStatus();
}
