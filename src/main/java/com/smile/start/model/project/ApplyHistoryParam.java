package com.smile.start.model.project;

import com.smile.start.model.enums.AuditType;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核历史查询参数
 *
 * @author Joseph
 * @version v1.0 2019/5/3 10:55, ApplyHistoryParam.java
 * @since 1.8
 */
public class ApplyHistoryParam implements Serializable {
    private static final long serialVersionUID = -4013593594252175874L;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 申请类型
     */
    private AuditType auditType;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 申请人
     */
    private String applyUser;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public AuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }
}
