/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.project;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目模式
 * 
 * @author smile.jing
 * @version $Id: ProjectModel.java, v 0.1 Jan 8, 2019 9:51:19 PM smile.jing Exp
 *          $
 */
public enum ProjectModel {
                          /** 有追索权模式 */
                          RECOURSE_RIGHT("RECOURSE_RIGHT", "有"),
                          /** 无追索权模式 */
                          DIS_RECOURSE_RIGHT("DIS_RECOURSE_RIGHT", "无");

    ProjectModel(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public ProjectModel getByCode(String code) {
        ProjectModel[] values = ProjectModel.values();
        for (ProjectModel value : values) {
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
