package com.smile.start.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 21:28, ContractReceivableConfirmationDTO.java
 * @since 1.8
 */
public class ContractReceivableConfirmationDTO implements Serializable {
    private static final long serialVersionUID = 1238783392027860047L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 合同流水
     */
    private String contractSerialNo;

    /**
     * 让与人
     */
    private String assignor;

    /**
     * 签署日期
     */
    private String signDate;

    /**
     * 债务人
     */
    private String obligor;

    /**
     * 商务合同名称
     */
    private String businessContractName;

    /**
     * 应收账款受让款
     */
    private BigDecimal receivableAssigneeMoney;

    /**
     * 应收账款回收款
     */
    private BigDecimal receivableRecoveryMoney;

    /**
     * 应收账款回收款截止日期
     */
    private String receivableExpiryDate;

    /**
     * 合同应收账款
     */
    private BigDecimal contractReceivable;

    /**
     * 合同应收账款大写
     */
    private String contractReceivableUpper;

    /**
     * 让与人对债务人的应收账款
     */
    private BigDecimal assignorAbligorReceivable;

    /**
     * 让与人对债务人的应收账款大写
     */
    private String assignorAbligorReceivableUpper;

    /**
     * 受让人已向让与人支付的应收账款受让款
     */
    private BigDecimal receivableAssigneeMoneyPaid;

    /**
     * 受让人已向让与人支付的应收账款受让款大写
     */
    private String receivableAssigneeMoneyPaidUpper;

    /**
     * 让与人提交资料日期
     */
    private String assignorCommitDate;

    /**
     * 受让人户名
     */
    private String assigneeAccountName;

    /**
     * 受让人开户银行
     */
    private String assigneeBankName;

    /**
     * 受让人账户
     */
    private String assigneeAccount;

    /**
     * 确认函签订地
     */
    private String confirmationAddress;

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

    public String getContractSerialNo() {
        return contractSerialNo;
    }

    public void setContractSerialNo(String contractSerialNo) {
        this.contractSerialNo = contractSerialNo;
    }

    public String getAssignor() {
        return assignor;
    }

    public void setAssignor(String assignor) {
        this.assignor = assignor;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getObligor() {
        return obligor;
    }

    public void setObligor(String obligor) {
        this.obligor = obligor;
    }

    public String getBusinessContractName() {
        return businessContractName;
    }

    public void setBusinessContractName(String businessContractName) {
        this.businessContractName = businessContractName;
    }

    public BigDecimal getReceivableAssigneeMoney() {
        return receivableAssigneeMoney;
    }

    public void setReceivableAssigneeMoney(BigDecimal receivableAssigneeMoney) {
        this.receivableAssigneeMoney = receivableAssigneeMoney;
    }

    public BigDecimal getReceivableRecoveryMoney() {
        return receivableRecoveryMoney;
    }

    public void setReceivableRecoveryMoney(BigDecimal receivableRecoveryMoney) {
        this.receivableRecoveryMoney = receivableRecoveryMoney;
    }

    public String getReceivableExpiryDate() {
        return receivableExpiryDate;
    }

    public void setReceivableExpiryDate(String receivableExpiryDate) {
        this.receivableExpiryDate = receivableExpiryDate;
    }

    public BigDecimal getContractReceivable() {
        return contractReceivable;
    }

    public void setContractReceivable(BigDecimal contractReceivable) {
        this.contractReceivable = contractReceivable;
    }

    public String getContractReceivableUpper() {
        return contractReceivableUpper;
    }

    public void setContractReceivableUpper(String contractReceivableUpper) {
        this.contractReceivableUpper = contractReceivableUpper;
    }

    public BigDecimal getAssignorAbligorReceivable() {
        return assignorAbligorReceivable;
    }

    public void setAssignorAbligorReceivable(BigDecimal assignorAbligorReceivable) {
        this.assignorAbligorReceivable = assignorAbligorReceivable;
    }

    public String getAssignorAbligorReceivableUpper() {
        return assignorAbligorReceivableUpper;
    }

    public void setAssignorAbligorReceivableUpper(String assignorAbligorReceivableUpper) {
        this.assignorAbligorReceivableUpper = assignorAbligorReceivableUpper;
    }

    public BigDecimal getReceivableAssigneeMoneyPaid() {
        return receivableAssigneeMoneyPaid;
    }

    public void setReceivableAssigneeMoneyPaid(BigDecimal receivableAssigneeMoneyPaid) {
        this.receivableAssigneeMoneyPaid = receivableAssigneeMoneyPaid;
    }

    public String getReceivableAssigneeMoneyPaidUpper() {
        return receivableAssigneeMoneyPaidUpper;
    }

    public void setReceivableAssigneeMoneyPaidUpper(String receivableAssigneeMoneyPaidUpper) {
        this.receivableAssigneeMoneyPaidUpper = receivableAssigneeMoneyPaidUpper;
    }

    public String getAssignorCommitDate() {
        return assignorCommitDate;
    }

    public void setAssignorCommitDate(String assignorCommitDate) {
        this.assignorCommitDate = assignorCommitDate;
    }

    public String getAssigneeAccountName() {
        return assigneeAccountName;
    }

    public void setAssigneeAccountName(String assigneeAccountName) {
        this.assigneeAccountName = assigneeAccountName;
    }

    public String getAssigneeBankName() {
        return assigneeBankName;
    }

    public void setAssigneeBankName(String assigneeBankName) {
        this.assigneeBankName = assigneeBankName;
    }

    public String getAssigneeAccount() {
        return assigneeAccount;
    }

    public void setAssigneeAccount(String assigneeAccount) {
        this.assigneeAccount = assigneeAccount;
    }

    public String getConfirmationAddress() {
        return confirmationAddress;
    }

    public void setConfirmationAddress(String confirmationAddress) {
        this.confirmationAddress = confirmationAddress;
    }

}
