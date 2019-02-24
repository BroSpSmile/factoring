/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目明细
 * @author smile.jing
 * @version $Id: FactoringDetail.java, v 0.1 Feb 24, 2019 7:20:04 PM smile.jing Exp $
 */
public class FactoringDetail implements Serializable {
    /**  */
    private static final long serialVersionUID = -3549416431549274388L;

    /** 编号 */
    private Long              id;

    /** 所属项目 */
    private Project           project;

    /** 与让人 */
    private String            creditor;

    /** 债务人 */
    private String            debtor;

    /** 应收账款受让款 */
    private double            assignee;

    /** 应收账款 */
    private double            receivable;

    /** 已投放金额 */
    private double            dropAmount;

    /** 转让年限 */
    private int               duration;

    /** 合同汇款日 */
    private Date              remittanceDay;

    /** 实际回款日 */
    private Date              realBackDay;

    /** 回款金额 */
    private double            backAmount;

    /** 是否回款 */
    private boolean           moneyBack;

    /** 保理费合计 */
    private double            totalFactoringFee;

    /** 保理费分期 */
    private double            factoringStages;

    /** 收益率 */
    private double            returnRate;

    /** 保理费到账日 */
    private Date              accountDay;

    /** 已开发票 */
    private boolean           invoicing;

    /** 是否已支付 */
    private boolean           paied;

    /** 备注 */
    private String            remark;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (id != null ? "id\":\"" + id + "\", \"" : "") + (project != null ? "project\":\"" + project + "\", \"" : "")
               + (creditor != null ? "creditor\":\"" + creditor + "\", \"" : "") + (debtor != null ? "debtor\":\"" + debtor + "\", \"" : "") + "assignee\":\"" + assignee
               + "\", \"receivable\":\"" + receivable + "\", \"dropAmount\":\"" + dropAmount + "\", \"duration\":\"" + duration + "\", \""
               + (remittanceDay != null ? "remittanceDay\":\"" + remittanceDay + "\", \"" : "") + (realBackDay != null ? "realBackDay\":\"" + realBackDay + "\", \"" : "")
               + "backAmount\":\"" + backAmount + "\", \"moneyBack\":\"" + moneyBack + "\", \"totalFactoringFee\":\"" + totalFactoringFee + "\", \"factoringStages\":\""
               + factoringStages + "\", \"returnRate\":\"" + returnRate + "\", \"" + (accountDay != null ? "accountDay\":\"" + accountDay + "\", \"" : "") + "invoicing\":\""
               + invoicing + "\", \"paied\":\"" + paied + "\", \"" + (remark != null ? "remark\":\"" + remark : "") + "\"}  ";
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
     * Getter method for property <tt>project</tt>.
     * 
     * @return property value of project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Setter method for property <tt>project</tt>.
     * 
     * @param project value to be assigned to property project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Getter method for property <tt>creditor</tt>.
     * 
     * @return property value of creditor
     */
    public String getCreditor() {
        return creditor;
    }

    /**
     * Setter method for property <tt>creditor</tt>.
     * 
     * @param creditor value to be assigned to property creditor
     */
    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    /**
     * Getter method for property <tt>debtor</tt>.
     * 
     * @return property value of debtor
     */
    public String getDebtor() {
        return debtor;
    }

    /**
     * Setter method for property <tt>debtor</tt>.
     * 
     * @param debtor value to be assigned to property debtor
     */
    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    /**
     * Getter method for property <tt>assignee</tt>.
     * 
     * @return property value of assignee
     */
    public double getAssignee() {
        return assignee;
    }

    /**
     * Setter method for property <tt>assignee</tt>.
     * 
     * @param assignee value to be assigned to property assignee
     */
    public void setAssignee(double assignee) {
        this.assignee = assignee;
    }

    /**
     * Getter method for property <tt>receivable</tt>.
     * 
     * @return property value of receivable
     */
    public double getReceivable() {
        return receivable;
    }

    /**
     * Setter method for property <tt>receivable</tt>.
     * 
     * @param receivable value to be assigned to property receivable
     */
    public void setReceivable(double receivable) {
        this.receivable = receivable;
    }

    /**
     * Getter method for property <tt>dropAmount</tt>.
     * 
     * @return property value of dropAmount
     */
    public double getDropAmount() {
        return dropAmount;
    }

