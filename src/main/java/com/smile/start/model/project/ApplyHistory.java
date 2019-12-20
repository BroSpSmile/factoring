package com.smile.start.model.project;

import java.io.Serializable;
import java.util.Date;

import com.smile.start.model.enums.audit.AuditType;

/**
 * @author Joseph
 * @version v1.0 2019/5/3 10:58, ApplyHistory.java
 * @since 1.8
 */
public class ApplyHistory implements Serializable {
    private static final long serialVersionUID = 4249905687333440567L;

    /**
     * 项目ID
     */
    private Long              id;

    /**
     * 申请类型
     */
    private AuditType         auditType;

    /**
     * 申请时间
     */
    private Date              createTime;

    /**
     * 所属项目
     */
    private Project           project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
