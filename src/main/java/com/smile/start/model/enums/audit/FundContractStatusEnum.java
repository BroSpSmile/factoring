package com.smile.start.model.enums.audit;

/**
 * 合同状态枚举
 * @author Joseph
 * @version v1.0 2019/2/22 15:46, ContractStatusEnum.java
 * @since 1.8
 */
public enum FundContractStatusEnum {
                                    APPLY(0, "提出申请") {
                                        @Override
                                        public FundContractStatusEnum getNextStatus() {
                                            return DEPARTMENT_AUDIT;
                                        }

                                        @Override
                                        public FundContractStatusEnum getDefaultRejectStatus() {
                                            return null;
                                        }
                                    },
                                    DEPARTMENT_AUDIT(1, "部门初审") {
                                        @Override
                                        public FundContractStatusEnum getNextStatus() {
                                            return LEGAL_AUDIT;
                                        }

                                        @Override
                                        public FundContractStatusEnum getDefaultRejectStatus() {
                                            return APPLY;
                                        }
                                    },
                                    CHARGE_AUDIT(2, "直投负责人审核") {
                                        @Override
                                        public FundContractStatusEnum getNextStatus() {
                                            return LEGAL_AUDIT;
                                        }

                                        @Override
                                        public FundContractStatusEnum getDefaultRejectStatus() {
                                            return DEPARTMENT_AUDIT;
                                        }
                                    },
                                    LEGAL_AUDIT(3, "法务风控审核") {
                                        @Override
                                        public FundContractStatusEnum getNextStatus() {
                                            return VICE_GENERAL_MANAGER_AUDIT;
                                        }

                                        @Override
                                        public FundContractStatusEnum getDefaultRejectStatus() {
                                            return CHARGE_AUDIT;
                                        }
                                    },
                                    VICE_GENERAL_MANAGER_AUDIT(4, "集团副总审核") {
                                        @Override
                                        public FundContractStatusEnum getNextStatus() {
                                            return GENERAL_MANAGER_AUDIT;
                                        }

                                        @Override
                                        public FundContractStatusEnum getDefaultRejectStatus() {
                                            return LEGAL_AUDIT;
                                        }
                                    },
                                    GENERAL_MANAGER_AUDIT(5, "集团正总审核") {
                                        @Override
                                        public FundContractStatusEnum getNextStatus() {
                                            return null;
                                        }

                                        @Override
                                        public FundContractStatusEnum getDefaultRejectStatus() {
                                            return VICE_GENERAL_MANAGER_AUDIT;
                                        }
                                    };

    private int    value;
    private String desc;

    FundContractStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static FundContractStatusEnum fromValue(int value) {
        for (FundContractStatusEnum statusEnum : FundContractStatusEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum;
            }
        }
        return null;
    }

    public abstract FundContractStatusEnum getDefaultRejectStatus();

    public abstract FundContractStatusEnum getNextStatus();
}
