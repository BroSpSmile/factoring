package com.smile.start.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, AuthRoleInfoDO.java
 * @since 1.8
 */
@Entity
@Table(name = "auth_role_info", catalog = "orange_factoring")
public class AuthRoleInfoDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_no", nullable = false, length = 64)
    private String serialNo;

    @Column(name = "role_code", nullable = false, length = 64)
    private String roleCode;

    @Column(name = "role_name", nullable = false, length = 64)
    private String roleName;

    @Column(name = "role_desc", nullable = true, length = 512)
    private String roleDesc;

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
}
