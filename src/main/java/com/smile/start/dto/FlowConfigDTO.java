package com.smile.start.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 12:41, FlowConfigDTO.java
 * @since 1.8
 */
public class FlowConfigDTO implements Serializable {
    private static final long serialVersionUID = -6555693113320252827L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 流程类型：1、合同；2、项目
     */
    private Integer flowType;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 状态列表
     */
    private List<FlowStatusDTO> statusList;

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

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
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

    public List<FlowStatusDTO> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<FlowStatusDTO> statusList) {
        this.statusList = statusList;
    }
}
