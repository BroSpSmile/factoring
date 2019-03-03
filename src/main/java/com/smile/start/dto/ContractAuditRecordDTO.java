package com.smile.start.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/2/25 17:00, ContractAuditRecordDTO.java
 * @since 1.8
 */
public class ContractAuditRecordDTO implements Serializable {
    private static final long serialVersionUID = -2576597527854202870L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务流水号
     */
    private String serialNo;

    /**
     * 合同业务流水
     */
    private String contractSerialNo;

    /**
     * 操作状态
     */
    private String operationStatus;

    /**
     * 操作类型：1、通过；2、驳回
     */
    private Integer operationType;

    /**
     * 操作人
     */
    private String operationUser;

    /**
     * 操作时间
     */
    private String operationTime;

    /**
     * 备注
     */
    private String remark;

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

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
