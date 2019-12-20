/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.tianyan.model;

import java.util.List;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: CompanyInfo
 * @date Date : 2019年12月18日 18:47
 */
public class CompanyInfo {
    /** id */
    private long            id;

    /** 公司名称 */
    private String          name;

    /** 法人 */
    private String          legalPersonName;

    /** 法人类型 ，1 人 2 公司 */
    private int             legalPersonType;

    /** 注册号 */
    private String          regNumber;

    /** 行业 */
    private String          industry;

    /** 企业类型 */
    private String          companyOrgType;

    /** 注册地址 */
    private String          regLocation;

    /** 成立时间 */
    private String          estiblishTime;

    /** 经营范围 */
    private String          businessScope;

    /** 核准时间 */
    private String          approvedTime;

    /** 经营状态 */
    private String          regStatus;

    /** 注册资本 */
    private String          regCapital;

    /** 登记机关 */
    private String          regInstitute;

    /** 组织机构代码 */
    private String          orgNumber;

    /** 统一社会信用代码 */
    private String          creditCode;

    /** 更新时间 */
    private String          updatetime;

    /** company 的id  */
    private String          companyId;

    /** 纳税人识别号 */
    private String          taxNumber;

    /** 联系电话 */
    private String          phoneNumber;

    /** 主要成员信息 */
    private List<StaffInfo> staffList;

    @Override
    public String toString() {
        return "{\"CompanyInfo\":{" + "\"id\":" + id + ",\"name\":\"" + name + '\"' + ",\"legalPersonName\":\"" + legalPersonName + '\"' + ",\"legalPersonType\":" + legalPersonType
               + ",\"regNumber\":\"" + regNumber + '\"' + ",\"industry\":\"" + industry + '\"' + ",\"companyOrgType\":\"" + companyOrgType + '\"' + ",\"regLocation\":\""
               + regLocation + '\"' + ",\"estiblishTime\":\"" + estiblishTime + '\"' + ",\"businessScope\":\"" + businessScope + '\"' + ",\"approvedTime\":\"" + approvedTime + '\"'
               + ",\"regStatus\":\"" + regStatus + '\"' + ",\"regCapital\":\"" + regCapital + '\"' + ",\"regInstitute\":\"" + regInstitute + '\"' + ",\"orgNumber\":\"" + orgNumber
               + '\"' + ",\"creditCode\":\"" + creditCode + '\"' + ",\"updatetime\":\"" + updatetime + '\"' + ",\"companyId\":\"" + companyId + '\"' + ",\"taxNumber\":\""
               + taxNumber + '\"' + ",\"phoneNumber\":\"" + phoneNumber + '\"' + ",\"staffList\":" + staffList + "}}";

    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property  id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property  name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>legalPersonName</tt>.
     *
     * @return property value of legalPersonName
     */
    public String getLegalPersonName() {
        return legalPersonName;
    }

    /**
     * Setter method for property <tt>legalPersonName</tt>.
     *
     * @param legalPersonName value to be assigned to property  legalPersonName
     */
    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    /**
     * Getter method for property <tt>legalPersonType</tt>.
     *
     * @return property value of legalPersonType
     */
    public int getLegalPersonType() {
        return legalPersonType;
    }

    /**
     * Setter method for property <tt>legalPersonType</tt>.
     *
     * @param legalPersonType value to be assigned to property  legalPersonType
     */
    public void setLegalPersonType(int legalPersonType) {
        this.legalPersonType = legalPersonType;
    }

    /**
     * Getter method for property <tt>regNumber</tt>.
     *
     * @return property value of regNumber
     */
    public String getRegNumber() {
        return regNumber;
    }

    /**
     * Setter method for property <tt>regNumber</tt>.
     *
     * @param regNumber value to be assigned to property  regNumber
     */
    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    /**
     * Getter method for property <tt>industry</tt>.
     *
     * @return property value of industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * Setter method for property <tt>industry</tt>.
     *
     * @param industry value to be assigned to property  industry
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * Getter method for property <tt>companyOrgType</tt>.
     *
     * @return property value of companyOrgType
     */
    public String getCompanyOrgType() {
        return companyOrgType;
    }

    /**
     * Setter method for property <tt>companyOrgType</tt>.
     *
     * @param companyOrgType value to be assigned to property  companyOrgType
     */
    public void setCompanyOrgType(String companyOrgType) {
        this.companyOrgType = companyOrgType;
    }

    /**
     * Getter method for property <tt>regLocation</tt>.
     *
     * @return property value of regLocation
     */
    public String getRegLocation() {
        return regLocation;
    }

    /**
     * Setter method for property <tt>regLocation</tt>.
     *
     * @param regLocation value to be assigned to property  regLocation
     */
    public void setRegLocation(String regLocation) {
        this.regLocation = regLocation;
    }

    /**
     * Getter method for property <tt>estiblishTime</tt>.
     *
     * @return property value of estiblishTime
     */
    public String getEstiblishTime() {
        return estiblishTime;
    }

    /**
     * Setter method for property <tt>estiblishTime</tt>.
     *
     * @param estiblishTime value to be assigned to property  estiblishTime
     */
    public void setEstiblishTime(String estiblishTime) {
        this.estiblishTime = estiblishTime;
    }

    /**
     * Getter method for property <tt>businessScope</tt>.
     *
     * @return property value of businessScope
     */
    public String getBusinessScope() {
        return businessScope;
    }

    /**
     * Setter method for property <tt>businessScope</tt>.
     *
     * @param businessScope value to be assigned to property  businessScope
     */
    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    /**
     * Getter method for property <tt>approvedTime</tt>.
     *
     * @return property value of approvedTime
     */
    public String getApprovedTime() {
        return approvedTime;
    }

    /**
     * Setter method for property <tt>approvedTime</tt>.
     *
     * @param approvedTime value to be assigned to property  approvedTime
     */
    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }

