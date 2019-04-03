/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;

/**
 * 月利润
 * @author smile.jing
 * @version $Id: Profit.java, v 0.1 Apr 2, 2019 10:35:07 PM smile.jing Exp $
 */
public class Profit implements Serializable {

    /** UID */
    private static final long serialVersionUID = 3085006022690914322L;

    /** 月利润 */
    private double            monthProfit;

    /** 年利润 */
    private double            yearProfit;

    /**
     * Getter method for property <tt>monthProfit</tt>.
     * 
     * @return property value of monthProfit
     */
    public double getMonthProfit() {
        return monthProfit;
    }

    /**
     * Setter method for property <tt>monthProfit</tt>.
     * 
     * @param monthProfit value to be assigned to property monthProfit
     */
    public void setMonthProfit(double monthProfit) {
        this.monthProfit = monthProfit;
    }

    /**
     * Getter method for property <tt>yearProfit</tt>.
     * 
     * @return property value of yearProfit
     */
    public double getYearProfit() {
        return yearProfit;
    }

    /**
     * Setter method for property <tt>yearProfit</tt>.
     * 
     * @param yearProfit value to be assigned to property yearProfit
     */
    public void setYearProfit(double yearProfit) {
        this.yearProfit = yearProfit;
    }

}
