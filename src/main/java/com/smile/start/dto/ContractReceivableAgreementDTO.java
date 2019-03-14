package com.smile.start.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 21:27, ContractReceivableAgreementDTO.java
 * @since 1.8
 */
public class ContractReceivableAgreementDTO implements Serializable {
    private static final long serialVersionUID = -991280311079354434L;

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
    private String protocolCode;

    /**
     * 乙方名称
     */
    private String spName;

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
     * 合同签署日期
     */
    private Date contractSignDate;

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

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
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

    public Date getContractSignDate() {
        return contractSignDate;
    }

    public void setContractSignDate(Date contractSignDate) {
        this.contractSignDate = contractSignDate;
    }
}
