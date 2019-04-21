/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.loan;

import java.io.Serializable;

/**
 * 放款分组
 * @author smile.jing
 * @version $Id: LoanGroup.java, v 0.1 Apr 20, 2019 5:38:56 PM smile.jing Exp $
 */
public class LoanGroup implements Serializable {

    /**  */
    private static final long serialVersionUID = -4668462814100988957L;

    /** 收款单位名称 */
    private String            payeeName;

    /** 收款方开户银行 */
    private String            payeeBankName;

    /** 收款方银行账号 */
    private String            payeeAccountNo;

    /** 付款单位名称 */
    private String            payerName;

    /** 付款方开户银行 */
    private String            payerBankName;

    /** 付款方银行账号 */
    private String            payerAccountNo;

    /** 本次付款金额 */
    private double            payments;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (payeeName != null ? "payeeName\":\"" + payeeName + "\", \"" : "") + (payeeBankName != null ? "payeeBankName\":\"" + payeeBankName + "\", \"" : "")
               + (payeeAccountNo != null ? "payeeAccountNo\":\"" + payeeAccountNo + "\", \"" : "") + (payerName != null ? "payerName\":\"" + payerName + "\", \"" : "")
               + (payerBankName != null ? "payerBankName\":\"" + payerBankName + "\", \"" : "") + (payerAccountNo != null ? "payerAccountNo\":\"" + payerAccountNo + "\", \"" : "")
               + "payments\":\"" + payments + "\"}  ";
    }

    /**
     * Getter method for property <tt>payeeName</tt>.
     * 
     * @return property value of payeeName
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     * Setter method for property <tt>payeeName</tt>.
     * 
     * @param payeeName value to be assigned to property payeeName
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    /**
     * Getter method for property <tt>payeeBankName</tt>.
     * 
     * @return property value of payeeBankName
     */
    public String getPayeeBankName() {
        return payeeBankName;
    }

    /**
     * Setter method for property <tt>payeeBankName</tt>.
     * 
     * @param payeeBankName value to be assigned to property payeeBankName
     */
    public void setPayeeBankName(String payeeBankName) {
        this.payeeBankName = payeeBankName;
    }

    /**
     * Getter method for property <tt>payeeAccountNo</tt>.
     * 
     * @return property value of payeeAccountNo
     */
    public String getPayeeAccountNo() {
        return payeeAccountNo;
    }

    /**
     * Setter method for property <tt>payeeAccountNo</tt>.
     * 
     * @param payeeAccountNo value to be assigned to property payeeAccountNo
     */
    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    /**
     * Getter method for property <tt>payerName</tt>.
     * 
     * @return property value of payerName
     */
    public String getPayerName() {
        return payerName;
    }

    /**
     * Setter method for property <tt>payerName</tt>.
     * 
     * @param payerName value to be assigned to property payerName
     */
    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    /**
     * Getter method for property <tt>payerBankName</tt>.
     * 
     * @return property value of payerBankName
     */
    public String getPayerBankName() {
        return payerBankName;
    }

    /**
     * Setter method for property <tt>payerBankName</tt>.
     * 
     * @param payerBankName value to be assigned to property payerBankName
     */
    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    /**
     * Getter method for property <tt>payerAccountNo</tt>.
     * 
     * @return property value of payerAccountNo
     */
    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    /**
     * Setter method for property <tt>payerAccountNo</tt>.
     * 
     * @param payerAccountNo value to be assigned to property payerAccountNo
     */
    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    /**
     * Getter method for property <tt>payments</tt>.
     * 
     * @return property value of payments
     */
    public double getPayments() {
        return payments;
    }

    /**
     * Setter method for property <tt>payments</tt>.
     * 
     * @param payments value to be assigned to property payments
     */
    public void setPayments(double payments) {
        this.payments = payments;
    }

}
