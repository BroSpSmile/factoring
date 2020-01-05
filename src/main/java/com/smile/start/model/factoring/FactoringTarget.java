/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.factoring;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.smile.start.model.enums.factoring.FactoringStatus;
import com.smile.start.model.project.Installment;
import com.smile.start.model.project.Project;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FactoringTarget
 * @date Date : 2019年12月30日 16:10
 */
public class FactoringTarget {

    /**  */
    private static final long serialVersionUID      = -3549416431549274388L;

    /** 编号 */
    private Long              id;

    /** 所属项目 */
    private Project           project;

    /** 项目状态 */
    private FactoringStatus   status;

    /** 让与人 */
    private String            creditor;

    /** 债务人 */
    private String            debtor;

    /** 签署日期 */
    private Date              signDate;

    /** 基础合同 */
    private String            baseContract;

    /** 应收账款受让款 */
    private double            assignee;

    /** 应收账款 */
    private double            receivable;

    /** 已投放金额 */
    private double            dropAmount;

    /** 放款分期信息 */
    private List<Installment> loanInstallments      = Lists.newArrayList();

    /** 转让年限 */
    private int               duration;

    /** 合同回款日 */
    private Date              remittanceDay;

    /** 回款分期信息 */
    private List<Installment> returnInstallments    = Lists.newArrayList();

    /** 保理费合计 */
    private double            totalFactoringFee;

    /** 保理费分期信息 */
    private List<Installment> factoringInstallments = Lists.newArrayList();;

    /** 收益率 */
    private double            returnRate;

    /** 备注 */
    private String            remark;

    @Override
    public String toString() {
        return "{\"FactoringTarget\":{" + "\"id\":" + id + ",\"project\":" + project + ",\"creditor\":\"" + creditor + '\"' + ",\"debtor\":\"" + debtor + '\"' + ",\"signDate\":\""
               + signDate + '\"' + ",\"baseContract\":\"" + baseContract + '\"' + ",\"assignee\":" + assignee + ",\"receivable\":" + receivable + ",\"dropAmount\":" + dropAmount
               + ",\"loanInstallments\":" + loanInstallments + ",\"duration\":" + duration + ",\"remittanceDay\":\"" + remittanceDay + '\"' + ",\"returnInstallments\":"
               + returnInstallments + ",\"totalFactoringFee\":" + totalFactoringFee + ",\"factoringInstallments\":" + factoringInstallments + ",\"returnRate\":" + returnRate
               + ",\"remark\":\"" + remark + '\"' + "}}";

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
     * @param id value to be assigned to property  id
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
     * @param project value to be assigned to property  project
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
     * @param assignee value to be assigned to property  assignee
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
     * @param receivable value to be assigned to property  receivable
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
     * @param dropAmount value to be assigned to property  dropAmount
     */
    public void setDropAmount(double dropAmount) {
        this.dropAmount = dropAmount;
    }

    /**
     * Getter method for property <tt>loanInstallments</tt>.
     *
     * @return property value of loanInstallments
     */
    public List<Installment> getLoanInstallments() {
        return loanInstallments;
    }

    /**
     * Setter method for property <tt>loanInstallments</tt>.
     *
     * @param loanInstallments value to be assigned to property  loanInstallments
     */
    public void setLoanInstallments(List<Installment> loanInstallments) {
        this.loanInstallments = loanInstallments;
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
     * Getter method for property <tt>returnInstallments</tt>.
     *
     * @return property value of returnInstallments
     */
    public List<Installment> getReturnInstallments() {
        return returnInstallments;
    }

    /**
     * Setter method for property <tt>returnInstallments</tt>.
     *
     * @param returnInstallments value to be assigned to property  returnInstallments
     */
    public void setReturnInstallments(List<Installment> returnInstallments) {
        this.returnInstallments = returnInstallments;
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
     * @param totalFactoringFee value to be assigned to property  totalFactoringFee
     */
    public void setTotalFactoringFee(double totalFactoringFee) {
        this.totalFactoringFee = totalFactoringFee;
    }

    /**
     * Getter method for property <tt>factoringInstallments</tt>.
     *
     * @return property value of factoringInstallments
     */
    public List<Installment> getFactoringInstallments() {
        return factoringInstallments;
    }

    /**
     * Setter method for property <tt>factoringInstallments</tt>.
     *
     * @param factoringInstallments value to be assigned to property  factoringInstallments
     */
    public void setFactoringInstallments(List<Installment> factoringInstallments) {
        this.factoringInstallments = factoringInstallments;
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
     * @param returnRate value to be assigned to property  returnRate
     */
    public void setReturnRate(double returnRate) {
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

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public FactoringStatus getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property  status
     */
    public void setStatus(FactoringStatus status) {
        this.status = status;
    }
}
