package com.smile.start.model.enums;

/**
 * 直投归档审核流程
 *
 * @author smile.jing
 * @version $Id: TuneUpFlowEnum.java, v 0.1 Mar 5, 2019 12:30:14 PM smile.jing Exp $
 */
public enum FundFileFlowEnum {

                          FILE_APPLY(0, "提出归档申请") {
                              @Override
                              public FundFileFlowEnum getNextStatus() {
                                  return REVIEWER_AUDIT;
                              }

                              @Override
                              public FundFileFlowEnum getDefaultRejectStatus() {
                                  return null;
                              }
                          },
                          REVIEWER_AUDIT(1, "复核人审核") {
                              @Override
                              public FundFileFlowEnum getNextStatus() {
                                  return FILE_LEGAL_AUDIT;
                              }

                              @Override
                              public FundFileFlowEnum getDefaultRejectStatus() {
                                  return FILE_APPLY;
                              }
                          },
                          FILE_LEGAL_AUDIT(2, "法务风控审核") {
                              @Override
                              public FundFileFlowEnum getNextStatus() {
                                  return FILE_OFFICER;
                              }

                              @Override
                              public FundFileFlowEnum getDefaultRejectStatus() {
                                  return FILE_APPLY;
                              }
                          },
                          FILE_OFFICER(3, "办公室专员归档") {
                              @Override
                              public FundFileFlowEnum getNextStatus() {
                                  return null;
                              }

                              @Override
                              public FundFileFlowEnum getDefaultRejectStatus() {
                                  return FILE_LEGAL_AUDIT;
                              }
                          };

    private int    value;

    private String desc;

    FundFileFlowEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static FundFileFlowEnum fromValue(int value) {
        for (FundFileFlowEnum statusEnum : FundFileFlowEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum;
            }
        }
        return null;
    }

    public abstract FundFileFlowEnum getDefaultRejectStatus();

    public abstract FundFileFlowEnum getNextStatus();
}
