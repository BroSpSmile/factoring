package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/2/20 20:41, ContractInfoSearchDTO.java
 * @since 1.8
 */
public class ContractInfoSearchDTO implements Serializable {
    private static final long serialVersionUID = -6538832410950100423L;

    /**
     * 合同编号
     */
    private String contractCode;

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

    @Override
    public String toString() {
        return "ContractInfoSearchDTO{" +
                "contractCode='" + contractCode + '\'' +
                ", contractName='" + contractName + '\'' +
                ", projectMode=" + projectMode +
                ", contractTemplate=" + contractTemplate +
                ", status=" + status +
                '}';
    }
}
