package com.smile.start.model.enums;

/**
 * 直投放款审核流程
 * @author smile.jing
 * @version $Id: TuneUpFlowEnum.java, v 0.1 Mar 5, 2019 12:30:14 PM smile.jing Exp $
 */
public enum FundLoanFlowEnum {
                              APPLY(0, "提出申请") {
                                  @Override
                                  public FundLoanFlowEnum getNextStatus() {
                                      return FINANCIAL_AUDIT;
                                  }

                                  @Override
                                  public FundLoanFlowEnum getDefaultRejectStatus() {
                                      return null;
                                  }
                              },
                              DEPARTMENT_AUDIT(1, "部门负责人审核") {
                                  @Override
                                  public FundLoanFlowEnum getNextStatus() {
                                      return CHARGE_AUDIT;
                                  }

                                  @Override
                                  public FundLoanFlowEnum getDefaultRejectStatus() {
                                      return APPLY;
                                  }
                              },
                              CHARGE_AUDIT(2, "直投负责人审核") {
                                  @Override
                                  public FundLoanFlowEnum getNextStatus() {
                                      return FINANCIAL_AUDIT;
                                  }

                                  @Override
                                  public FundLoanFlowEnum getDefaultRejectStatus() {
                                      return DEPARTMENT_AUDIT;
                                  }
                              },
                              FINANCIAL_AUDIT(3, "财务风控审核") {
                                  @Override
                                  public FundLoanFlowEnum getNextStatus() {
                                      return FORENSIC_AUDIT;
                                  }

                                  @Override
                                  public FundLoanFlowEnum getDefaultRejectStatus() {
                                      return CHARGE_AUDIT;
                                  }
                              },
                              FORENSIC_AUDIT(4, "法务风控审核") {
                                  @Override
                                  public FundLoanFlowEnum getNextStatus() {
                                      return FINANCIAL_MAIN_AUDIT;
                                  }

                                  @Override
                                  public FundLoanFlowEnum getDefaultRejectStatus() {
                                      return FINANCIAL_AUDIT;
                                  }
                              },
                              FINANCIAL_MAIN_AUDIT(5, "财务负责人审核") {
                                  @Override
                                  public FundLoanFlowEnum getNextStatus() {
                                      return VP_AUDIT;
                                  }

                                  @Override
                                  public FundLoanFlowEnum getDefaultRejectStatus() {
                                      return FORENSIC_AUDIT;
                                  }
                              },
                              VP_AUDIT(6, "集团副总审核") {
                                  @Override
                                  public FundLoanFlowEnum getNextStatus() {
                                      return CEO_AUDIT;
                                  }

                                  @Override
                                  public FundLoanFlowEnum getDefaultRejectStatus() {
                                      return FINANCIAL_MAIN_AUDIT;
                                  }
                              },
                              CEO_AUDIT(7, "集团正总审核") {
                                  @Override
                                  public FundLoanFlowEnum getNextStatus() {
                                      return null;
                                  }

                                  @Override
                                  public FundLoanFlowEnum getDefaultRejectStatus() {
                                      return VP_AUDIT;
                                  }
                              };

    private int    value;
    private String desc;

    FundLoanFlowEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static FundLoanFlowEnum fromValue(int value) {
        for (FundLoanFlowEnum statusEnum : FundLoanFlowEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum;
            }
        }
        return null;
    }

    public abstract FundLoanFlowEnum getDefaultRejectStatus();

    public abstract FundLoanFlowEnum getNextStatus();
}
