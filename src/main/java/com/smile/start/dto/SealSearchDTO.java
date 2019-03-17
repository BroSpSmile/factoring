package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/3/17 21:28, SealSearchDTO.java
 * @since 1.8
 */
public class SealSearchDTO implements Serializable {
    private static final long serialVersionUID = 2766828140516566029L;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 业务负责人
     */
    private String projectPerson;

    /**
     * 用印状态：0、未用印；1、已用印
     */
    private Integer sealStatus;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPerson() {
        return projectPerson;
    }

    public void setProjectPerson(String projectPerson) {
        this.projectPerson = projectPerson;
    }

    public Integer getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }
}
