/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 节点状态
 * @author smile.jing
 * @version $Id: ProgressStatus.java, v 0.1 Mar 2, 2019 9:12:40 PM smile.jing Exp $
 */
public enum StepStatus {
                            /**  */
                            LATER("LATER", "后补"),

                            /**  */
                            COMPLETED("COMPLETED", "已完成"),

                            /**  */
                            PROCESSING("PROCESSING", "处理中");

    StepStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public StepStatus getByCode(String code) {
        StepStatus[] values = StepStatus.values();
        for (StepStatus value : values) {
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