    /**
     * Setter method for property <tt>dropAmount</tt>.
     * 
     * @param dropAmount value to be assigned to property dropAmount
     */
    public void setDropAmount(double dropAmount) {
        this.dropAmount = dropAmount;
    }

    /**
     * Getter method for property <tt>duration</tt>.
     * 
     * @return property value of duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter method for property <tt>duration</tt>.
     * 
     * @param duration value to be assigned to property duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Getter method for property <tt>remittanceDay</tt>.
     * 
     * @return property value of remittanceDay
     */
    public Date getRemittanceDay() {
        return remittanceDay;
    }

    /**
     * Setter method for property <tt>remittanceDay</tt>.
     * 
     * @param remittanceDay value to be assigned to property remittanceDay
     */
    public void setRemittanceDay(Date remittanceDay) {
        this.remittanceDay = remittanceDay;
    }

    /**
     * Getter method for property <tt>realBackDay</tt>.
     * 
     * @return property value of realBackDay
     */
    public Date getRealBackDay() {
        return realBackDay;
    }

    /**
     * Setter method for property <tt>realBackDay</tt>.
     * 
     * @param realBackDay value to be assigned to property realBackDay
     */
    public void setRealBackDay(Date realBackDay) {
        this.realBackDay = realBackDay;
    }

    /**
     * Getter method for property <tt>backAmount</tt>.
     * 
     * @return property value of backAmount
     */
    public double getBackAmount() {
        return backAmount;
    }

    /**
     * Setter method for property <tt>backAmount</tt>.
     * 
     * @param backAmount value to be assigned to property backAmount
     */
    public void setBackAmount(double backAmount) {
        this.backAmount = backAmount;
    }

    /**
     * Getter method for property <tt>moneyBack</tt>.
     * 
     * @return property value of moneyBack
     */
    public boolean isMoneyBack() {
        return moneyBack;
    }

    /**
     * Setter method for property <tt>moneyBack</tt>.
     * 
     * @param moneyBack value to be assigned to property moneyBack
     */
    public void setMoneyBack(boolean moneyBack) {
        this.moneyBack = moneyBack;
    }

    /**
     * Getter method for property <tt>totalFactoringFee</tt>.
     * 
     * @return property value of totalFactoringFee
     */
    public double getTotalFactoringFee() {
        return totalFactoringFee;
    }

    /**
     * Setter method for property <tt>totalFactoringFee</tt>.
     * 
     * @param totalFactoringFee value to be assigned to property totalFactoringFee
     */
    public void setTotalFactoringFee(double totalFactoringFee) {
        this.totalFactoringFee = totalFactoringFee;
    }

    /**
     * Getter method for property <tt>factoringStages</tt>.
     * 
     * @return property value of factoringStages
     */
    public double getFactoringStages() {
        return factoringStages;
    }

    /**
     * Setter method for property <tt>factoringStages</tt>.
     * 
     * @param factoringStages value to be assigned to property factoringStages
     */
    public void setFactoringStages(double factoringStages) {
        this.factoringStages = factoringStages;
    }

    /**
     * Getter method for property <tt>returnRate</tt>.
     * 
     * @return property value of returnRate
     */
    public double getReturnRate() {
        return returnRate;
    }

    /**
     * Setter method for property <tt>returnRate</tt>.
     * 
     * @param returnRate value to be assigned to property returnRate
     */
    public void setReturnRate(double returnRate) {
        this.returnRate = returnRate;
    }

    /**
     * Getter method for property <tt>accountDay</tt>.
     * 
     * @return property value of accountDay
     */
    public Date getAccountDay() {
        return accountDay;
    }

    /**
     * Setter method for property <tt>accountDay</tt>.
     * 
     * @param accountDay value to be assigned to property accountDay
     */
    public void setAccountDay(Date accountDay) {
        this.accountDay = accountDay;
    }

    /**
     * Getter method for property <tt>invoicing</tt>.
     * 
     * @return property value of invoicing
     */
    public boolean isInvoicing() {
        return invoicing;
    }

    /**
     * Setter method for property <tt>invoicing</tt>.
     * 
     * @param invoicing value to be assigned to property invoicing
     */
    public void setInvoicing(boolean invoicing) {
        this.invoicing = invoicing;
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

    /**
     * Getter method for property <tt>remark</tt>.
     * 
     * @return property value of remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Setter method for property <tt>remark</tt>.
     * 
     * @param remark value to be assigned to property remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
