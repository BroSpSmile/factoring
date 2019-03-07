/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目附件枚举
 * @author smile.jing
 * @version $Id: ProjectItemType.java, v 0.1 Jan 28, 2019 3:49:43 PM smile.jing Exp $
 */
public enum ProjectItemType {
                             /**  */
                             TUNEUP("TUNEUP", "尽调文件"),

                             /**  */
                             PENDINGLOAN("PENDINGLOAN", "放款文件"),

                             /** */
                             INITIATE("INITIATE", "立项纪要"),

                             /** */
                             FILE("FILE", "归档文件");

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
