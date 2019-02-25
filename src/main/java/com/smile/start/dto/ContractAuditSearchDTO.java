package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/2/25 15:25, ContractAuditSearchDTO.java
 * @since 1.8
 */
public class ContractAuditSearchDTO implements Serializable {
    private static final long serialVersionUID = 3136629365807881016L;

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
     * 当前登录人
     */
    private String userSerialNo;

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

    public String getUserSerialNo() {
        return userSerialNo;
    }

    public void setUserSerialNo(String userSerialNo) {
        this.userSerialNo = userSerialNo;
    }

    @Override
    public String toString() {
        return "ContractAuditSearchDTO{" +
            "contractCode='" + contractCode + '\'' +
            ", contractName='" + contractName + '\'' +
            ", projectMode=" + projectMode +
            ", userSerialNo='" + userSerialNo + '\'' +
            '}';
    }
}
