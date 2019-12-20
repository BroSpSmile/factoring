package com.smile.start.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/3/9 14:24, BankInfoSearchDTO.java
 * @since 1.8
 */
public class BankInfoSearchDTO implements Serializable {
    private static final long serialVersionUID = 8867440426764642572L;

    /**
     * 所属组织
     */
    private List<String> organizationalList;

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

    public List<String> getOrganizationalList() {
        return organizationalList;
    }

    public void setOrganizationalList(List<String> organizationalList) {
        this.organizationalList = organizationalList;
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
