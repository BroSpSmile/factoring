package com.smile.start.model.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务顾问服务协议
 * @author Joseph
 * @version v1.0 2019/3/23 14:45, ContractFasa.java
 * @since 1.8
 */
public class ContractFasa implements Serializable {
    private static final long serialVersionUID = -965336133013698103L;

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
     * 协议编号
     */
    private String fasaCode;

    /**
     * 甲方公司名称
     */
    private String fpCompanyName;

    /**
     * 甲方住所
     */
    private String fpResidence;

    /**
     * 甲方法定代表人
     */
    private String fpLegalPerson;

    /**
     * 甲方邮编
     */
    private String fpPostCode;

    /**
     * 甲方电话
     */
    private String fpTelephone;

    /**
     * 甲方传真
     */
    private String fpFax;

    /**
     * 协议签署地
     */
    private String signAddress;

    /**
     * 协议签署日期
     */
    private Date signDate;

    /**
     * 财务顾问费
     */
    private BigDecimal advisoryServiceMoney;

    /**
     * 财务顾问费大写
     */
    private String advisoryServiceMoneyUpper;

    /**
     * 财务顾问费约定
     */
    private String advisoryServiceMoneyAppointment;

    /**
     * 乙方银行名称
     */
    private String spBankName;

    /**
     * 乙方银行账户
     */
    private String spAccount;

    /**
     * 协议有效期月数
     */
    private Integer expiryDateMonth;

    /**
     * 甲方签字日期
     */
    private Date fpSignatureDate;

    /**
     * 乙方签字日期
     */
    private Date spSignatureDate;

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

    public String getFasaCode() {
        return fasaCode;
    }

    public void setFasaCode(String fasaCode) {
        this.fasaCode = fasaCode;
    }

    public String getFpCompanyName() {
        return fpCompanyName;
    }

    public void setFpCompanyName(String fpCompanyName) {
        this.fpCompanyName = fpCompanyName;
    }

    public String getFpResidence() {
        return fpResidence;
    }

    public void setFpResidence(String fpResidence) {
        this.fpResidence = fpResidence;
    }

    public String getFpLegalPerson() {
        return fpLegalPerson;
    }

    public void setFpLegalPerson(String fpLegalPerson) {
        this.fpLegalPerson = fpLegalPerson;
    }

    public String getFpPostCode() {
        return fpPostCode;
    }

    public void setFpPostCode(String fpPostCode) {
        this.fpPostCode = fpPostCode;
    }

    public String getFpTelephone() {
        return fpTelephone;
    }

    public void setFpTelephone(String fpTelephone) {
        this.fpTelephone = fpTelephone;
    }

    public String getFpFax() {
        return fpFax;
    }

    public void setFpFax(String fpFax) {
        this.fpFax = fpFax;
    }

    public String getSignAddress() {
        return signAddress;
    }

    public void setSignAddress(String signAddress) {
        this.signAddress = signAddress;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public BigDecimal getAdvisoryServiceMoney() {
        return advisoryServiceMoney;
    }

    public void setAdvisoryServiceMoney(BigDecimal advisoryServiceMoney) {
        this.advisoryServiceMoney = advisoryServiceMoney;
    }

    public String getAdvisoryServiceMoneyUpper() {
        return advisoryServiceMoneyUpper;
    }

    public void setAdvisoryServiceMoneyUpper(String advisoryServiceMoneyUpper) {
        this.advisoryServiceMoneyUpper = advisoryServiceMoneyUpper;
    }

    public String getAdvisoryServiceMoneyAppointment() {
        return advisoryServiceMoneyAppointment;
    }

    public void setAdvisoryServiceMoneyAppointment(String advisoryServiceMoneyAppointment) {
        this.advisoryServiceMoneyAppointment = advisoryServiceMoneyAppointment;
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

    public Integer getExpiryDateMonth() {
        return expiryDateMonth;
    }

    public void setExpiryDateMonth(Integer expiryDateMonth) {
        this.expiryDateMonth = expiryDateMonth;
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
}
