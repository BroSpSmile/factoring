/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.List;

/**
 * 财务管理查询页面使用
 *
 * @author xioutman
 * @version
 */
public class ProjectForView implements Serializable {

    private static final long serialVersionUID = 6543700438388296682L;

    /**
     * 项目编号
     */
    private Long              id;

    /**
     * 项目编号
     */
    private String            projectId;

    /**
     * 项目名称
     */
    private String            projectName;

    /**
     * 项目发起人 业务负责人
     */
    private String            username;

    /** 让与人 */
    private String            creditor;

    /** 债务人 */
    private String            debtor;

    /** 项目模式 */
    private String            model;

    /**
     * 放款审核通过时间
     */
    private String            loanAuditPassTime;

    /**
     * 应收账款
     */
    private double            receivable;

    /**
     * 已投放金额
     */
    private List<Double>      dropAmount;

    /**
     * 投放日期
     */
    private List<String>      dropDates;

    /**
     * 回款金额
     */
    private List<Double>      returnAmount;

    /**
     * 回款日期
     */
    private List<String>      returnDates;

    /**
     * 保理费合计
     */
    private double            totalFactoringFee;

    /** 当前步骤 */
    private int               step;

    /**
     * 保理费分期金额
     */
    private List<Double>      factoringInstallmentAmounts;

    /**
     * 保理费分期到账日期
     */
    private List<String>      factoringInstallmentDates;

