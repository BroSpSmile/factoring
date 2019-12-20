package com.smile.start.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/27 23:12, ContractSignDTO.java
 * @since 1.8
 */
public class ContractSignDTO implements Serializable {
    private static final long serialVersionUID = -6894536194501862919L;

    private Long projectId;

    private Boolean finished = false;

    private List<ContractSignListDTO> signList;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public List<ContractSignListDTO> getSignList() {
        return signList;
    }

    public void setSignList(List<ContractSignListDTO> signList) {
        this.signList = signList;
    }
}
