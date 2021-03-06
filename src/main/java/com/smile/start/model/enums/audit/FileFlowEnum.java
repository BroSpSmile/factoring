package com.smile.start.model.enums.audit;

/**
 * 归档审核流程
 *
 * @author smile.jing
 * @version $Id: TuneUpFlowEnum.java, v 0.1 Mar 5, 2019 12:30:14 PM smile.jing Exp $
 */
public enum FileFlowEnum {

                          FILE_APPLY(0, "提出归档申请") {
                              @Override
                              public FileFlowEnum getNextStatus() {
                                  return REVIEWER_AUDIT;
                              }

                              @Override
                              public FileFlowEnum getDefaultRejectStatus() {
                                  return null;
                              }
                          },
                          REVIEWER_AUDIT(1, "复核人审核") {
                              @Override
                              public FileFlowEnum getNextStatus() {
                                  return FILE_LEGAL_AUDIT;
                              }

                              @Override
                              public FileFlowEnum getDefaultRejectStatus() {
                                  return FILE_APPLY;
                              }
                          },
                          FILE_LEGAL_AUDIT(2, "法务风控审核") {
                              @Override
                              public FileFlowEnum getNextStatus() {
                                  return FILE_OFFICER;
                              }

                              @Override
                              public FileFlowEnum getDefaultRejectStatus() {
                                  return FILE_APPLY;
                              }
                          },
                          FILE_OFFICER(3, "办公室专员归档") {
                              @Override
                              public FileFlowEnum getNextStatus() {
                                  return null;
                              }

                              @Override
                              public FileFlowEnum getDefaultRejectStatus() {
                                  return FILE_LEGAL_AUDIT;
                              }
                          };

    private int    value;

    private String desc;

    FileFlowEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static FileFlowEnum fromValue(int value) {
        for (FileFlowEnum statusEnum : FileFlowEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum;
            }
        }
        return null;
    }

    public abstract FileFlowEnum getDefaultRejectStatus();

    public abstract FileFlowEnum getNextStatus();
}
