package com.smile.start.model.seal;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目用印信息实体
 * @author Joseph
 * @version v1.0 2019/3/17 20:54, ProjectSeal.java
 * @since 1.8
 */
public class ProjectSeal implements Serializable {
    private static final long serialVersionUID = 2960041055461286290L;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 合同审核进度ID
     */
    private Long auditId;

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
     * 用印完成时间
     */
    private Date sealFinishTime;

    /**
     * 用印状态：0、未用印；1、已用印
     */
    private Integer sealStatus;

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

    public Date getSealFinishTime() {
        return sealFinishTime;
    }

    public void setSealFinishTime(Date sealFinishTime) {
        this.sealFinishTime = sealFinishTime;
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
