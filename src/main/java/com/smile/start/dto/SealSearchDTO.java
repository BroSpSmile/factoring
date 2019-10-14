package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/3/17 21:28, SealSearchDTO.java
 * @since 1.8
 */
public class SealSearchDTO implements Serializable {

    /** uid */
    private static final long serialVersionUID = 2766828140516566029L;

    /** 项目编号 */
    private String            projectCode;

    /** 项目名称 */
    private String            projectName;

    /** 业务负责人 */
    private String            projectPerson;

    /** 用印状态：0、未用印；1、已用印 */
    private Integer           sealStatus;

    /** 用印人 */
    private String            sealUser;

    @Override
    public String toString() {
        return "{\"SealSearchDTO\":{" + "\"projectCode\":\"" + projectCode + '\"' + ",\"projectName\":\"" + projectName + '\"' + ",\"projectPerson\":\"" + projectPerson + '\"'
               + ",\"sealStatus\":" + sealStatus + ",\"sealUser\":\"" + sealUser + '\"' + "}}";

    }

    /**
     * Getter method for property <tt>projectCode</tt>.
     *
     * @return property value of projectCode
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Setter method for property <tt>projectCode</tt>.
     *
     * @param projectCode value to be assigned to property  projectCode
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Getter method for property <tt>projectName</tt>.
     *
     * @return property value of projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Setter method for property <tt>projectName</tt>.
     *
     * @param projectName value to be assigned to property  projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Getter method for property <tt>projectPerson</tt>.
     *
     * @return property value of projectPerson
     */
    public String getProjectPerson() {
        return projectPerson;
    }

    /**
     * Setter method for property <tt>projectPerson</tt>.
     *
     * @param projectPerson value to be assigned to property  projectPerson
     */
    public void setProjectPerson(String projectPerson) {
        this.projectPerson = projectPerson;
    }

    /**
     * Getter method for property <tt>sealStatus</tt>.
     *
     * @return property value of sealStatus
     */
    public Integer getSealStatus() {
        return sealStatus;
    }

    /**
     * Setter method for property <tt>sealStatus</tt>.
     *
     * @param sealStatus value to be assigned to property  sealStatus
     */
    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }

    /**
     * Getter method for property <tt>sealUser</tt>.
     *
     * @return property value of sealUser
     */
    public String getSealUser() {
        return sealUser;
    }

    /**
     * Setter method for property <tt>sealUser</tt>.
     *
     * @param sealUser value to be assigned to property  sealUser
     */
    public void setSealUser(String sealUser) {
        this.sealUser = sealUser;
    }
}
