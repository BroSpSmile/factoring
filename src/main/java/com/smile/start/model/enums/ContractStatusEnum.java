package com.smile.start.model.enums;

/**
 * 合同状态枚举
 * @author Joseph
 * @version v1.0 2019/2/22 15:46, ContractStatusEnum.java
 * @since 1.8
 */
public enum ContractStatusEnum {
                                NEW(0, "拟定") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return DEPARTMENT_AUDIT;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return null;
                                    }
                                },
                                APPLY(1, "提出申请") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return DEPARTMENT_AUDIT;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return NEW;
                                    }
                                },
                                DEPARTMENT_AUDIT(2, "部门初审") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return LEGAL_AUDIT;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return APPLY;
                                    }
                                },
                                CHARGE_AUDIT(3, "债权负责人审核") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return LEGAL_AUDIT;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return DEPARTMENT_AUDIT;
                                    }
                                },
                                LEGAL_AUDIT(4, "法务风控审核") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return VICE_GENERAL_MANAGER_AUDIT;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return CHARGE_AUDIT;
                                    }
                                },
                                VICE_GENERAL_MANAGER_AUDIT(5, "集团副总审核") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return GENERAL_MANAGER_AUDIT;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return LEGAL_AUDIT;
                                    }
                                },
                                GENERAL_MANAGER_AUDIT(6, "集团正总审核") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return SIGN;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return VICE_GENERAL_MANAGER_AUDIT;
                                    }
                                },
                                FINISH(7, "完成") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return null;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return null;
                                    }
                                },
                                //    NOTIFY_OFFICE(7, "通知办公室") {
                                //        @Override
                                //        public ContractStatusEnum getNextStatus() {
                                //            return SIGN;
                                //        }
                                //
                                //        @Override
                                //        public ContractStatusEnum getDefaultRejectStatus() {
                                //            return null;
                                //        }
                                //    },
                                SIGN(8, "签署") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return null;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return null;
                                    }
                                },
                                SIGN_FINISH(9, "签署完成") {
                                    @Override
                                    public ContractStatusEnum getNextStatus() {
                                        return null;
                                    }

                                    @Override
                                    public ContractStatusEnum getDefaultRejectStatus() {
                                        return null;
                                    }
                                };

    private int    value;
    private String desc;

    ContractStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ContractStatusEnum fromValue(int value) {
        for (ContractStatusEnum statusEnum : ContractStatusEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum;
            }
        }
        return null;
    }

    public abstract ContractStatusEnum getDefaultRejectStatus();

    public abstract ContractStatusEnum getNextStatus();
}