    /**
     * 是否已经开票 列表
     */
    private List<Boolean>     factoringInstallmentInvoiceds;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (id != null ? "id\":\"" + id + "\", \"" : "") + (projectId != null ? "projectId\":\"" + projectId + "\", \"" : "")
               + (projectName != null ? "projectName\":\"" + projectName + "\", \"" : "") + (username != null ? "username\":\"" + username + "\", \"" : "")
               + (creditor != null ? "creditor\":\"" + creditor + "\", \"" : "") + (debtor != null ? "debtor\":\"" + debtor + "\", \"" : "")
               + (model != null ? "model\":\"" + model + "\", \"" : "") + (loanAuditPassTime != null ? "loanAuditPassTime\":\"" + loanAuditPassTime + "\", \"" : "")
               + "receivable\":\"" + receivable + "\", \"" + (dropAmount != null ? "dropAmount\":\"" + dropAmount + "\", \"" : "")
               + (dropDates != null ? "dropDates\":\"" + dropDates + "\", \"" : "") + (returnAmount != null ? "returnAmount\":\"" + returnAmount + "\", \"" : "")
               + (returnDates != null ? "returnDates\":\"" + returnDates + "\", \"" : "") + "totalFactoringFee\":\"" + totalFactoringFee + "\", \""
               + (factoringInstallmentAmounts != null ? "factoringInstallmentAmounts\":\"" + factoringInstallmentAmounts + "\", \"" : "")
               + (factoringInstallmentDates != null ? "factoringInstallmentDates\":\"" + factoringInstallmentDates + "\", \"" : "")
               + (factoringInstallmentInvoiceds != null ? "factoringInstallmentInvoiceds\":\"" + factoringInstallmentInvoiceds : "") + "\"}  ";
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
     * Getter method for property <tt>projectId</tt>.
     * 
     * @return property value of projectId
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Setter method for property <tt>projectId</tt>.
     * 
     * @param projectId value to be assigned to property projectId
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Getter method for property <tt>projectName</tt>.
     * 
     * @return property value of projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Setter method for property <tt>projectName</tt>.
     * 
     * @param projectName value to be assigned to property projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Getter method for property <tt>username</tt>.
     * 
     * @return property value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for property <tt>username</tt>.
     * 
     * @param username value to be assigned to property username
     */
    public void setUsername(String username) {
        this.username = username;
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
     * Getter method for property <tt>model</tt>.
     * 
     * @return property value of model
     */
    public String getModel() {
        return model;
    }

    /**
     * Setter method for property <tt>model</tt>.
     * 
     * @param model value to be assigned to property model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Getter method for property <tt>loanAuditPassTime</tt>.
     * 
     * @return property value of loanAuditPassTime
     */
    public String getLoanAuditPassTime() {
        return loanAuditPassTime;
    }

    /**
     * Setter method for property <tt>loanAuditPassTime</tt>.
     * 
     * @param loanAuditPassTime value to be assigned to property loanAuditPassTime
     */
    public void setLoanAuditPassTime(String loanAuditPassTime) {
        this.loanAuditPassTime = loanAuditPassTime;
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
    public List<Double> getDropAmount() {
        return dropAmount;
    }

    /**
     * Setter method for property <tt>dropAmount</tt>.
     * 
     * @param dropAmount value to be assigned to property dropAmount
     */
    public void setDropAmount(List<Double> dropAmount) {
        this.dropAmount = dropAmount;
    }

    /**
     * Getter method for property <tt>dropDates</tt>.
     * 
     * @return property value of dropDates
     */
    public List<String> getDropDates() {
        return dropDates;
    }

    /**
     * Setter method for property <tt>dropDates</tt>.
     * 
     * @param dropDates value to be assigned to property dropDates
     */
    public void setDropDates(List<String> dropDates) {
        this.dropDates = dropDates;
    }

    /**
     * Getter method for property <tt>returnAmount</tt>.
     * 
     * @return property value of returnAmount
     */
    public List<Double> getReturnAmount() {
        return returnAmount;
    }

    /**
     * Setter method for property <tt>returnAmount</tt>.
     * 
     * @param returnAmount value to be assigned to property returnAmount
     */
    public void setReturnAmount(List<Double> returnAmount) {
        this.returnAmount = returnAmount;
    }

    /**
     * Getter method for property <tt>returnDates</tt>.
     * 
     * @return property value of returnDates
     */
    public List<String> getReturnDates() {
        return returnDates;
    }

    /**
     * Setter method for property <tt>returnDates</tt>.
     * 
     * @param returnDates value to be assigned to property returnDates
     */
    public void setReturnDates(List<String> returnDates) {
        this.returnDates = returnDates;
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
     * Getter method for property <tt>step</tt>.
     * 
     * @return property value of step
     */
    public int getStep() {
        return step;
    }

    /**
     * Setter method for property <tt>step</tt>.
     * 
     * @param step value to be assigned to property step
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * Getter method for property <tt>factoringInstallmentAmounts</tt>.
     * 
     * @return property value of factoringInstallmentAmounts
     */
    public List<Double> getFactoringInstallmentAmounts() {
        return factoringInstallmentAmounts;
    }

    /**
     * Setter method for property <tt>factoringInstallmentAmounts</tt>.
     * 
     * @param factoringInstallmentAmounts value to be assigned to property factoringInstallmentAmounts
     */
    public void setFactoringInstallmentAmounts(List<Double> factoringInstallmentAmounts) {
        this.factoringInstallmentAmounts = factoringInstallmentAmounts;
    }

    /**
     * Getter method for property <tt>factoringInstallmentDates</tt>.
     * 
     * @return property value of factoringInstallmentDates
     */
    public List<String> getFactoringInstallmentDates() {
        return factoringInstallmentDates;
    }

    /**
     * Setter method for property <tt>factoringInstallmentDates</tt>.
     * 
     * @param factoringInstallmentDates value to be assigned to property factoringInstallmentDates
     */
    public void setFactoringInstallmentDates(List<String> factoringInstallmentDates) {
        this.factoringInstallmentDates = factoringInstallmentDates;
    }

    /**
     * Getter method for property <tt>factoringInstallmentInvoiceds</tt>.
     * 
     * @return property value of factoringInstallmentInvoiceds
     */
    public List<Boolean> getFactoringInstallmentInvoiceds() {
        return factoringInstallmentInvoiceds;
    }

    /**
     * Setter method for property <tt>factoringInstallmentInvoiceds</tt>.
     * 
     * @param factoringInstallmentInvoiceds value to be assigned to property factoringInstallmentInvoiceds
     */
    public void setFactoringInstallmentInvoiceds(List<Boolean> factoringInstallmentInvoiceds) {
        this.factoringInstallmentInvoiceds = factoringInstallmentInvoiceds;
    }

}
