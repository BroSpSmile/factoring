package com.smile.start.model.contract;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同基本信息
 * @author Joseph
 * @version v1.0 2019/2/19 8:29, ContractInfo.java
 * @since 1.8
 */
public class ContractInfo implements Serializable {
    private static final long serialVersionUID = 1250002625857054450L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目主键
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 项目模式：1、有追索权模式；2、无追索权模式
     */
    private Integer projectMode;

    /**
     * 合同模板：1、标准模板；2、自定义模板
     */
    private Integer contractTemplate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 删除标志：0、未删除；1、删除
     */
    private Integer deleteFlag;
    private String createUser;
    private String modifyUser;
    private Date gmtCreate;
    private Date gmtModify;

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

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}
