/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.smile.start.model.auth.User;
import com.smile.start.model.enums.audit.AuditResult;

/**
 * 审核历史
 * @author smile.jing
 * @version $Id: AuditRecord.java, v 0.1 Mar 2, 2019 9:08:36 PM smile.jing Exp $
 */
public class AuditRecord implements Serializable {

    /** UID  */
    private static final long     serialVersionUID = -7280913097348533872L;

    /** 编号 */
    private Long                  id;

    /** 审核项目 */
    private Audit                 audit;

    /** 操作类型 */
    private String                type;

    /** 审核人 */
    private User                  auditor;

    /** 审核结果 */
    private AuditResult           result;

    /** 审核时间 */
    private Date                  auditTime;

    /** 审核备注 */
    private String                remark;

    /** 项目状态 */
    private String                status;

    /** 附件 */
    private List<AuditRecordItem> items;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (audit != null ? "audit\":\"" + audit + "\", \"" : "") + (type != null ? "type\":\"" + type + "\", \"" : "")
               + (auditor != null ? "auditor\":\"" + auditor + "\", \"" : "") + (result != null ? "result\":\"" + result + "\", \"" : "")
               + (auditTime != null ? "auditTime\":\"" + auditTime + "\", \"" : "") + (remark != null ? "remark\":\"" + remark + "\", \"" : "")
               + (status != null ? "status\":\"" + status : "") + "\"}  ";
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

    /**
     * Getter method for property <tt>type</tt>.
     * 
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     * 
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
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
     * Getter method for property <tt>remark</tt>.
     * 
     * @return property value of remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Setter method for property <tt>remark</tt>.
     * 
     * @param remark value to be assigned to property remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>items</tt>.
     * 
     * @return property value of items
     */
    public List<AuditRecordItem> getItems() {
        return items;
    }

    /**
     * Setter method for property <tt>items</tt>.
     * 
     * @param items value to be assigned to property items
     */
    public void setItems(List<AuditRecordItem> items) {
        this.items = items;
    }

}