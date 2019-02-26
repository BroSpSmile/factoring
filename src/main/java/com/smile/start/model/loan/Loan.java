/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.loan;

import java.io.Serializable;
import java.util.Date;

import com.smile.start.dto.OrganizationalDTO;
import com.smile.start.model.auth.User;
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

    /** 申请类型 */
    private LoanType          type;

    /** 申请部门 */
    private OrganizationalDTO department;

    /** 申请人 */
    private User              user;

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

    /** 收款单位名称 */
    private String            payeeName;

    /** 收款方开户银行 */
    private String            payeeBankName;

    /** 收款方银行账号 */
    private String            payeeAccountNo;

    /** 付款单位名称 */
    private String            payerName;

    /** 付款方开户银行 */
    private String            payerBankName;

    /** 付款方银行账号 */
    private String            payerAccountNo;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (type != null ? "type\":\"" + type + "\", \"" : "") + (department != null ? "department\":\"" + department + "\", \"" : "")
               + (user != null ? "user\":\"" + user + "\", \"" : "") + (createTime != null ? "createTime\":\"" + createTime + "\", \"" : "")
               + (project != null ? "project\":\"" + project + "\", \"" : "") + "subscriptionAmount\":\"" + subscriptionAmount + "\", \"payments\":\"" + payments + "\", \""
               + (chineseAmount != null ? "chineseAmount\":\"" + chineseAmount + "\", \"" : "") + "accumulativeyments\":\"" + accumulativeyments + "\", \"unpaid\":\"" + unpaid
               + "\", \"" + (paymentPurpose != null ? "paymentPurpose\":\"" + paymentPurpose + "\", \"" : "") + (payeeName != null ? "payeeName\":\"" + payeeName + "\", \"" : "")
               + (payeeBankName != null ? "payeeBankName\":\"" + payeeBankName + "\", \"" : "") + (payeeAccountNo != null ? "payeeAccountNo\":\"" + payeeAccountNo + "\", \"" : "")
               + (payerName != null ? "payerName\":\"" + payerName + "\", \"" : "") + (payerBankName != null ? "payerBankName\":\"" + payerBankName + "\", \"" : "")
               + (payerAccountNo != null ? "payerAccountNo\":\"" + payerAccountNo : "") + "\"}  ";
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
    public OrganizationalDTO getDepartment() {
        return department;
    }

    /**
     * Setter method for property <tt>department</tt>.
     * 
     * @param department value to be assigned to property department
     */
    public void setDepartment(OrganizationalDTO department) {
        this.department = department;
    }

    /**
     * Getter method for property <tt>user</tt>.
     * 
     * @return property value of user
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter method for property <tt>user</tt>.
     * 
     * @param user value to be assigned to property user
     */
    public void setUser(User user) {
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
     * Getter method for property <tt>payeeName</tt>.
     * 
     * @return property value of payeeName
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     * Setter method for property <tt>payeeName</tt>.
     * 
     * @param payeeName value to be assigned to property payeeName
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    /**
     * Getter method for property <tt>payeeBankName</tt>.
     * 
     * @return property value of payeeBankName
     */
    public String getPayeeBankName() {
        return payeeBankName;
    }

    /**
     * Setter method for property <tt>payeeBankName</tt>.
     * 
     * @param payeeBankName value to be assigned to property payeeBankName
     */
    public void setPayeeBankName(String payeeBankName) {
        this.payeeBankName = payeeBankName;
    }

    /**
     * Getter method for property <tt>payeeAccountNo</tt>.
     * 
     * @return property value of payeeAccountNo
     */
    public String getPayeeAccountNo() {
        return payeeAccountNo;
    }

    /**
     * Setter method for property <tt>payeeAccountNo</tt>.
     * 
     * @param payeeAccountNo value to be assigned to property payeeAccountNo
     */
    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    /**
     * Getter method for property <tt>payerName</tt>.
     * 
     * @return property value of payerName
     */
    public String getPayerName() {
        return payerName;
    }

    /**
     * Setter method for property <tt>payerName</tt>.
     * 
     * @param payerName value to be assigned to property payerName
     */
    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    /**
     * Getter method for property <tt>payerBankName</tt>.
     * 
     * @return property value of payerBankName
     */
    public String getPayerBankName() {
        return payerBankName;
    }

    /**
     * Setter method for property <tt>payerBankName</tt>.
     * 
     * @param payerBankName value to be assigned to property payerBankName
     */
    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    /**
     * Getter method for property <tt>payerAccountNo</tt>.
     * 
     * @return property value of payerAccountNo
     */
    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    /**
     * Setter method for property <tt>payerAccountNo</tt>.
     * 
     * @param payerAccountNo value to be assigned to property payerAccountNo
     */
    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

}
