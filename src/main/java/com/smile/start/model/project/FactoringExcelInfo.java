/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.util.Date;

/**
 * 保理项目导出对象
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FactoringExcelInfo
 * @date Date : 2019年12月23日 15:59
 */
public class FactoringExcelInfo {

    /** 项目编号 */
    private String projectId;

    /** 项目名称 */
    private String projectName;

    /** 签署日期 */
    private Date   signDate;

    /** 让与人 */
    private String creditor;

    /** 债务人 */
    private String debtor;

    /** 基础合同 */
    private String baseContract;

    /** 追索权 */
    private String model;

    /** 应收账款受让款 */
    private String assignee;

    /** 应收账款 */
    private String receivable;

    /** 已投放金额 */
    private String dropAmount;

    /** 转让年限 */
    private int    duration;

    /** 合同回款日 */
    private Date   remittanceDay;

    /** 保理费合计 */
    private String totalFactoringFee;

    /** 收益率 */
    private String returnRate;

    /** 备注 */
    private String remark;

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
     * @param projectId value to be assigned to property  projectId
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
     * @param projectName value to be assigned to property  projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Getter method for property <tt>signDate</tt>.
     *
     * @return property value of signDate
     */
    public Date getSignDate() {
        return signDate;
    }

    /**
     * Setter method for property <tt>signDate</tt>.
     *
     * @param signDate value to be assigned to property  signDate
     */
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
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
     * @param creditor value to be assigned to property  creditor
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
     * @param debtor value to be assigned to property  debtor
     */
    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    /**
     * Getter method for property <tt>baseContract</tt>.
     *
     * @return property value of baseContract
     */
    public String getBaseContract() {
        return baseContract;
    }

    /**
     * Setter method for property <tt>baseContract</tt>.
     *
     * @param baseContract value to be assigned to property  baseContract
     */
    public void setBaseContract(String baseContract) {
        this.baseContract = baseContract;
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
     * @param model value to be assigned to property  model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Getter method for property <tt>assignee</tt>.
     *
     * @return property value of assignee
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * Setter method for property <tt>assignee</tt>.
     *
     * @param assignee value to be assigned to property  assignee
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * Getter method for property <tt>receivable</tt>.
     *
     * @return property value of receivable
     */
    public String getReceivable() {
        return receivable;
    }

    /**
     * Setter method for property <tt>receivable</tt>.
     *
     * @param receivable value to be assigned to property  receivable
     */
    public void setReceivable(String receivable) {
        this.receivable = receivable;
    }

    /**
     * Getter method for property <tt>dropAmount</tt>.
     *
     * @return property value of dropAmount
     */
    public String getDropAmount() {
        return dropAmount;
    }

    /**
     * Setter method for property <tt>dropAmount</tt>.
     *
     * @param dropAmount value to be assigned to property  dropAmount
     */
    public void setDropAmount(String dropAmount) {
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
     * @param duration value to be assigned to property  duration
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
     * @param remittanceDay value to be assigned to property  remittanceDay
     */
    public void setRemittanceDay(Date remittanceDay) {
        this.remittanceDay = remittanceDay;
    }

    /**
     * Getter method for property <tt>totalFactoringFee</tt>.
     *
     * @return property value of totalFactoringFee
     */
    public String getTotalFactoringFee() {
        return totalFactoringFee;
    }

    /**
     * Setter method for property <tt>totalFactoringFee</tt>.
     *
     * @param totalFactoringFee value to be assigned to property  totalFactoringFee
     */
    public void setTotalFactoringFee(String totalFactoringFee) {
        this.totalFactoringFee = totalFactoringFee;
    }

    /**
     * Getter method for property <tt>returnRate</tt>.
     *
     * @return property value of returnRate
     */
    public String getReturnRate() {
        return returnRate;
    }

    /**
     * Setter method for property <tt>returnRate</tt>.
     *
     * @param returnRate value to be assigned to property  returnRate
     */
    public void setReturnRate(String returnRate) {
        this.returnRate = returnRate;
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
     * @param remark value to be assigned to property  remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
