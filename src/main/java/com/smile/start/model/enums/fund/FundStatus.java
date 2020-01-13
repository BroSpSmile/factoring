/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.fund;

/**
 * 
 * @author smile.jing
 * @version $Id: EnumItem.java, v 0.1 2019年8月10日 下午9:16:07 smile.jing Exp $
 */
public enum FundStatus {
                        /** 项目终止 */
                        STOP(-1, "STOP", "项目终止"),
                        /** 暂停 */
                        SUSPEND(0, "SUSPEND", "暂停"),
                        /** 初步接触 */
                        INITIAL_CONTACT(1, "INITIAL_CONTACT", "初步接触"),
                        /** 初步意见 */
                        INITIAL_OPINION(2, "INITIAL_OPINION", "初步意见"),
                        /** 签署保密协议 */
                        SIGN_CONFIDENTIALITY(3, "SIGN_CONFIDENTIALITY", "签署保密协议"),
                        /** 初步尽调 */
                        INITIAL_TUNING(4, "INITIAL_TUNING", "初步尽调"),
                        /** 项目立项 */
                        APPROVAL(5, "APPROVAL", "项目立项"),
                        /** 深入尽调 */
                        DEEP_TUNING(6, "DEEP_TUNING", "深入尽调"),
                        /** 风控审核 */
                        PARTMENT_AUDIT(7, "PARTMENT_AUDIT", "风控审核"),
                        /** 三重一大 */
                        AUDIT_MEETING(8, "AUDIT_MEETING", "三重一大"),
                        /** 国资委审批 */
                        SASAC_APPROVAL(9, "SASAC_APPROVAL", "国资委审批"),
                        /** 区政府审批 */
                        GOV_APPROVAL(10, "GOV_APPROVAL", "区政府审批"),
                        /** 合同签署 */
                        CONTRACT_SIGN(11, "CONTRACT_SIGN", "合同签署"),
                        /** 合同审核 */
                        CONTRACT_AUDIT(12, "CONTRACT_AUDIT", "合同审核"),
                        /** 盖章合同上传 */
                        CONTRACT_SIGNED(13, "CONTRACT_SIGNED", "盖章合同上传"),
                        /** 付款通知 */
                        PAYMENT(14, "PAYMENT", "付款通知"),
                        /** 支付打款 */
                        FUND_LOAN(15, "FUND_LOAN", "支付打款"),
                        /** 验资/信息变更 
                        INFO_CHANGE(16, "INFO_CHANGE", "验资/信息变更"),*/
                        /** 项目归档 */
                        FILE(16, "FILE", "项目归档"),
                        /** 项目归档 */
                        FILE_AUDIT(17, "FILE_AUDIT", "归档审核"),
                        /** 投后管理 */
                        POST_INVESTMENT(18, "POST_INVESTMENT", "投后管理"),
                        /** 项目退出 */
                        OUT(19, "OUT", "项目退出"),;

    FundStatus(int index, String code, String desc) {
        this.index = index;
        this.code = code;
        this.desc = desc;
    }

    /** index */
    private int    index;

    /** code */
    private String code;

    /** 描述 */
    private String desc;

    /**
     * 
     * @param index
     * @return
     */
    public static FundStatus getByIndex(int index) {
        FundStatus[] values = FundStatus.values();
        for (FundStatus status : values) {
            if (status.getIndex() == index) {
                return status;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>index</tt>.
     * 
     * @return property value of index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     * 
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

}
