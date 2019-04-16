package com.smile.start.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:42, AuthPermissionInfoDTO.java
 * @since 1.8
 */
public class AuthPermissionInfoDTO implements Serializable {
    private static final long serialVersionUID = 6785871097069277271L;

    private Long id;

    private String serialNo;

    private String permissionCode;

    private String permissionName;

    private Integer permissionType;

    private String remark;

    private String parentSerialNo;

    private String url;

    private Integer deleteFlag;

    private Integer sort;

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

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParentSerialNo() {
        return parentSerialNo;
    }

    public void setParentSerialNo(String parentSerialNo) {
        this.parentSerialNo = parentSerialNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        return "AuthPermissionInfoDTO{" +
                "id=" + id +
                ", serialNo='" + serialNo + '\'' +
                ", permissionCode='" + permissionCode + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", permissionType=" + permissionType +
                ", remark='" + remark + '\'' +
                ", parentSerialNo='" + parentSerialNo + '\'' +
                ", url='" + url + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", sort=" + sort +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                '}';
    }
}
