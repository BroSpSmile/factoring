package com.smile.start.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, AuthUserInfoDO.java
 * @since 1.8
 */
@Entity
@Table(name = "auth_user_info", catalog = "orange_factoring")
public class AuthUserInfoDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_no", nullable = false, length = 64)
    private String serialNo;

    @Column(name = "mobile", nullable = true, length = 32)
    private String mobile;

    @Column(name = "openid", nullable = true, length = 128)
    private String openid;

    @Column(name = "email", nullable = true, length = 128)
    private String email;

    @Column(name = "status", nullable = true)
    private Integer status;

    @Column(name = "delete_flag", nullable = true)
    private Integer deleteFlag;

    @Column(name = "passwd", nullable = true, length = 64)
    private String passwd;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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