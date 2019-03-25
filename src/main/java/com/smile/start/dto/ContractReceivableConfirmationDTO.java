package com.smile.start.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
     * 确认函编号
     */
    private String confirmationCode;

    /**
     * 让与人
     */
    private String assignor;

    /**
     * 签署日期
     */
    private Date signDate;

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
     * 应收账款受让款大写
     */
    private String receivableAssigneeMoneyUpper;

    /**
     * 应收账款受让款类别：不低于、为
     */
    private String receivableAssigneeMoneyType;

    /**
     * 未支付应收账款受让款
     */
    private BigDecimal unpaidReceivableAssigneeMoney;

    /**
     * 未支付应收账款受让款大写
     */
    private String unpaidReceivableAssigneeMoneyUpper;

    /**
     * 未支付应收账款受让款类别：不低于、为
     */
    private String unpaidReceivableAssigneeMoneyType;

    /**
     * 应收账款回收款
     */
    private BigDecimal receivableRecoveryMoney;

    /**
     * 应收账款回收款大写
     */
    private String receivableRecoveryMoneyUpper;

    /**
     * 应收账款回收款截止日期
     */
    private Date receivableExpiryDate;

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
     * 未支付让与人对债务人的应收账款
     */
    private BigDecimal unpaidAssignorAbligorReceivable;

    /**
     * 未支付让与人对债务人的应收账款大写
     */
    private String unpaidAssignorAbligorReceivableUpper;

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
    private Date assignorCommitDate;

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

    /**
     * 受让人签字日期
     */
    private Date assigneeSignatureDate;

    /**
     * 让与人公司名称
     */
    private String assignorCompanyName;

    /**
     * 让与人签字日期
     */
    private Date assignorSignatureDate;

    /**
     * 债务人公司名称
     */
    private String obligorCompanyName;

    /**
     * 债务人签字日期
     */
    private Date obligorSignatureDate;

    /**
     * 商务合同标的物名称
     */
    private String nameOfSubject;

    /**
     * 发票/收据所载金额（元）
     */
    private BigDecimal invoiceMoney;

    /**
     * 发票/收据所载金额类别：不低于、为
     */
    private String invoiceMoneyType;


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

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getAssignor() {
        return assignor;
    }

    public void setAssignor(String assignor) {
        this.assignor = assignor;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
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

    public String getReceivableAssigneeMoneyUpper() {
        return receivableAssigneeMoneyUpper;
    }

    public void setReceivableAssigneeMoneyUpper(String receivableAssigneeMoneyUpper) {
        this.receivableAssigneeMoneyUpper = receivableAssigneeMoneyUpper;
    }

    public BigDecimal getReceivableRecoveryMoney() {
        return receivableRecoveryMoney;
    }

    public void setReceivableRecoveryMoney(BigDecimal receivableRecoveryMoney) {
        this.receivableRecoveryMoney = receivableRecoveryMoney;
    }

    public String getReceivableRecoveryMoneyUpper() {
        return receivableRecoveryMoneyUpper;
    }

    public void setReceivableRecoveryMoneyUpper(String receivableRecoveryMoneyUpper) {
        this.receivableRecoveryMoneyUpper = receivableRecoveryMoneyUpper;
    }

    public Date getReceivableExpiryDate() {
        return receivableExpiryDate;
    }

    public void setReceivableExpiryDate(Date receivableExpiryDate) {
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

    public Date getAssignorCommitDate() {
        return assignorCommitDate;
    }

    public void setAssignorCommitDate(Date assignorCommitDate) {
        this.assignorCommitDate = assignorCommitDate;
    }

    public Date getAssigneeSignatureDate() {
        return assigneeSignatureDate;
    }

    public void setAssigneeSignatureDate(Date assigneeSignatureDate) {
        this.assigneeSignatureDate = assigneeSignatureDate;
    }

    public String getAssignorCompanyName() {
        return assignorCompanyName;
    }

    public void setAssignorCompanyName(String assignorCompanyName) {
        this.assignorCompanyName = assignorCompanyName;
    }

    public Date getAssignorSignatureDate() {
        return assignorSignatureDate;
    }

    public void setAssignorSignatureDate(Date assignorSignatureDate) {
        this.assignorSignatureDate = assignorSignatureDate;
    }

    public String getObligorCompanyName() {
        return obligorCompanyName;
    }

    public void setObligorCompanyName(String obligorCompanyName) {
        this.obligorCompanyName = obligorCompanyName;
    }

    public Date getObligorSignatureDate() {
        return obligorSignatureDate;
    }

    public void setObligorSignatureDate(Date obligorSignatureDate) {
        this.obligorSignatureDate = obligorSignatureDate;
    }

    public String getNameOfSubject() {
        return nameOfSubject;
    }

    public void setNameOfSubject(String nameOfSubject) {
        this.nameOfSubject = nameOfSubject;
    }

    public BigDecimal getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(BigDecimal invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public BigDecimal getUnpaidReceivableAssigneeMoney() {
        return unpaidReceivableAssigneeMoney;
    }

    public void setUnpaidReceivableAssigneeMoney(BigDecimal unpaidReceivableAssigneeMoney) {
        this.unpaidReceivableAssigneeMoney = unpaidReceivableAssigneeMoney;
    }

    public String getUnpaidReceivableAssigneeMoneyUpper() {
        return unpaidReceivableAssigneeMoneyUpper;
    }

    public void setUnpaidReceivableAssigneeMoneyUpper(String unpaidReceivableAssigneeMoneyUpper) {
        this.unpaidReceivableAssigneeMoneyUpper = unpaidReceivableAssigneeMoneyUpper;
    }

    public BigDecimal getUnpaidAssignorAbligorReceivable() {
        return unpaidAssignorAbligorReceivable;
    }

    public void setUnpaidAssignorAbligorReceivable(BigDecimal unpaidAssignorAbligorReceivable) {
        this.unpaidAssignorAbligorReceivable = unpaidAssignorAbligorReceivable;
    }

    public String getUnpaidAssignorAbligorReceivableUpper() {
        return unpaidAssignorAbligorReceivableUpper;
    }

    public void setUnpaidAssignorAbligorReceivableUpper(String unpaidAssignorAbligorReceivableUpper) {
        this.unpaidAssignorAbligorReceivableUpper = unpaidAssignorAbligorReceivableUpper;
    }

    public String getReceivableAssigneeMoneyType() {
        return receivableAssigneeMoneyType;
    }

    public void setReceivableAssigneeMoneyType(String receivableAssigneeMoneyType) {
        this.receivableAssigneeMoneyType = receivableAssigneeMoneyType;
    }

    public String getUnpaidReceivableAssigneeMoneyType() {
        return unpaidReceivableAssigneeMoneyType;
    }

    public void setUnpaidReceivableAssigneeMoneyType(String unpaidReceivableAssigneeMoneyType) {
        this.unpaidReceivableAssigneeMoneyType = unpaidReceivableAssigneeMoneyType;
    }

    public String getInvoiceMoneyType() {
        return invoiceMoneyType;
    }

    public void setInvoiceMoneyType(String invoiceMoneyType) {
        this.invoiceMoneyType = invoiceMoneyType;
    }
}
