/**
 * VIP.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import org.apache.poi.ss.formula.functions.T;

import java.util.Date;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: BaseProjectQuery
 * @date Date : 2019年10月09日 22:01
 */
@SuppressWarnings("hiding")
public class BaseProjectQuery<T> extends BaseProject<T> {
    /**  */
    private Date              stateDate;

    /**  */
    private Date              endDate;

    /**
     * Getter method for property <tt>stateDate</tt>.
     *
     * @return property value of stateDate
     */
    public Date getStateDate() {
        return stateDate;
    }

    /**
     * Setter method for property <tt>stateDate</tt>.
     *
     * @param stateDate value to be assigned to property stateDate
     */
    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    /**
     * Getter method for property <tt>endDate</tt>.
     *
     * @return property value of endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter method for property <tt>endDate</tt>.
     *
     * @param endDate value to be assigned to property endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
