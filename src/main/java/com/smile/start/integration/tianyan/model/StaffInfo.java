/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.tianyan.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: StaffInfo
 * @date Date : 2019年12月18日 19:09
 */
public class StaffInfo implements Serializable {

    /** id */
    private long         id;

    /** 姓名 */
    private String       name;

    /** 主要人员类型 1-公司 2-人 */
    private String       type;

    /** 主要人员职位 */
    private String       staffTypeName;

    /** 职位列表 */
    private List<String> typeJoin;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property  id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property  name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property  type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>staffTypeName</tt>.
     *
     * @return property value of staffTypeName
     */
    public String getStaffTypeName() {
        return staffTypeName;
    }

    /**
     * Setter method for property <tt>staffTypeName</tt>.
     *
     * @param staffTypeName value to be assigned to property  staffTypeName
     */
    public void setStaffTypeName(String staffTypeName) {
        this.staffTypeName = staffTypeName;
    }

    /**
     * Getter method for property <tt>typeJoin</tt>.
     *
     * @return property value of typeJoin
     */
    public List<String> getTypeJoin() {
        return typeJoin;
    }

    /**
     * Setter method for property <tt>typeJoin</tt>.
     *
     * @param typeJoin value to be assigned to property  typeJoin
     */
    public void setTypeJoin(List<String> typeJoin) {
        this.typeJoin = typeJoin;
    }
}
