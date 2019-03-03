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

                      /** 项目创建 */
                      INIT("INIT", "项目创建", 0),
                      /** 发起立项 */
                      INITIATE("INITIATE", "发起立项", 1),
                      /** 已立项 */
                      APPROVAL("APPROVAL", "已立项", 2),
                      /**  */
                      TUNEUP("TUNEUP", "尽调申请", 3),
                      /**  */
                      INVESTIGATION("INVESTIGATION", "尽调完成", 4),
                      /**  */
                      LATERMEETING("LATERMEETING", "待过会", 5),
                      /**  */
                      PASTMEETING("PASTMEETING", "已过会", 6),
                      /**  */
                      DRAFT("DRAFT", "草拟合同", 7),
                      /**  */
                      DRAWUP("DRAWUP", "合同拟定", 8),
                      /**  */
                      SIGN("SIGN", "已签署", 9),
                      /**  */
                      PENDINGLOAN("PENDINGLOAN", "放款申请", 10),
                      /**  */
                      LOAN("LOAN", "已放款", 11),
                      /** add by xioutman 添加归档类型 */
                      TOBEFILED("TOBEFILED", "待归档", 12),
                      /**  */
                      FILE("FILE", "归档申请", 13),
                      /**  */
                      FILEAUDIT("FILEAUDIT", "归档审核", 14),
                      /**  */
                      FILECOMPLETE("FILECOMPLETE", "归档完成", 15);

    Progress(String code, String desc, int index) {
        this.code = code;
        this.desc = desc;
        this.index = index;
    }

    /**
     * getByCode
     *
     * @param code
     * @return
     */
    public static Progress getByCode(String code) {
        Progress[] values = Progress.values();
        for (Progress value : values) {
            if (StringUtils.equals(code, value.code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 
     * @param index
     * @return
     */
    public static Progress getByIndex(int index) {
        Progress[] values = Progress.values();
        for (Progress value : values) {
            if (index == value.getIndex()) {
                return value;
            }
        }
        return null;
    }

    /**
     * 获取下一流程
     * @param progress
     * @return
     */
    public static Progress next(Progress progress) {
        if (progress.getIndex() >= 10) {
            return progress;
        }
        return getByIndex(progress.getIndex() + 1);
    }

    /**
     * 跳过下一流程
     * @param progress
     * @return
     */
    public static Progress skip(Progress progress) {
        if (progress.getIndex() >= 9) {
            return progress;
        }
        return getByIndex(progress.getIndex() + 2);
    }

    /** 状态码 */
    private String code;

    /** 状态描述 */
    private String desc;

    /** 流程索引 */
    private int    index;

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
     * Getter method for property <tt>index</tt>.
     * 
     * @return property value of index
     */
    public int getIndex() {
        return index;
    }

}
