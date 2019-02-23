package com.smile.start.model.common;

import java.io.Serializable;

/**
 * 流程与状态的关联关系
 * @author Joseph
 * @version v1.0 2019/2/22 12:57, FlowStatus.java
 * @since 1.8
 */
public class FlowStatus implements Serializable {
    private static final long serialVersionUID = 7969916481196657235L;

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
     * 流程状态描述
     */
    private String flowStatusDesc;

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

    public String getFlowStatusDesc() {
        return flowStatusDesc;
    }

    public void setFlowStatusDesc(String flowStatusDesc) {
        this.flowStatusDesc = flowStatusDesc;
    }
}
