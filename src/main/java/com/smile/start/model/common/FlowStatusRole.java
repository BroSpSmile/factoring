package com.smile.start.model.common;

import java.io.Serializable;

/**
 * 流程状态与角色关联信息
 * @author Joseph
 * @version v1.0 2019/2/22 13:06, FlowStatusRole.java
 * @since 1.8
 */
public class FlowStatusRole implements Serializable {
    private static final long serialVersionUID = 4455415509743503671L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 流程配置业务流水
     */
    private String flowSerialNo;

    /**
     * 状态表业务流水
     */
    private String statusSerialNo;

    /**
     * 角色表业务流水
     */
    private String roleSerialNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public String getFlowSerialNo() {
        return flowSerialNo;
    }

    public void setFlowSerialNo(String flowSerialNo) {
        this.flowSerialNo = flowSerialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getStatusSerialNo() {
        return statusSerialNo;
    }

    public void setStatusSerialNo(String statusSerialNo) {
        this.statusSerialNo = statusSerialNo;
    }

    public String getRoleSerialNo() {
        return roleSerialNo;
    }

    public void setRoleSerialNo(String roleSerialNo) {
        this.roleSerialNo = roleSerialNo;
    }
}
