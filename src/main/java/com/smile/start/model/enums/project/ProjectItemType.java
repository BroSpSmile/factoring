/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.project;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目附件枚举
 * @author smile.jing
 * @version $Id: ProjectItemType.java, v 0.1 Jan 28, 2019 3:49:43 PM smile.jing Exp $
 */
public enum ProjectItemType {
                             /** 项目附件 */
                             PROJECT("PROJECT", "项目附件"),

                             /**  */
                             TUNEUP("TUNEUP", "尽调文件"),

                             /**  */
                             LOAN("LOAN", "放款附件"),

                             /** */
                             INITIATE("INITIATE", "立项纪要"),

                             /** */
                             DIRECTORS("DIRECTORS", "三重一大附件"),

                             /** */
                             FILE("FILE", "保理归档文件"),

                             /** */
                             FUND_FILE("FUND_FILE", "直投归档文件"),

                             /** */
                             DRAWUP("DRAWUP", "合同文件"),

                             /** */
                             SIGN_CONFIDENTIALITY("SIGN_CONFIDENTIALITY", "保密协议"),

                             /** 初步尽调 */
                             INITIAL_TUNING("INITIAL_TUNING", "初步尽调文件"),

                             /** 深入尽调文件 */
                             DEEP_TUNING("DEEP_TUNING", "深入尽调文件"),

                             /** 合同文件 */
                             CONTRACT_SIGN("CONTRACT_SIGN", "合同文件"),

                             /** 付款通知书 */
                             PAYMENT("PAYMENT", "付款文件"),

                             /** 投后文件 */
                             POST_INVESTMENT("POST_INVESTMENT", "投后文件"),

                             /** */
                             COLLECTION("COLLECTION", "收款附件"),;

    ProjectItemType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public static ProjectItemType getByCode(String code) {
        ProjectItemType[] values = ProjectItemType.values();
        for (ProjectItemType value : values) {
            if (StringUtils.equals(code, value.code)) {
                return value;
            }
        }
        return null;
    }

    /** 状态码 */
    private String code;

    /** 状态描述 */
    private String desc;

    /** 简码 */
    private String scode;

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

    /**
     * Getter method for property <tt>scode</tt>.
     * 
     * @return property value of scode
     */
    public String getScode() {
        return scode;
    }

}
