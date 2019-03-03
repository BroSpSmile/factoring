/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;

/**
 * 审核附件
 * @author smile.jing
 * @version $Id: AuditRecordItem.java, v 0.1 Mar 2, 2019 9:44:43 PM smile.jing Exp $
 */
public class AuditRecordItem implements Serializable {

    /** UID */
    private static final long serialVersionUID = -8877662160331925955L;

    /** 项目编号 */
    private Long              id;

    /** 审核记录 */
    private AuditRecord       record;

    /** 附件名称 */
    private String            itemName;

    /** 文件值 */
    private String            itemValue;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (id != null ? "id\":\"" + id + "\", \"" : "") + (record != null ? "record\":\"" + record + "\", \"" : "")
               + (itemName != null ? "itemName\":\"" + itemName + "\", \"" : "") + (itemValue != null ? "itemValue\":\"" + itemValue : "") + "\"}  ";
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
     * Getter method for property <tt>record</tt>.
     * 
     * @return property value of record
     */
    public AuditRecord getRecord() {
        return record;
    }

    /**
     * Setter method for property <tt>record</tt>.
     * 
     * @param record value to be assigned to property record
     */
    public void setRecord(AuditRecord record) {
        this.record = record;
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
