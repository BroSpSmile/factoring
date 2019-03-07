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
public enum FilingProgress {
    /**
     * 已放款状态可提出归档申请，即待归档状态
     */
    LOAN("LOAN", "待归档", 11),
    /**
     *
     */
    FILE_APPLY("FILE_APPLY", "提出归档申请", 12),
    /**
     *
     */
    FILE_LEGAL_AUDIT("FILE_LEGAL_AUDIT", "法务分控审核", 13),
    /**
     *
     */
    FILE_OFFICER("FILE_OFFICER", "办公室专员归档", 14),
    /**
     *
     */
    FILE_COMPLETE("FILE_COMPLETE", "归档完成", 15);

    FilingProgress(String code, String desc, int index) {
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
    public static FilingProgress getByCode(String code) {
        FilingProgress[] values = FilingProgress.values();
        for (FilingProgress value : values) {
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
     *
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
     *
     * @param progress
     * @return
     */
    public static Progress skip(Progress progress) {
        if (progress.getIndex() >= 9) {
            return progress;
        }
        return getByIndex(progress.getIndex() + 2);
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
