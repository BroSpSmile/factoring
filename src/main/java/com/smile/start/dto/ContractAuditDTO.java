package com.smile.start.dto;

import java.io.Serializable;

/**
 * 合同审核参数
 * @author Joseph
 * @version v1.0 2019/2/25 16:09, ContractAuditDTO.java
 * @since 1.8
 */
public class ContractAuditDTO implements Serializable {
    private static final long serialVersionUID = 4922651289142083963L;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 审核ID
     */
    private Long auditId;

    /**
     * 操作类型：1、审核通过；2、审核驳回
     */
    private Integer operationType;

    /**
     * 驳回状态
     */
    private Integer rejectStatus;

    /**
     * 备注、驳回原因
     */
    private String remark;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getRejectStatus() {
        return rejectStatus;
    }

    public void setRejectStatus(Integer rejectStatus) {
        this.rejectStatus = rejectStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ContractAuditDTO{" +
                "projectId=" + projectId +
                ", operationType=" + operationType +
                ", rejectStatus=" + rejectStatus +
                ", remark='" + remark + '\'' +
                '}';
    }
}
