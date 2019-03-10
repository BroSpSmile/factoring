/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目状态枚举,归档流程使用
 *
 * @author smile.jing
 * @version $Id: Progress.java, v 0.1 Jan 8, 2019 9:10:26 PM smile.jing Exp $
 */
public enum FilingSubProgress {
    /**
     *
     */
    FILE_TOBE_APPLY("FILE_TOBE_APPLY", "待提出归档申请", 0),

    /**
     *
     */
    FILE_APPLY("FILE_APPLY", "提出归档申请", 1),
    /**
     *
     */
    FILE_LEGAL_AUDIT("FILE_LEGAL_AUDIT", "法务分控审核", 2),
    /**
     *
     */
    FILE_OFFICER("FILE_OFFICER", "办公室专员归档", 3),
    /**
     *
     */
    FILE_COMPLETE("FILE_COMPLETE", "归档完成", 4);

    FilingSubProgress(String code, String desc, int index) {
        this.code = code;
        this.desc = desc;
        this.index = index;
    }


    /**
     * 获取下一流程
     *
     * @param progress
     * @return
     */
    public static FilingSubProgress next(FilingSubProgress progress) {
        if (progress.getIndex() > 4) {
            return null;
        }
        return getByIndex(progress.getIndex() + 1);
    }

    /**
     * 获取下一流程
     *
     * @param progress
     * @return
     */
    public static FilingSubProgress last(FilingSubProgress progress) {
        if (progress.getIndex() < 1) {
            return null;
        }
        return getByIndex(progress.getIndex() - 1);
    }


    /**
     * getByCode
     *
     * @param code
     * @return
     */
    public static FilingSubProgress getByCode(String code) {
        FilingSubProgress[] values = FilingSubProgress.values();
        for (FilingSubProgress value : values) {
            if (StringUtils.equals(code, value.code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * @param index
     * @return
     */
    public static FilingSubProgress getByIndex(int index) {
        FilingSubProgress[] values = FilingSubProgress.values();
        for (FilingSubProgress value : values) {
            if (index == value.getIndex()) {
                return value;
            }
        }
        return null;
    }

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String desc;

    /**
     * 流程索引
     */
    private int index;

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
