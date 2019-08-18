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

    /** 所属项目 */
    private FundTarget        target;

    /** 上传步骤 */
    private FundStatus        status;

    /** 附件名称 */
    private String            itemName;

    /** 文件值 */
    private String            itemValue;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (target != null ? "target\":\"" + target + "\", \"" : "") + (status != null ? "status\":\"" + status + "\", \"" : "")
               + (itemName != null ? "itemName\":\"" + itemName + "\", \"" : "") + (itemValue != null ? "itemValue\":\"" + itemValue : "") + "\"}  ";
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
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public FundStatus getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(FundStatus status) {
        this.status = status;
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
