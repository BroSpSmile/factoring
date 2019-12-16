/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.fund;

/**
 * 直投信息
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundInfos
 * @date Date : 2019年12月16日 11:33
 */
public class FundInfos {

    /** 月份 */
    private String months;

    /** 投资数量 */
    private int    total;

    /** 投资金额 */
    private double investment;

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
     * @param months value to be assigned to property  months
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
     * @param total value to be assigned to property  total
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
     * @param investment value to be assigned to property  investment
     */
    public void setInvestment(double investment) {
        this.investment = investment;
    }
}
