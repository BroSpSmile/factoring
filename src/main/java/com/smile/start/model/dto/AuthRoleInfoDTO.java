package com.smile.start.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:40, AuthRoleInfoDTO.java
 * @since 1.8
 */
public class AuthRoleInfoDTO implements Serializable {
    private static final long serialVersionUID = 6892055478015140958L;

    private Long id;

    private String serialNo;

    private String roleCode;

    private String roleName;

    private String roleDesc;

    private Integer deleteFlag;

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

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
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

    @Override
    public String toString() {
        return "AuthRoleInfoDTO{" +
                "id=" + id +
                ", serialNo='" + serialNo + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                '}';
    }
}
