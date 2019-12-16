package com.smile.start.model.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author Joseph
 * @version v1.0 2019/1/22 19:51, User.java
 * @since 1.8
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3207313861086193530L;

    private Long              id;

    private String            serialNo;

    private String            username;

    private String            mobile;

    /** 企业微信号 */
    private String            wechatNo;

    private String            openid;

    private String            email;

    private Integer           status;

    private Integer           deleteFlag;

    private String            passwd;

    private String            createUser;

    private String            modifyUser;

    private Date              gmtCreate;

    private Date              gmtModify;

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
     * @param id value to be assigned to property  id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>serialNo</tt>.
     *
     * @return property value of serialNo
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * Setter method for property <tt>serialNo</tt>.
     *
     * @param serialNo value to be assigned to property  serialNo
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * Getter method for property <tt>username</tt>.
     *
     * @return property value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for property <tt>username</tt>.
     *
     * @param username value to be assigned to property  username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for property <tt>mobile</tt>.
     *
     * @return property value of mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Setter method for property <tt>mobile</tt>.
     *
     * @param mobile value to be assigned to property  mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Getter method for property <tt>wechatNo</tt>.
     *
     * @return property value of wechatNo
     */
    public String getWechatNo() {
        return wechatNo;
    }

    /**
     * Setter method for property <tt>wechatNo</tt>.
     *
     * @param wechatNo value to be assigned to property  wechatNo
     */
    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    /**
     * Getter method for property <tt>openid</tt>.
     *
     * @return property value of openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * Setter method for property <tt>openid</tt>.
     *
     * @param openid value to be assigned to property  openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * Getter method for property <tt>email</tt>.
     *
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     *
     * @param email value to be assigned to property  email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property  status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>deleteFlag</tt>.
     *
     * @return property value of deleteFlag
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Setter method for property <tt>deleteFlag</tt>.
     *
     * @param deleteFlag value to be assigned to property  deleteFlag
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * Getter method for property <tt>passwd</tt>.
     *
     * @return property value of passwd
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Setter method for property <tt>passwd</tt>.
     *
     * @param passwd value to be assigned to property  passwd
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Getter method for property <tt>createUser</tt>.
     *
     * @return property value of createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * Setter method for property <tt>createUser</tt>.
     *
     * @param createUser value to be assigned to property  createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * Getter method for property <tt>modifyUser</tt>.
     *
     * @return property value of modifyUser
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * Setter method for property <tt>modifyUser</tt>.
     *
     * @param modifyUser value to be assigned to property  modifyUser
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Setter method for property <tt>gmtCreate</tt>.
     *
     * @param gmtCreate value to be assigned to property  gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Getter method for property <tt>gmtModify</tt>.
     *
     * @return property value of gmtModify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * Setter method for property <tt>gmtModify</tt>.
     *
     * @param gmtModify value to be assigned to property  gmtModify
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}
