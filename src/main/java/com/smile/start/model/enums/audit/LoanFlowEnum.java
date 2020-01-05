package com.smile.start.model.enums.audit;

/**
 * 尽调审核流程
 * @author smile.jing
 * @version $Id: TuneUpFlowEnum.java, v 0.1 Mar 5, 2019 12:30:14 PM smile.jing Exp $
 */
public enum LoanFlowEnum {
                          APPLY(0, "提出申请") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return CHARGE_AUDIT;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return null;
                              }
                          },
                          CHARGE_AUDIT(1, "相宁永赢负责人审定") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return DEPARTMENT_AUDIT;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return APPLY;
                              }
                          },
                          DEPARTMENT_AUDIT(2, "保理公司负责人审核") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return null;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return CHARGE_AUDIT;
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
