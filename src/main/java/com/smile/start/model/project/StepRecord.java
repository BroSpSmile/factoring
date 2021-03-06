/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.Date;

import com.smile.start.model.enums.Step;
import com.smile.start.model.enums.StepStatus;

/**
 * 项目步骤记录
 * @author smile.jing
 * @version $Id: ProjectRecord.java, v 0.1 Mar 3, 2019 10:37:46 PM smile.jing Exp $
 */
public class StepRecord implements Serializable {

    /** UID */
    private static final long serialVersionUID = -4454241868223583328L;

    /** 编号 */
    private Long              id;

    /** 所属记录 */
    private Project           project;

    /** 当前流程 */
    private Step              step;

    /** 流程状态 */
    private StepStatus        status;

    /** 审核记录 */
    private Audit             audit;

    /** 创建时间 */
    private Date              createTime;

    /** 修改时间 */
    private Date              modifyTime;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (id != null ? "id\":\"" + id + "\", \"" : "") + (project != null ? "project\":\"" + project + "\", \"" : "")
               + (step != null ? "step\":\"" + step + "\", \"" : "") + (status != null ? "status\":\"" + status + "\", \"" : "")
               + (createTime != null ? "createTime\":\"" + createTime + "\", \"" : "") + (modifyTime != null ? "modifyTime\":\"" + modifyTime : "") + "\"}  ";
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
    public Step getStep() {
        return step;
    }

    /**
     * Setter method for property <tt>step</tt>.
     * 
     * @param step value to be assigned to property step
     */
    public void setStep(Step step) {
        this.step = step;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public StepStatus getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(StepStatus status) {
        this.status = status;
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
     * Getter method for property <tt>modifyTime</tt>.
     * 
     * @return property value of modifyTime
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * Setter method for property <tt>modifyTime</tt>.
     * 
     * @param modifyTime value to be assigned to property modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * Getter method for property <tt>audit</tt>.
     * 
     * @return property value of audit
     */
    public Audit getAudit() {
        return audit;
    }

    /**
     * Setter method for property <tt>audit</tt>.
     * 
     * @param audit value to be assigned to property audit
     */
    public void setAudit(Audit audit) {
        this.audit = audit;
    }

}
