/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.Date;
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
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目发起人 业务负责人
     */
    private String username;

    /**
     * 放款审核通过时间
     */
    private Date loanAuditPassTime;

    /**
     * 应收账款
     */
    private double receivable;

    /**
     * 已投放金额
     */
    private double dropAmount;

    /**
     * 投放日期
     */
    private List<String> dropDates;

    /**
     * 回款金额
     */
    private double returnAmount;

    /**
     * 回款日期
     */
    private List<String> returnDates;

    /**
     * 保理费合计
     */
    private double totalFactoringFee;

    /**
     * 保理费分期金额
     */
    private List<Double> factoringInstallmentAmounts;

    /**
     * 保理费分期到账日期
     */
    private List<String> factoringInstallmentDates;

    /**
     * 是否已经开票 列表
     */
    private List<Boolean> factoringInstallmentInvoiceds;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoanAuditPassTime() {
        return loanAuditPassTime;
    }

    public void setLoanAuditPassTime(Date loanAuditPassTime) {
        this.loanAuditPassTime = loanAuditPassTime;
    }

    public double getReceivable() {
        return receivable;
    }

    public void setReceivable(double receivable) {
        this.receivable = receivable;
    }

    public double getDropAmount() {
        return dropAmount;
    }

    public void setDropAmount(double dropAmount) {
        this.dropAmount = dropAmount;
    }

    public List<String> getDropDates() {
        return dropDates;
    }

    public void setDropDates(List<String> dropDates) {
        this.dropDates = dropDates;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public List<String> getReturnDates() {
        return returnDates;
    }

    public void setReturnDates(List<String> returnDates) {
        this.returnDates = returnDates;
    }

    public double getTotalFactoringFee() {
        return totalFactoringFee;
    }

    public void setTotalFactoringFee(double totalFactoringFee) {
        this.totalFactoringFee = totalFactoringFee;
    }

    public List<Double> getFactoringInstallmentAmounts() {
        return factoringInstallmentAmounts;
    }

    public void setFactoringInstallmentAmounts(List<Double> factoringInstallmentAmounts) {
        this.factoringInstallmentAmounts = factoringInstallmentAmounts;
    }

    public List<String> getFactoringInstallmentDates() {
        return factoringInstallmentDates;
    }

    public void setFactoringInstallmentDates(List<String> factoringInstallmentDates) {
        this.factoringInstallmentDates = factoringInstallmentDates;
    }

    public List<Boolean> getFactoringInstallmentInvoiceds() {
        return factoringInstallmentInvoiceds;
    }

    public void setFactoringInstallmentInvoiceds(List<Boolean> factoringInstallmentInvoiceds) {
        this.factoringInstallmentInvoiceds = factoringInstallmentInvoiceds;
    }
}