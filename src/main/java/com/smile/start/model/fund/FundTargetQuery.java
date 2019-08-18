/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.fund;

import java.util.Date;

/**
 * 
 * @author smile.jing
 * @version $Id: FundTargetQuery.java, v 0.1 2019年8月10日 下午8:11:06 smile.jing Exp $
 */
public class FundTargetQuery extends FundTarget {

    /** UID */
    private static final long serialVersionUID = -9124274119388627627L;

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
