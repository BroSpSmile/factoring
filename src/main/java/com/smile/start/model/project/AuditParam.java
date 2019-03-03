/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.Date;

import com.smile.start.model.auth.User;
import com.smile.start.model.enums.AuditType;

/**
 * 审核查询条件
 * @author smile.jing
 * @version $Id: AuditParam.java, v 0.1 Mar 2, 2019 10:21:19 PM smile.jing Exp $
 */
public class AuditParam implements Serializable {

    /** UID */
    private static final long serialVersionUID = -6333715772335192986L;

    /** 当前审核人 */
    private User              audit;

    /** 项目编号 */
    private String            projectId;

    /** 申请类型 */
    private AuditType         auditType;

    /** 申请人 */
    private User              applicant;

    /** 开始时间 */
    private Date              beginTime;

    /** 结束时间 */
    private Date              endTime;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (audit != null ? "audit\":\"" + audit + "\", \"" : "") + (projectId != null ? "projectId\":\"" + projectId + "\", \"" : "")
               + (auditType != null ? "auditType\":\"" + auditType + "\", \"" : "") + (applicant != null ? "applicant\":\"" + applicant + "\", \"" : "")
               + (beginTime != null ? "beginTime\":\"" + beginTime + "\", \"" : "") + (endTime != null ? "endTime\":\"" + endTime : "") + "\"}  ";
    }

    /**
     * Getter method for property <tt>audit</tt>.
     * 
     * @return property value of audit
     */
    public User getAudit() {
        return audit;
    }

    /**
     * Setter method for property <tt>audit</tt>.
     * 
     * @param audit value to be assigned to property audit
     */
    public void setAudit(User audit) {
        this.audit = audit;
    }

    /**
     * Getter method for property <tt>projectId</tt>.
     * 
     * @return property value of projectId
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Setter method for property <tt>projectId</tt>.
     * 
     * @param projectId value to be assigned to property projectId
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Getter method for property <tt>auditType</tt>.
     * 
     * @return property value of auditType
     */
    public AuditType getAuditType() {
        return auditType;
    }

    /**
     * Setter method for property <tt>auditType</tt>.
     * 
     * @param auditType value to be assigned to property auditType
     */
    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }

    /**
     * Getter method for property <tt>applicant</tt>.
     * 
     * @return property value of applicant
     */
    public User getApplicant() {
        return applicant;
    }

    /**
     * Setter method for property <tt>applicant</tt>.
     * 
     * @param applicant value to be assigned to property applicant
     */
    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    /**
     * Getter method for property <tt>beginTime</tt>.
     * 
     * @return property value of beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * Setter method for property <tt>beginTime</tt>.
     * 
     * @param beginTime value to be assigned to property beginTime
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Getter method for property <tt>endTime</tt>.
     * 
     * @return property value of endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     * 
     * @param endTime value to be assigned to property endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
