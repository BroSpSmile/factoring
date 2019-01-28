/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;

import com.smile.start.model.enums.ProjectItemType;

/**
 * 项目附件
 * @author smile.jing
 * @version $Id: ProjectItem.java, v 0.1 Jan 28, 2019 3:47:50 PM smile.jing Exp $
 */
public class ProjectItem implements Serializable {

    /** UID */
    private static final long serialVersionUID = 1206639280144526741L;

    /** 编号 */
    private Long              id;

    /** 项目编号 */
    private Long              projectId;

    /** 附件类型 */
    private ProjectItemType   itemType;

    /** 附件名称 */
    private String            itemName;

    /** 附件文件id */
    private String            itemValue;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\", \"projectId\":\"" + projectId + "\", \"itemType\":\"" + itemType + "\", \"itemName\":\"" + itemName + "\", \"itemValue\":\"" + itemValue
               + "\"}  ";
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>projectId</tt>.
     * 
     * @return property value of projectId
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Setter method for property <tt>projectId</tt>.
     * 
     * @param projectId value to be assigned to property projectId
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Getter method for property <tt>itemType</tt>.
     * 
     * @return property value of itemType
     */
    public ProjectItemType getItemType() {
        return itemType;
    }

    /**
     * Setter method for property <tt>itemType</tt>.
     * 
     * @param itemType value to be assigned to property itemType
     */
    public void setItemType(ProjectItemType itemType) {
        this.itemType = itemType;
    }

    /**
     * Getter method for property <tt>itemName</tt>.
     * 
     * @return property value of itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Setter method for property <tt>itemName</tt>.
     * 
     * @param itemName value to be assigned to property itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Getter method for property <tt>itemValue</tt>.
     * 
     * @return property value of itemValue
     */
    public String getItemValue() {
        return itemValue;
    }

    /**
     * Setter method for property <tt>itemValue</tt>.
     * 
     * @param itemValue value to be assigned to property itemValue
     */
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

}
