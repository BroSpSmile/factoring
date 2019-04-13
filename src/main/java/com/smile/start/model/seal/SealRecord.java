package com.smile.start.model.seal;

import java.io.Serializable;
import java.util.Date;

/**
 * 用印记录实体
 * @author Joseph
 * @version v1.0 2019/4/13 13:57, SealRecord.java
 * @since 1.8
 */
public class SealRecord implements Serializable {
    private static final long serialVersionUID = 1255193105259665732L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 用户完成时间
     */
    private Date sealFinishTime;

    /**
     * 用印人
     */
    private String sealUser;

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getSealFinishTime() {
        return sealFinishTime;
    }

    public void setSealFinishTime(Date sealFinishTime) {
        this.sealFinishTime = sealFinishTime;
    }

    public String getSealUser() {
        return sealUser;
    }

    public void setSealUser(String sealUser) {
        this.sealUser = sealUser;
    }
}
