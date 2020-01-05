/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.combobox;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: EnumItem
 * @date Date : 2019年12月30日 18:58
 */
public class EnumItem {

    /** 索引 */
    private int    index;

    /** 状态码 */
    private String code;

    /** 状态描述 */
    private String desc;

    /**
     * 无参构造函数
     */
    public EnumItem() {

    }

    /**
     * 有参构造函数
     * @param index
     * @param code
     * @param desc
     */
    public EnumItem(int index, String code, String desc) {
        this.index = index;
        this.code = code;
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>index</tt>.
     *
     * @return property value of index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter method for property <tt>index</tt>.
     *
     * @param index value to be assigned to property  index
     */
    public void setIndex(int index) {
        this.index = index;
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
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property  code
     */
    public void setCode(String code) {
        this.code = code;
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
     * Setter method for property <tt>desc</tt>.
     *
     * @param desc value to be assigned to property  desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
