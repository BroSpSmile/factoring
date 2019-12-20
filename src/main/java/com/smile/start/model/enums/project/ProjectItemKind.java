/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.project;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目附件类型
 * @author smile.jing
 * @version $Id: ProjectItemType.java, v 0.1 Jan 28, 2019 3:49:43 PM smile.jing Exp $
 */
public enum ProjectItemKind {
                             /** 文本附件 */
                             DOC("DOC", "DOC"),

                             /** 在线附件 */
                             WEB("WEB", "在线附件");

    ProjectItemKind(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * getByCode
     * 
     * @param code
     * @return
     */
    public static ProjectItemKind getByCode(String code) {
        ProjectItemKind[] values = ProjectItemKind.values();
        for (ProjectItemKind value : values) {
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
