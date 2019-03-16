package com.smile.start.model.enums;

/**
 * 尽调审核流程
 * @author smile.jing
 * @version $Id: TuneUpFlowEnum.java, v 0.1 Mar 5, 2019 12:30:14 PM smile.jing Exp $
 */
public enum TuneUpFlowEnum {
                            APPLY(0, "提出申请") {
                                @Override
                                public TuneUpFlowEnum getNextStatus() {
                                    return FINANCIAL_AUDIT;
                                }

                                @Override
                                public TuneUpFlowEnum getDefaultRejectStatus() {
                                    return null;
                                }
                            },
                            DEPARTMENT_AUDIT(1, "部门负责人审核") {
                                @Override
                                public TuneUpFlowEnum getNextStatus() {
                                    return CHARGE_AUDIT;
                                }

                                @Override
                                public TuneUpFlowEnum getDefaultRejectStatus() {
                                    return APPLY;
                                }
                            },
                            CHARGE_AUDIT(2, "债权负责人审核") {
                                @Override
                                public TuneUpFlowEnum getNextStatus() {
                                    return FINANCIAL_AUDIT;
                                }

                                @Override
                                public TuneUpFlowEnum getDefaultRejectStatus() {
                                    return DEPARTMENT_AUDIT;
                                }
                            },
                            FINANCIAL_AUDIT(3, "财务风控审核") {
                                @Override
                                public TuneUpFlowEnum getNextStatus() {
                                    return DEPARTMENT_AUDIT;
                                }

                                @Override
                                public TuneUpFlowEnum getDefaultRejectStatus() {
                                    return APPLY;
                                }
                            },
                            FORENSIC_AUDIT(4, "法务风控审核") {
                                @Override
                                public TuneUpFlowEnum getNextStatus() {
                                    return null;
                                }

                                @Override
                                public TuneUpFlowEnum getDefaultRejectStatus() {
                                    return FINANCIAL_AUDIT;
                                }
                            };

    private int    value;
    private String desc;

    TuneUpFlowEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static TuneUpFlowEnum fromValue(int value) {
        for (TuneUpFlowEnum statusEnum : TuneUpFlowEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum;
            }
        }
        return null;
    }

    public abstract TuneUpFlowEnum getDefaultRejectStatus();

    public abstract TuneUpFlowEnum getNextStatus();
}
