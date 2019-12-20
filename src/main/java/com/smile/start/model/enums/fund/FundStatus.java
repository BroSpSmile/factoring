/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.fund;

/**
 * 
 * @author smile.jing
 * @version $Id: FundStatus.java, v 0.1 2019年8月10日 下午9:16:07 smile.jing Exp $
 */
public enum FundStatus {
                        /** 项目终止 */
                        STOP(-1, "STOP", "项目终止"),
                        /** 初步接触 */
                        SUSPEND(0, "SUSPEND", "暂停"),
                        /** 初步接触 */
                        INITIAL_CONTACT(1, "INITIAL_CONTACT", "初步接触"),
                        /** 签署保密协议 */
                        SIGN_CONFIDENTIALITY(2, "SIGN_CONFIDENTIALITY", "签署保密协议"),
                        /** 初步尽调 */
                        INITIAL_TUNING(3, "INITIAL_TUNING", "初步尽调"),
                        /** 项目立项 */
                        APPROVAL(4, "APPROVAL", "项目立项"),
                        /** 深入尽调 */
                        DEEP_TUNING(5, "DEEP_TUNING", "深入尽调"),
                        /** 部门内核 */
                        PARTMENT_AUDIT(6, "PARTMENT_AUDIT", "风控审核"),
                        /** 三重一大 */
                        AUDIT_MEETING(8, "AUDIT_MEETING", "三重一大"),
                        /** 国资委审批 */
                        SASAC_APPROVAL(9, "SASAC_APPROVAL", "国资委审批"),
                        /** 区政府审批 */
                        GOV_APPROVAL(10, "GOV_APPROVAL", "区政府审批"),
                        /** 合同签署 */
                        CONTRACT_SIGN(11, "CONTRACT_SIGN", "合同签署"),
                        /** 付款通知 */
                        PAYMENT(12, "PAYMENT", "付款通知"),
                        /** 支付打款 */
                        FUND_LOAN(13, "FUND_LOAN", "支付打款"),
                        /** 验资/信息变更 */
                        INFO_CHANGE(14, "INFO_CHANGE", "验资/信息变更"),
                        /** 项目归档 */
                        FILE(15, "FILE", "项目归档"),
                        /** 投后管理 */
                        POST_INVESTMENT(16, "POST_INVESTMENT", "投后管理"),
                        /** 项目退出 */
                        OUT(17, "OUT", "项目退出"),;

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
