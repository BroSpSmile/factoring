package com.smile.start.model.enums;

/**
 * 签署清单类别
 * @author Joseph
 * @version v1.0 2019/4/14 21:20, SignListCategoryEnum.java
 * @since 1.8
 */
public enum SignListCategoryEnum {

                                  CREDITOR(1, "立项材料"),

                                  OBLIGOR(2, "尽调报告"),

                                  INTRA_DECISIONS(3, "风控意见材料"),

                                  SIGN_FILE(4, "投决会材料"),

                                  BASIS_FOR_PAYMENT(5, "协议签署及打款材料"),

                                  SFJD(6, "三方尽调材料"),

                                  OTHER(7, "其他"),;

    private int    value;
    private String desc;

    SignListCategoryEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SignListCategoryEnum fromValue(int value) {
        for (SignListCategoryEnum signListCategoryEnum : SignListCategoryEnum.values()) {
            if (signListCategoryEnum.getValue() == value) {
                return signListCategoryEnum;
            }
        }
        return OTHER;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
