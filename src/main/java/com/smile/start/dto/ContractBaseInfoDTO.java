package com.smile.start.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/2/23 20:06, ContractBaseInfoDTO.java
 * @since 1.8
 */
public class ContractBaseInfoDTO implements Serializable {
    private static final long serialVersionUID = -7443932638085131876L;

    /**
     * 主键
     */
    private Long              id;

    /**
     * 项目主键
     */
    private Long              projectId;

    /**
     * 项目编号
     */
    private String            projectCode;

    /**
     * 项目名称
     */
    private String            projectName;

    /**
     * 业务流水
     */
    private String            serialNo;

    /**
     * 合同名称
     */
    private String            contractName;

    /**
     * 项目模式：1、有追索权模式；2、无追索权模式
     */
    private Integer           projectMode;

    /**
     * 合同模板：1、标准模板；2、自定义模板
     */
    private Integer           contractTemplate;

    /**
     * 状态
     */
    private Integer           status;

    /**
     * 删除标志：0、未删除；1、删除
     */
    private Integer deleteFlag;

    /**
     * 用印状态：0、未用印；1、已用印
     */
    private Integer sealStatus;

    /**
     * 是否有保理合同：1、有；0、无
     */
    private Boolean factoringContract;

    /**
     * 是否有确认函：1、有；0、无
     */
    private Boolean confirmationLetter;

    /**
     * 是否有登记协议：1、有；0、无
     */
    private Boolean registrationAgreement;

    /**
     * 是否有财务协议：1、有；0、无
     */
    private Boolean financialAgreement;

    /**
     * 是否有股东会决议：1、有；0、无
     */
    private Boolean shareholderResolution;

    /**
     * 创建人
     */
    private String            createUser;

    /**
     * 创建时间
     */
    private String            gmtCreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Integer getProjectMode() {
        return projectMode;
    }

    public void setProjectMode(Integer projectMode) {
        this.projectMode = projectMode;
    }

    public Integer getContractTemplate() {
        return contractTemplate;
    }

    public void setContractTemplate(Integer contractTemplate) {
        this.contractTemplate = contractTemplate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Boolean getFactoringContract() {
        return factoringContract;
    }

    public void setFactoringContract(Boolean factoringContract) {
        this.factoringContract = factoringContract;
    }

    public Boolean getConfirmationLetter() {
        return confirmationLetter;
    }

    public void setConfirmationLetter(Boolean confirmationLetter) {
        this.confirmationLetter = confirmationLetter;
    }

    public Boolean getRegistrationAgreement() {
        return registrationAgreement;
    }

    public void setRegistrationAgreement(Boolean registrationAgreement) {
        this.registrationAgreement = registrationAgreement;
    }

    public Boolean getFinancialAgreement() {
        return financialAgreement;
    }

    public void setFinancialAgreement(Boolean financialAgreement) {
        this.financialAgreement = financialAgreement;
    }

    public Boolean getShareholderResolution() {
        return shareholderResolution;
    }

    public void setShareholderResolution(Boolean shareholderResolution) {
        this.shareholderResolution = shareholderResolution;
    }

    public Integer getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }
}
