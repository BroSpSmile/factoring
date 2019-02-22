package com.smile.start.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 13:08, FlowStatusDTO.java
 * @since 1.8
 */
public class FlowStatusDTO implements Serializable {
    private static final long serialVersionUID = 5758822975020975759L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 流程配置表业务流水
     */
    private String flowSerialNo;

    /**
     * 流程状态
     */
    private Integer flowStatus;

    /**
     * 选中角色列表
     */
    private List<String> checkedRoleList;

    /**
     * 所有角色列表
     */
    private List<AuthRoleInfoDTO> roleList;

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

    public String getFlowSerialNo() {
        return flowSerialNo;
    }

    public void setFlowSerialNo(String flowSerialNo) {
        this.flowSerialNo = flowSerialNo;
    }

    public Integer getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

    public List<String> getCheckedRoleList() {
        return checkedRoleList;
    }

    public void setCheckedRoleList(List<String> checkedRoleList) {
        this.checkedRoleList = checkedRoleList;
    }

    public List<AuthRoleInfoDTO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AuthRoleInfoDTO> roleList) {
        this.roleList = roleList;
    }
}
