/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import com.smile.start.model.enums.Step;

import java.io.Serializable;

/**
 * 委托记录
 *
 * @author smile.jing
 * @version $Id: Project.java, v 0.1 Jan 8, 2019 9:44:16 PM smile.jing Exp $
 */
public class EntrustAuth implements Serializable {

    private static final long serialVersionUID = 138001867380229189L;

    /**
     * ID
     */
    private long id = -1;

    private Step type;

    /**
     * 项目编号
     */
    private long projectId;

    /**
     * 用户编号
     */
    private long fromUserId;

    /**
     * 被委托用户编号
     */
    private long toUserId;


    /**
     * 审核id
     */
    private long auditId;

    /**
     * 审核步骤id
     */
    private long recordId;

    /**
     * 备注
     */
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public long getAuditId() {
        return auditId;
    }

    public void setAuditId(long auditId) {
        this.auditId = auditId;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Step getType() {
        return type;
    }

    public void setType(Step type) {
        this.type = type;
    }
}
