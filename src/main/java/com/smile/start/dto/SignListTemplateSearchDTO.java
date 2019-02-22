package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/2/13 20:39, ContractSignListSearchDTO.java
 * @since 1.8
 */
public class SignListTemplateSearchDTO implements Serializable {
    private static final long serialVersionUID = 2228953529611857250L;

    private String signListName;

    /**
     * 项目模式：1、有追索权模式；2、无追索权模式
     */
    private Integer projectMode;

    public String getSignListName() {
        return signListName;
    }

    public void setSignListName(String signListName) {
        this.signListName = signListName;
    }

    public Integer getProjectMode() {
        return projectMode;
    }

    public void setProjectMode(Integer projectMode) {
        this.projectMode = projectMode;
    }

    @Override
    public String toString() {
        return "SignListTemplateSearchDTO{" +
                "signListName='" + signListName + '\'' +
                ", projectMode=" + projectMode +
                '}';
    }
}
