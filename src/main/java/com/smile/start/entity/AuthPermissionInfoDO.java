package com.smile.start.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, AuthPermissionInfoDO.java
 * @since 1.8
 */
@Entity
@Table(name = "auth_permission_info", catalog = "orange_factoring")
public class AuthPermissionInfoDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_no", nullable = false, length = 64)
    private String serialNo;

    @Column(name = "permission_code", nullable = false, length = 64)
    private String permissionCode;

    @Column(name = "permission_name", nullable = false, length = 128)
    private String permissionName;

    @Column(name = "permission_type", nullable = false)
    private Integer permissionType;

    @Column(name = "remark", nullable = true, length = 512)
    private String remark;

    @Column(name = "parent_serial_no", nullable = true, length = 64)
    private String parentSerialNo;

    @Column(name = "url", nullable = true, length = 128)
    private String url;

    @Column(name = "delete_flag", nullable = true)
    private Integer deleteFlag;

    @Column(name = "create_user", nullable = true, length = 64)
    private String createUser;

    @Column(name = "modify_user", nullable = true, length = 64)
    private String modifyUser;

    @Column(name = "gmt_create", nullable = true)
    private Date gmtCreate;

    @Column(name = "gmt_modify", nullable = true)
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
}
