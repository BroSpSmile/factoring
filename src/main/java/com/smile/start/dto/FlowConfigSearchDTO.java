package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 12:54, FlowConfigSearchDTO.java
 * @since 1.8
 */
public class FlowConfigSearchDTO implements Serializable {
    private static final long serialVersionUID = -2860206983085165368L;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 流程类型：1、合同；2、项目
     */
    private Integer flowType;

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

    @Override
    public String toString() {
        return "FlowConfigSearchDTO{" +
            "flowName='" + flowName + '\'' +
            ", flowType=" + flowType +
            '}';
    }
}
