package com.smile.start.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/1/29 16:53, OrganizationalDTO.java
 * @since 1.8
 */
public class OrganizationalDTO implements Serializable {
    private static final long serialVersionUID = 6658553336569584570L;

    private Long id;
    private String serialNo;
    private String organizationalName;
    private String parentSerialNo;
    private Integer deleteFlag;
    private String remark;
    private String createUser;
    private String modifyUser;
    private Date gmtCreate;
    private Date gmtModify;

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

    public String getOrganizationalName() {
        return organizationalName;
    }

    public void setOrganizationalName(String organizationalName) {
        this.organizationalName = organizationalName;
    }

    public String getParentSerialNo() {
        return parentSerialNo;
    }

    public void setParentSerialNo(String parentSerialNo) {
        this.parentSerialNo = parentSerialNo;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Override
    public String toString() {
        return "OrganizationalDTO{" +
                "id=" + id +
                ", serialNo='" + serialNo + '\'' +
                ", organizationalName='" + organizationalName + '\'' +
                ", parentSerialNo='" + parentSerialNo + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                '}';
    }
}
