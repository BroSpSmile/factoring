/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.Date;

import com.smile.start.model.dto.AuthRoleInfoDTO;
import com.smile.start.model.auth.User;
import com.smile.start.model.enums.audit.AuditResult;

/**
 * 审核流程
 * @author smile.jing
 * @version $Id: AuditFlow.java, v 0.1 Mar 7, 2019 5:36:01 PM smile.jing Exp $
 */
public class AuditFlow implements Serializable {

    /** UID */
    private static final long serialVersionUID = 2472045809225914087L;

    private Long              id;

    /** 审核步骤描述 */
    private String            desc;

    /** 审核步骤 */
    private int               step;

    /** 审核角色 */
    private AuthRoleInfoDTO   role;

    /** 审核结果 */
    private AuditResult       result;

    /** 审核时间 */
    private Date              auditTime;

    /** 处理人 */
    private User              user;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"step\":\"" + step + "\", \"" + (role != null ? "role\":\"" + role + "\", \"" : "") + (result != null ? "result\":\"" + result + "\", \"" : "")
               + (user != null ? "user\":\"" + user : "") + "\"}  ";
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
     * Getter method for property <tt>step</tt>.
     * 
     * @return property value of step
     */
    public int getStep() {
        return step;
    }

    /**
     * Setter method for property <tt>step</tt>.
     * 
     * @param step value to be assigned to property step
     */
    public void setStep(int step) {
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
     * Getter method for property <tt>result</tt>.
     * 
     * @return property value of result
     */
    public AuditResult getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     * 
     * @param result value to be assigned to property result
     */
    public void setResult(AuditResult result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>user</tt>.
     * 
     * @return property value of user
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter method for property <tt>user</tt>.
     * 
     * @param user value to be assigned to property user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter method for property <tt>auditTime</tt>.
     * 
     * @return property value of auditTime
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * Setter method for property <tt>auditTime</tt>.
     * 
     * @param auditTime value to be assigned to property auditTime
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     * 
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc</tt>.
     * 
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

}