    /**
     * Getter method for property <tt>regStatus</tt>.
     *
     * @return property value of regStatus
     */
    public String getRegStatus() {
        return regStatus;
    }

    /**
     * Setter method for property <tt>regStatus</tt>.
     *
     * @param regStatus value to be assigned to property  regStatus
     */
    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    /**
     * Getter method for property <tt>regCapital</tt>.
     *
     * @return property value of regCapital
     */
    public String getRegCapital() {
        return regCapital;
    }

    /**
     * Setter method for property <tt>regCapital</tt>.
     *
     * @param regCapital value to be assigned to property  regCapital
     */
    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }

    /**
     * Getter method for property <tt>regInstitute</tt>.
     *
     * @return property value of regInstitute
     */
    public String getRegInstitute() {
        return regInstitute;
    }

    /**
     * Setter method for property <tt>regInstitute</tt>.
     *
     * @param regInstitute value to be assigned to property  regInstitute
     */
    public void setRegInstitute(String regInstitute) {
        this.regInstitute = regInstitute;
    }

    /**
     * Getter method for property <tt>orgNumber</tt>.
     *
     * @return property value of orgNumber
     */
    public String getOrgNumber() {
        return orgNumber;
    }

    /**
     * Setter method for property <tt>orgNumber</tt>.
     *
     * @param orgNumber value to be assigned to property  orgNumber
     */
    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    /**
     * Getter method for property <tt>creditCode</tt>.
     *
     * @return property value of creditCode
     */
    public String getCreditCode() {
        return creditCode;
    }

    /**
     * Setter method for property <tt>creditCode</tt>.
     *
     * @param creditCode value to be assigned to property  creditCode
     */
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    /**
     * Getter method for property <tt>updatetime</tt>.
     *
     * @return property value of updatetime
     */
    public String getUpdatetime() {
        return updatetime;
    }

    /**
     * Setter method for property <tt>updatetime</tt>.
     *
     * @param updatetime value to be assigned to property  updatetime
     */
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * Getter method for property <tt>companyId</tt>.
     *
     * @return property value of companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * Setter method for property <tt>companyId</tt>.
     *
     * @param companyId value to be assigned to property  companyId
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * Getter method for property <tt>taxNumber</tt>.
     *
     * @return property value of taxNumber
     */
    public String getTaxNumber() {
        return taxNumber;
    }

    /**
     * Setter method for property <tt>taxNumber</tt>.
     *
     * @param taxNumber value to be assigned to property  taxNumber
     */
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    /**
     * Getter method for property <tt>phoneNumber</tt>.
     *
     * @return property value of phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter method for property <tt>phoneNumber</tt>.
     *
     * @param phoneNumber value to be assigned to property  phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter method for property <tt>staffList</tt>.
     *
     * @return property value of staffList
     */
    public List<StaffInfo> getStaffList() {
        return staffList;
    }

    /**
     * Setter method for property <tt>staffList</tt>.
     *
     * @param staffList value to be assigned to property  staffList
     */
    public void setStaffList(List<StaffInfo> staffList) {
        this.staffList = staffList;
    }
}
