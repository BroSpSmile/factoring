package com.smile.start.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 21:26, ContractExtendInfoDTO.java
 * @since 1.8
 */
public class ContractExtendInfoDTO implements Serializable {
    private static final long serialVersionUID = 295902311085871340L;

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
     * 合同编号
     */
    private String contractCode;

    /**
     * 乙方公司名称
     */
    private String spCompanyName;

    /**
     * 乙方住所
     */
    private String spResidence;

    /**
     * 乙方法定代表人
     */
    private String spLegalPerson;

    /**
     * 乙方联系地址
     */
    private String spContactAddress;

    /**
     * 乙方邮编
     */
    private String spPostCode;

    /**
     * 乙方电话
     */
    private String spTelephone;

    /**
     * 乙方传真
     */
    private String spFax;

    /**
     * 债务人
     */
    private String obligor;

    /**
     * 签署日期
     */
    private Date signDate;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 应收账款受让款
     */
    private BigDecimal receivableAssigneeMoney;

    /**
     * 应收账款受让款大写
     */
    private String receivableAssigneeMoneyUpper;

    /**
     * 应收账款回收款
     */
    private BigDecimal receivableRecoveryMoney;

    /**
     * 应收账款回收款大写
     */
    private String receivableRecoveryMoneyUpper;

    /**
     * 甲方户名
     */
    private String fpAccountName;

    /**
     * 甲方银行名称
     */
    private String fpBankName;

    /**
     * 甲方银行账户
     */
    private String fpAccount;

    /**
     * 乙方户名
     */
    private String spAccountName;

    /**
     * 乙方银行名称
     */
    private String spBankName;

    /**
     * 乙方银行账户
     */
    private String spAccount;

    /**
     * 合同强制解除日期
     * @return
     */
    private String ompulsoryRescissionDate;

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

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getSpCompanyName() {
        return spCompanyName;
    }

    public void setSpCompanyName(String spCompanyName) {
        this.spCompanyName = spCompanyName;
    }

    public String getSpResidence() {
        return spResidence;
    }

    public void setSpResidence(String spResidence) {
        this.spResidence = spResidence;
    }

    public String getSpLegalPerson() {
        return spLegalPerson;
    }

    public void setSpLegalPerson(String spLegalPerson) {
        this.spLegalPerson = spLegalPerson;
    }

    public String getSpContactAddress() {
        return spContactAddress;
    }

    public void setSpContactAddress(String spContactAddress) {
        this.spContactAddress = spContactAddress;
    }

    public String getSpPostCode() {
        return spPostCode;
    }

    public void setSpPostCode(String spPostCode) {
        this.spPostCode = spPostCode;
    }

    public String getSpTelephone() {
        return spTelephone;
    }

    public void setSpTelephone(String spTelephone) {
        this.spTelephone = spTelephone;
    }

    public String getSpFax() {
        return spFax;
    }

    public void setSpFax(String spFax) {
        this.spFax = spFax;
    }

    public String getObligor() {
        return obligor;
    }

    public void setObligor(String obligor) {
        this.obligor = obligor;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
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

    public String getFpAccountName() {
        return fpAccountName;
    }

    public void setFpAccountName(String fpAccountName) {
        this.fpAccountName = fpAccountName;
    }

    public String getFpBankName() {
        return fpBankName;
    }

    public void setFpBankName(String fpBankName) {
        this.fpBankName = fpBankName;
    }

    public String getFpAccount() {
        return fpAccount;
    }

    public void setFpAccount(String fpAccount) {
        this.fpAccount = fpAccount;
    }

    public String getSpAccountName() {
        return spAccountName;
    }

    public void setSpAccountName(String spAccountName) {
        this.spAccountName = spAccountName;
    }

    public String getSpBankName() {
        return spBankName;
    }

    public void setSpBankName(String spBankName) {
        this.spBankName = spBankName;
    }

    public String getSpAccount() {
        return spAccount;
    }

    public void setSpAccount(String spAccount) {
        this.spAccount = spAccount;
    }

    public String getOmpulsoryRescissionDate() {
        return ompulsoryRescissionDate;
    }

    public void setOmpulsoryRescissionDate(String ompulsoryRescissionDate) {
        this.ompulsoryRescissionDate = ompulsoryRescissionDate;
    }
}
