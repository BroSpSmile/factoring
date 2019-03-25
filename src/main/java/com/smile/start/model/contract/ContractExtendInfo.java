package com.smile.start.model.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同扩展信息
 * @author Joseph
 * @version v1.0 2019/2/19 8:32, ContractExtendInfo.java
 * @since 1.8
 */
public class ContractExtendInfo implements Serializable {
    private static final long serialVersionUID = -2686764924339437670L;

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
     * 应收账款
     */
    private BigDecimal receivableMoney;

    /**
     * 应收账款大写
     */
    private String receivableMoneyUpper;

    /**
     * 债务人享有金额
     */
    private BigDecimal obligorEnjoyMoney;

    /**
     * 债务人享有金额大写
     */
    private String obligorEnjoyMoneyUpper;

    /**
     * 应收账款受让款
     */
    private BigDecimal receivableAssigneeMoney;

    /**
     * 应收账款受让款大写
     */
    private String receivableAssigneeMoneyUpper;

    /**
     * 应收账款受让款首付款
     */
    private BigDecimal receivableAssigneeFirstMoney;

    /**
     * 应收账款受让款首付款大写
     */
    private String receivableAssigneeFirstMoneyUpper;

    /**
     * 应收账款回收款
     */
    private BigDecimal receivableRecoveryMoney;

    /**
     * 应收账款回收款大写
     */
    private String receivableRecoveryMoneyUpper;

    /**
     * 应收账款支付时间
     */
    private Date receivableRecoveryMoneyPaytime;

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
    private Date compulsoryRescissionDate;

    /**
     * 甲方签字日期
     */
    private Date fpSignatureDate;

    /**
     * 乙方签字日期
     */
    private Date spSignatureDate;

    /**
     * 计费起始日期
     */
    private Date billingStartDate;

    /**
     * 年利率
     */
    private BigDecimal interestRate;

    /**
     * 应收账款类别：不低于、为
     */
    private String receivableMoneyType;

    /**
     * 应收账款回收款类别：不低于、为
     */
    private String receivableRecoveryMoneyType;

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

    public BigDecimal getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableMoney(BigDecimal receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public String getReceivableMoneyUpper() {
        return receivableMoneyUpper;
    }

    public void setReceivableMoneyUpper(String receivableMoneyUpper) {
        this.receivableMoneyUpper = receivableMoneyUpper;
    }

    public BigDecimal getObligorEnjoyMoney() {
        return obligorEnjoyMoney;
    }

    public void setObligorEnjoyMoney(BigDecimal obligorEnjoyMoney) {
        this.obligorEnjoyMoney = obligorEnjoyMoney;
    }

    public String getObligorEnjoyMoneyUpper() {
        return obligorEnjoyMoneyUpper;
    }

    public void setObligorEnjoyMoneyUpper(String obligorEnjoyMoneyUpper) {
        this.obligorEnjoyMoneyUpper = obligorEnjoyMoneyUpper;
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

    public BigDecimal getReceivableAssigneeFirstMoney() {
        return receivableAssigneeFirstMoney;
    }

    public void setReceivableAssigneeFirstMoney(BigDecimal receivableAssigneeFirstMoney) {
        this.receivableAssigneeFirstMoney = receivableAssigneeFirstMoney;
    }

    public String getReceivableAssigneeFirstMoneyUpper() {
        return receivableAssigneeFirstMoneyUpper;
    }

    public void setReceivableAssigneeFirstMoneyUpper(String receivableAssigneeFirstMoneyUpper) {
        this.receivableAssigneeFirstMoneyUpper = receivableAssigneeFirstMoneyUpper;
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

    public Date getReceivableRecoveryMoneyPaytime() {
        return receivableRecoveryMoneyPaytime;
    }

    public void setReceivableRecoveryMoneyPaytime(Date receivableRecoveryMoneyPaytime) {
        this.receivableRecoveryMoneyPaytime = receivableRecoveryMoneyPaytime;
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

    public Date getCompulsoryRescissionDate() {
        return compulsoryRescissionDate;
    }

    public void setCompulsoryRescissionDate(Date compulsoryRescissionDate) {
        this.compulsoryRescissionDate = compulsoryRescissionDate;
    }

    public Date getFpSignatureDate() {
        return fpSignatureDate;
    }

    public void setFpSignatureDate(Date fpSignatureDate) {
        this.fpSignatureDate = fpSignatureDate;
    }

    public Date getSpSignatureDate() {
        return spSignatureDate;
    }

    public void setSpSignatureDate(Date spSignatureDate) {
        this.spSignatureDate = spSignatureDate;
    }

    public Date getBillingStartDate() {
        return billingStartDate;
    }

    public void setBillingStartDate(Date billingStartDate) {
        this.billingStartDate = billingStartDate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getReceivableMoneyType() {
        return receivableMoneyType;
    }

    public void setReceivableMoneyType(String receivableMoneyType) {
        this.receivableMoneyType = receivableMoneyType;
    }

    public String getReceivableRecoveryMoneyType() {
        return receivableRecoveryMoneyType;
    }

    public void setReceivableRecoveryMoneyType(String receivableRecoveryMoneyType) {
        this.receivableRecoveryMoneyType = receivableRecoveryMoneyType;
    }
}
