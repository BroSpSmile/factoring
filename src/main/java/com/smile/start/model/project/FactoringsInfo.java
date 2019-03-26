/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;

/**
 * 保理项目信息
 * @author smile.jing
 * @version $Id: FactoringsInfo.java, v 0.1 Mar 25, 2019 11:28:50 PM smile.jing Exp $
 */
public class FactoringsInfo implements Serializable {

    /** UID */
    private static final long serialVersionUID = -8180230315200864625L;

    /** 月份 */
    private String            months;

    /** 项目数 */
    private int               total;

    /** 投资总额 */
    private double            investment;

    /** 利润 */
    private double            profit;

    /**
     * Getter method for property <tt>months</tt>.
     * 
     * @return property value of months
     */
    public String getMonths() {
        return months;
    }

    /**
     * Setter method for property <tt>months</tt>.
     * 
     * @param months value to be assigned to property months
     */
    public void setMonths(String months) {
        this.months = months;
    }

    /**
     * Getter method for property <tt>total</tt>.
     * 
     * @return property value of total
     */
    public int getTotal() {
        return total;
    }

    /**
     * Setter method for property <tt>total</tt>.
     * 
     * @param total value to be assigned to property total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Getter method for property <tt>investment</tt>.
     * 
     * @return property value of investment
     */
    public double getInvestment() {
        return investment;
    }

    /**
     * Setter method for property <tt>investment</tt>.
     * 
     * @param investment value to be assigned to property investment
     */
    public void setInvestment(double investment) {
        this.investment = investment;
    }

    /**
     * Getter method for property <tt>profit</tt>.
     * 
     * @return property value of profit
     */
    public double getProfit() {
        return profit;
    }

    /**
     * Setter method for property <tt>profit</tt>.
     * 
     * @param profit value to be assigned to property profit
     */
    public void setProfit(double profit) {
        this.profit = profit;
    }

}
