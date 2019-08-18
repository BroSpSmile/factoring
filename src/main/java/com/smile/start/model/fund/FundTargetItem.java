/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.fund;

import java.io.Serializable;

import com.smile.start.model.enums.FundStatus;

/**
 * 直投标的附件
 * @author smile.jing
 * @version $Id: FundTargetItem.java, v 0.1 2019年8月18日 下午3:04:39 smile.jing Exp $
 */
public class FundTargetItem implements Serializable {

    /** UID */
    private static final long serialVersionUID = 6572114648866342984L;

    /** 编号 */
    private Long              id;

    /** 所属项目 */
    private FundTarget        target;

    /** 上传步骤 */
    private FundStatus        itemType;

    /** 附件名称 */
    private String            itemName;

    /** 文件值 */
    private String            itemValue;

    /** (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FundTargetItem [target=" + target + ", itemType=" + itemType + ", itemName=" + itemName + ", itemValue=" + itemValue + "]";
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>target</tt>.
     * 
     * @return property value of target
     */
    public FundTarget getTarget() {
        return target;
    }

    /**
     * Setter method for property <tt>target</tt>.
     * 
     * @param target value to be assigned to property target
     */
    public void setTarget(FundTarget target) {
        this.target = target;
    }

    /**
     * @return the itemType
     */
    public FundStatus getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(FundStatus itemType) {
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
