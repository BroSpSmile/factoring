/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums.factoring;

/**
 * 
 * @author smile.jing
 * @version $Id: EnumItem.java, v 0.1 2019年8月10日 下午9:16:07 smile.jing Exp $
 */
public enum FactoringStatus {
                             /** 业务申请 */
                             APPROVAL(1, "APPROVAL", "业务申请"),

                             /** 业务审核 */
                             FACTORING_AUDIT(2, "FACTORING_AUDIT", "业务审核"),

                             /** 放款录入 */
                             LOAN(3, "LOAN", "放款录入"),

                             /** 贷后管理 */
                             AFTER_LOAN(4, "AFTER_LOAN", "贷后管理"),

                             /** 归档 */
                             FILE(4, "FILE", "归档");

    FactoringStatus(int index, String code, String desc) {
        this.index = index;
        this.code = code;
        this.desc = desc;
    }

    /** index */
    private int    index;

    /** code */
    private String code;

    /** 描述 */
    private String desc;

    /**
     * Getter method for property <tt>index</tt>.
     * 
     * @return property value of index
     */
    public int getIndex() {
        return index;
    }

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
