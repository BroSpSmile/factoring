/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.loan;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.smile.start.model.enums.LoanType;
import com.smile.start.model.project.Project;

/**
 * 放款
 * @author smile.jing
 * @version $Id: Loan.java, v 0.1 Feb 25, 2019 4:18:30 PM smile.jing Exp $
 */
public class Loan implements Serializable {

    /** UID */
    private static final long serialVersionUID = -5641828046068734162L;

    /**  */
    private Long              id;

    /** 申请类型 */
    private LoanType          type;

    /** 申请部门 */
    private String            department;

    /** 申请人 */
    private String            user;

    /** 申请时间 */
    private Date              createTime;

    /** 关联项目 */
    private Project           project;

    /** 认缴金额 */
    private double            subscriptionAmount;

    /** 本次付款金额 */
    private double            payments;

    /** 金额大写 */
    private String            chineseAmount;

    /** 累计付款金额 */
    private double            accumulativeyments;

    /** 未付款金额 */
    private double            unpaid;

    /** 付款用途 */
    private String            paymentPurpose;

    /** 放款分组 */
    private List<LoanGroup>   groups;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (id != null ? "id\":\"" + id + "\", \"" : "") + (type != null ? "type\":\"" + type + "\", \"" : "")
               + (department != null ? "department\":\"" + department + "\", \"" : "") + (user != null ? "user\":\"" + user + "\", \"" : "")
               + (createTime != null ? "createTime\":\"" + createTime + "\", \"" : "") + (project != null ? "project\":\"" + project + "\", \"" : "") + "subscriptionAmount\":\""
               + subscriptionAmount + "\", \"payments\":\"" + payments + "\", \"" + (chineseAmount != null ? "chineseAmount\":\"" + chineseAmount + "\", \"" : "")
               + "accumulativeyments\":\"" + accumulativeyments + "\", \"unpaid\":\"" + unpaid + "\", \""
               + (paymentPurpose != null ? "paymentPurpose\":\"" + paymentPurpose + "\", \"" : "") + (groups != null ? "groups\":\"" + groups : "") + "\"}  ";
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
     * Getter method for property <tt>type</tt>.
     * 
     * @return property value of type
     */
    public LoanType getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     * 
     * @param type value to be assigned to property type
     */
    public void setType(LoanType type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>department</tt>.
     * 
     * @return property value of department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Setter method for property <tt>department</tt>.
     * 
     * @param department value to be assigned to property department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Getter method for property <tt>user</tt>.
     * 
     * @return property value of user
     */
    public String getUser() {
        return user;
    }

    /**
     * Setter method for property <tt>user</tt>.
     * 
     * @param user value to be assigned to property user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Getter method for property <tt>createTime</tt>.
     * 
     * @return property value of createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Setter method for property <tt>createTime</tt>.
     * 
     * @param createTime value to be assigned to property createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * Getter method for property <tt>subscriptionAmount</tt>.
     * 
     * @return property value of subscriptionAmount
     */
    public double getSubscriptionAmount() {
        return subscriptionAmount;
    }

    /**
     * Setter method for property <tt>subscriptionAmount</tt>.
     * 
     * @param subscriptionAmount value to be assigned to property subscriptionAmount
     */
    public void setSubscriptionAmount(double subscriptionAmount) {
        this.subscriptionAmount = subscriptionAmount;
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

    /**
     * Getter method for property <tt>chineseAmount</tt>.
     * 
     * @return property value of chineseAmount
     */
    public String getChineseAmount() {
        return chineseAmount;
    }

    /**
     * Setter method for property <tt>chineseAmount</tt>.
     * 
     * @param chineseAmount value to be assigned to property chineseAmount
     */
    public void setChineseAmount(String chineseAmount) {
        this.chineseAmount = chineseAmount;
    }

    /**
     * Getter method for property <tt>accumulativeyments</tt>.
     * 
     * @return property value of accumulativeyments
     */
    public double getAccumulativeyments() {
        return accumulativeyments;
    }

    /**
     * Setter method for property <tt>accumulativeyments</tt>.
     * 
     * @param accumulativeyments value to be assigned to property accumulativeyments
     */
    public void setAccumulativeyments(double accumulativeyments) {
        this.accumulativeyments = accumulativeyments;
    }

    /**
     * Getter method for property <tt>unpaid</tt>.
     * 
     * @return property value of unpaid
     */
    public double getUnpaid() {
        return unpaid;
    }

    /**
     * Setter method for property <tt>unpaid</tt>.
     * 
     * @param unpaid value to be assigned to property unpaid
     */
    public void setUnpaid(double unpaid) {
        this.unpaid = unpaid;
    }

    /**
     * Getter method for property <tt>paymentPurpose</tt>.
     * 
     * @return property value of paymentPurpose
     */
    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    /**
     * Setter method for property <tt>paymentPurpose</tt>.
     * 
     * @param paymentPurpose value to be assigned to property paymentPurpose
     */
    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    /**
     * Getter method for property <tt>groups</tt>.
     * 
     * @return property value of groups
     */
    public List<LoanGroup> getGroups() {
        return groups;
    }

    /**
     * Setter method for property <tt>groups</tt>.
     * 
     * @param groups value to be assigned to property groups
     */
    public void setGroups(List<LoanGroup> groups) {
        this.groups = groups;
    }

}
