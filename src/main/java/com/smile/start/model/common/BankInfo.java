package com.smile.start.model.common;

import java.io.Serializable;

/**
 * 银行信息
 * @author Joseph
 * @version v1.0 2019/3/9 14:15, BankInfo.java
 * @since 1.8
 */
public class BankInfo implements Serializable {
    private static final long serialVersionUID = 8398073824838259946L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 银行全称
     */
    private String bankFullName;

    /**
     * 银行名称简称
     */
    private String bankShortName;

    /**
     * 银行账号
     */
    private String bankAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBankFullName() {
        return bankFullName;
    }

    public void setBankFullName(String bankFullName) {
        this.bankFullName = bankFullName;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
