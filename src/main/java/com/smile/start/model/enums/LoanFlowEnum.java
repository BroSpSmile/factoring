package com.smile.start.model.enums;

/**
 * 尽调审核流程
 * @author smile.jing
 * @version $Id: TuneUpFlowEnum.java, v 0.1 Mar 5, 2019 12:30:14 PM smile.jing Exp $
 */
public enum LoanFlowEnum {
                            APPLY(1, "提出申请") {
                                @Override
                                public LoanFlowEnum getNextStatus() {
                                    return FINANCIAL_AUDIT;
                                }

                                @Override
                                public LoanFlowEnum getDefaultRejectStatus() {
                                    return null;
                                }
                            },
                            FINANCIAL_AUDIT(2, "财务审核") {
                                @Override
                                public LoanFlowEnum getNextStatus() {
                                    return FORENSIC_AUDIT;
                                }

                                @Override
                                public LoanFlowEnum getDefaultRejectStatus() {
                                    return APPLY;
                                }
                            },
                            FORENSIC_AUDIT(3, "法务审核") {
                                @Override
                                public LoanFlowEnum getNextStatus() {
                                    return null;
                                }

                                @Override
                                public LoanFlowEnum getDefaultRejectStatus() {
                                    return FORENSIC_AUDIT;
                                }
                            };

    private int    value;
    private String desc;

    LoanFlowEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static LoanFlowEnum fromValue(int value) {
        for (LoanFlowEnum statusEnum : LoanFlowEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum;
            }
        }
        return null;
    }

    public abstract LoanFlowEnum getDefaultRejectStatus();

    public abstract LoanFlowEnum getNextStatus();
}
