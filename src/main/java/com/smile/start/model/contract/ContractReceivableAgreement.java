package com.smile.start.model.contract;

import java.io.Serializable;

/**
 * 合同应收账款转让确认函
 * @author Joseph
 * @version v1.0 2019/2/19 10:16, ContractReceivableAgreement.java
 * @since 1.8
 */
public class ContractReceivableAgreement implements Serializable {
    private static final long serialVersionUID = 9140275426562952667L;

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
    private String contractSignDay;

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

    public String getContractSignDay() {
        return contractSignDay;
    }

    public void setContractSignDay(String contractSignDay) {
        this.contractSignDay = contractSignDay;
    }
}
