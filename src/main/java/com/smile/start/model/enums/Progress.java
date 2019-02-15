/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目状态枚举
 * @author smile.jing
 * @version $Id: Progress.java, v 0.1 Jan 8, 2019 9:10:26 PM smile.jing Exp $
 */
public enum Progress {

                      /**  */
                      INIT("INIT", "创建"),
                      /**  */
                      APPLY("APPLY", "立项申请"),
                      /**  */
                      EXAMINE("EXAMINE", "立项审核"),
                      /**  */
                      PASTMEETING("PASTMEETING", "三重一大"),
                      /**  */
                      DRAWUP("DRAWUP", "合同拟定"),
                      /**  */
                      CONTRACTAUDIT("CONTRACTAUDIT", "合同审核"),
                      /**  */
                      SIGN("SIGN", "签署"),
                      /**  */
                      LOAN("LOAN", "放款申请"),
                      /**  */
                      LOANAUDIT("LOANAUDIT", "放款审核"),

                      /** add by xioutman 添加归档类型 */
                      TOBEFILED("TOBEFILED", "待归档"),
                      FILE("FILE", "归档申请"),
                      FILEAUDIT("FILEAUDIT", "归档审核"),
                      FILECOMPLETE("FILECOMPLETE", "归档完成");

    Progress(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     *
     * @param code
     * @return
     */
    public Progress getByCode(String code) {
        Progress[] values = Progress.values();
        for (Progress value : values) {
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
