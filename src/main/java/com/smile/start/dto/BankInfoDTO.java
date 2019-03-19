package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/3/9 14:29, BankInfoDTO.java
 * @since 1.8
 */
public class BankInfoDTO implements Serializable {
    private static final long serialVersionUID = -5426792948809746428L;

    /**
     * 主键
     */
    private Long              id;

    /**
     * 业务流水
     */
    private String            serialNo;

    /**
     * 所属组织
     */
    private String            organizationalSerialNo;

    /**
     * 银行全称
     */
    private String            bankFullName;

    /**
     * 银行名称简称
     */
    private String            bankShortName;

    /**
     * 银行账号
     */
    private String            bankAccount;

    /** 银行余额 */
    private double            amount;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (id != null ? "id\":\"" + id + "\", \"" : "") + (serialNo != null ? "serialNo\":\"" + serialNo + "\", \"" : "")
               + (bankFullName != null ? "bankFullName\":\"" + bankFullName + "\", \"" : "") + (bankShortName != null ? "bankShortName\":\"" + bankShortName + "\", \"" : "")
               + (bankAccount != null ? "bankAccount\":\"" + bankAccount + "\", \"" : "") + "amount\":\"" + amount + "\"}  ";
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
     * Getter method for property <tt>serialNo</tt>.
     * 
     * @return property value of serialNo
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * Setter method for property <tt>serialNo</tt>.
     * 
     * @param serialNo value to be assigned to property serialNo
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getOrganizationalSerialNo() {
        return organizationalSerialNo;
    }

    public void setOrganizationalSerialNo(String organizationalSerialNo) {
        this.organizationalSerialNo = organizationalSerialNo;
    }

    /**
     * Getter method for property <tt>bankFullName</tt>.
     * 
     * @return property value of bankFullName
     */
    public String getBankFullName() {
        return bankFullName;
    }

    /**
     * Setter method for property <tt>bankFullName</tt>.
     * 
     * @param bankFullName value to be assigned to property bankFullName
     */
    public void setBankFullName(String bankFullName) {
        this.bankFullName = bankFullName;
    }

    /**
     * Getter method for property <tt>bankShortName</tt>.
     * 
     * @return property value of bankShortName
     */
    public String getBankShortName() {
        return bankShortName;
    }

    /**
     * Setter method for property <tt>bankShortName</tt>.
     * 
     * @param bankShortName value to be assigned to property bankShortName
     */
    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    /**
     * Getter method for property <tt>bankAccount</tt>.
     * 
     * @return property value of bankAccount
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * Setter method for property <tt>bankAccount</tt>.
     * 
     * @param bankAccount value to be assigned to property bankAccount
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * Getter method for property <tt>amount</tt>.
     * 
     * @return property value of amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter method for property <tt>amount</tt>.
     * 
     * @param amount value to be assigned to property amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

}
