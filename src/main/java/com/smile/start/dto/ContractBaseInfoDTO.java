package com.smile.start.dto;

import java.io.Serializable;

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
     * 业务流水
     */
    private String            serialNo;

    /**
     * 合同编号
     */
    private String            contractCode;

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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
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
}
