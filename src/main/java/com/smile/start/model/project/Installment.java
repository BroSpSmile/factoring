/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.smile.start.model.enums.InstallmentType;

/**
 * 分期信息
 * @author smile.jing
 * @version $Id: LoanInstallment.java, v 0.1 Mar 6, 2019 6:28:49 PM smile.jing Exp $
 */
public class Installment implements Serializable {

    /** UID */
    private static final long serialVersionUID = 2671634818269610449L;

    /** 编号 */
    private Long              id;

    /** 所属项目 */
    private FactoringDetail   detail;

    /** 分期类型 */
    private InstallmentType   type;

    /** 分期金额 */
    private double            amount;

    /** 分期时间 */
    private Date              installmentDate;

    /** 是否已支付 */
    private boolean           paied;

    /** 是否已开票 */
    private boolean            invoiced;

    /**
     * 分期收款或开票信息明细
     */
    private List<InstallmentDetail> detailList;

    /**
     * 分期凭证附件
     */
    private InstallmentItem item;

    public InstallmentItem getItem() {
        return item;
    }

    public void setItem(InstallmentItem item) {
        this.item = item;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (id != null ? "id\":\"" + id + "\", \"" : "")
            + (detail != null ? "detail\":\"" + detail + "\", \"" : "")
            + (type != null ? "type\":\"" + type + "\", \"" : "") + "amount\":\"" + amount + "\", \""
            + (installmentDate != null ? "installmentDate\":\"" + installmentDate + "\", \"" : "")
            + "paied\":\"" + paied + "\", \""
            + "invoiced\":\"" + invoiced + "\", \"" + "\"}  ";
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
     * Getter method for property <tt>detail</tt>.
     * 
     * @return property value of detail
     */
    public FactoringDetail getDetail() {
        return detail;
    }

    /**
     * Setter method for property <tt>detail</tt>.
     * 
     * @param detail value to be assigned to property detail
     */
    public void setDetail(FactoringDetail detail) {
        this.detail = detail;
    }

    /**
     * Getter method for property <tt>type</tt>.
     * 
     * @return property value of type
     */
    public InstallmentType getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     * 
     * @param type value to be assigned to property type
     */
    public void setType(InstallmentType type) {
        this.type = type;
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

    /**
     * Getter method for property <tt>installmentDate</tt>.
     * 
     * @return property value of installmentDate
     */
    public Date getInstallmentDate() {
        return installmentDate;
    }

    /**
     * Setter method for property <tt>installmentDate</tt>.
     * 
     * @param installmentDate value to be assigned to property installmentDate
     */
    public void setInstallmentDate(Date installmentDate) {
        this.installmentDate = installmentDate;
    }

    /**
     * Getter method for property <tt>paied</tt>.
     * 
     * @return property value of paied
     */
    public boolean isPaied() {
        return paied;
    }

    /**
     * Setter method for property <tt>paied</tt>.
     * 
     * @param paied value to be assigned to property paied
     */
    public void setPaied(boolean paied) {
        this.paied = paied;
    }

    public boolean isInvoiced() {
        return invoiced;
    }

    public void setInvoiced(boolean invoiced) {
        this.invoiced = invoiced;
    }

    public List<InstallmentDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<InstallmentDetail> detailList) {
        this.detailList = detailList;
    }

}
