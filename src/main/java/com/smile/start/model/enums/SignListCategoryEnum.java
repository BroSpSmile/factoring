package com.smile.start.model.enums;

/**
 * 签署清单类别
 * @author Joseph
 * @version v1.0 2019/4/14 21:20, SignListCategoryEnum.java
 * @since 1.8
 */
public enum SignListCategoryEnum {

    CREDITOR(1, "债权人"),
    OBLIGOR(2, "债务人"),
    INTRA_DECISIONS(3, "内部决策"),
    SIGN_FILE(4, "签署文件"),
    BASIS_FOR_PAYMENT(5, "出款依据"),
    OTHER(6, "其他");

    private int value;
    private String desc;

    SignListCategoryEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SignListCategoryEnum fromValue(int value) {
        for(SignListCategoryEnum signListCategoryEnum : SignListCategoryEnum.values()) {
            if(signListCategoryEnum.getValue() == value) {
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
