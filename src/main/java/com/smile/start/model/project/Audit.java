/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.model.auth.User;
import com.smile.start.model.enums.AuditType;

/**
 * 审核对象
 * @author smile.jing
 * @version $Id: Audit.java, v 0.1 Mar 2, 2019 8:51:34 PM smile.jing Exp $
 */
public class Audit implements Serializable {

    /** UID */
    private static final long serialVersionUID = -7623305861701564820L;

    /** 编号 */
    private Long              id;

    /** 申请类型 */
    private AuditType         auditType;

    /** 申请时间 */
    private Date              createTime;

    /** 申请人 */
    private User              applicant;

    /** 所属项目 */
    private Project           project;

    /** 当前审核步骤 */
    private Integer           step;

    /** 当前审核角色 */
    private AuthRoleInfoDTO   role;

    /** 审核人 */
    private User              auditor;

    /** 审核流程状态 */
    private List<AuditFlow>   flows;

    /** 审核历史 */
    private List<AuditRecord> records;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (id != null ? "id\":\"" + id + "\", \"" : "") + (auditType != null ? "auditType\":\"" + auditType + "\", \"" : "")
               + (createTime != null ? "createTime\":\"" + createTime + "\", \"" : "") + (applicant != null ? "applicant\":\"" + applicant + "\", \"" : "")
               + (project != null ? "project\":\"" + project + "\", \"" : "") + (step != null ? "step\":\"" + step + "\", \"" : "")
               + (role != null ? "role\":\"" + role + "\", \"" : "") + (auditor != null ? "auditor\":\"" + auditor + "\", \"" : "")
               + (records != null ? "records\":\"" + records : "") + "\"}  ";
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
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
     * Getter method for property <tt>createTime</tt>.
     * 
     * @return property value of createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Setter method for property <tt>createTime</tt>.
     * 
     * @param createTime value to be assigned to property createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * Getter method for property <tt>project</tt>.
     * 
     * @return property value of project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Setter method for property <tt>project</tt>.
     * 
     * @param project value to be assigned to property project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Getter method for property <tt>step</tt>.
     * 
     * @return property value of step
     */
    public Integer getStep() {
        return step;
    }

    /**
     * Setter method for property <tt>step</tt>.
     * 
     * @param step value to be assigned to property step
     */
    public void setStep(Integer step) {
        this.step = step;
    }

    /**
     * Getter method for property <tt>role</tt>.
     * 
     * @return property value of role
     */
    public AuthRoleInfoDTO getRole() {
        return role;
    }

    /**
     * Setter method for property <tt>role</tt>.
     * 
     * @param role value to be assigned to property role
     */
    public void setRole(AuthRoleInfoDTO role) {
        this.role = role;
    }

    /**
     * Getter method for property <tt>records</tt>.
     * 
     * @return property value of records
     */
    public List<AuditRecord> getRecords() {
        return records;
    }

    /**
     * Setter method for property <tt>records</tt>.
     * 
     * @param records value to be assigned to property records
     */
    public void setRecords(List<AuditRecord> records) {
        this.records = records;
    }

    /**
     * Getter method for property <tt>auditor</tt>.
     * 
     * @return property value of auditor
     */
    public User getAuditor() {
        return auditor;
    }

    /**
     * Setter method for property <tt>auditor</tt>.
     * 
     * @param auditor value to be assigned to property auditor
     */
    public void setAuditor(User auditor) {
        this.auditor = auditor;
    }

    /**
     * Getter method for property <tt>flows</tt>.
     * 
     * @return property value of flows
     */
    public List<AuditFlow> getFlows() {
        return flows;
    }

    /**
     * Setter method for property <tt>flows</tt>.
     * 
     * @param flows value to be assigned to property flows
     */
    public void setFlows(List<AuditFlow> flows) {
        this.flows = flows;
    }

}
