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
    TOBEFILED("TOBEFILED", "提出归档申请"),
    FILE("FILE", "法务分控审核"),
    FILEAUDIT("FILEAUDIT", "办公室专员归档"),
    FILECOMPLETE("FILECOMPLETE", "归档完成");

    FilingProgress(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     *
     * @param code
     * @return
     */
    public FilingProgress getByCode(String code) {
        FilingProgress[] values = FilingProgress.values();
        for (FilingProgress value : values) {
            if (StringUtils.equals(code, value.code)) {
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
