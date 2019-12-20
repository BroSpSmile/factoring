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
                                  return FINANCIAL_AUDIT;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return null;
                              }
                          },
                          DEPARTMENT_AUDIT(1, "部门负责人审核") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return CHARGE_AUDIT;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return APPLY;
                              }
                          },
                          CHARGE_AUDIT(2, "债权负责人审核") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return FINANCIAL_AUDIT;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return DEPARTMENT_AUDIT;
                              }
                          },
                          FINANCIAL_AUDIT(3, "财务风控审核") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return FORENSIC_AUDIT;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return CHARGE_AUDIT;
                              }
                          },
                          FORENSIC_AUDIT(4, "法务风控审核") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return FINANCIAL_MAIN_AUDIT;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return FINANCIAL_AUDIT;
                              }
                          },
                          FINANCIAL_MAIN_AUDIT(5, "财务负责人审核") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return VP_AUDIT;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return FORENSIC_AUDIT;
                              }
                          },
                          VP_AUDIT(6, "集团副总审核") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return CEO_AUDIT;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return FINANCIAL_MAIN_AUDIT;
                              }
                          },
                          CEO_AUDIT(7, "集团正总审核") {
                              @Override
                              public LoanFlowEnum getNextStatus() {
                                  return null;
                              }

                              @Override
                              public LoanFlowEnum getDefaultRejectStatus() {
                                  return VP_AUDIT;
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
