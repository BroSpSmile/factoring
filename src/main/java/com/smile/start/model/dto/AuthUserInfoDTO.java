package com.smile.start.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, AuthUserInfoDTO.java
 * @since 1.8
 */
public class AuthUserInfoDTO implements Serializable {
    private static final long           serialVersionUID = -3732140085953244122L;

    private Long                        id;

    private String                      serialNo;

    private String                      username;

    private String                      mobile;

    private String                      wechatNo;

    private String                      openid;

    private String                      email;

    private Integer                     status;

    private Integer                     deleteFlag;

    private String                      passwd;

    private String                      createUser;

    private String                      modifyUser;

    private Date                        gmtCreate;

    private Date                        gmtModify;

    private List<AuthRoleInfoDTO>       roleList;

    private List<AuthPermissionInfoDTO> permissionList;

    private List<String>                checkedRoleList;

    private List<String>                checkedOrganizationalList;

    private String                      firstOrganizationName;

    public String getFirstOrganizationName() {
        return firstOrganizationName;
    }

    public void setFirstOrganizationName(String firstOrganizationName) {
        this.firstOrganizationName = firstOrganizationName;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<AuthRoleInfoDTO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AuthRoleInfoDTO> roleList) {
        this.roleList = roleList;
    }

    public List<AuthPermissionInfoDTO> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<AuthPermissionInfoDTO> permissionList) {
        this.permissionList = permissionList;
    }

    public List<String> getCheckedRoleList() {
        return checkedRoleList;
    }

    public void setCheckedRoleList(List<String> checkedRoleList) {
        this.checkedRoleList = checkedRoleList;
    }

    public List<String> getCheckedOrganizationalList() {
        return checkedOrganizationalList;
    }

    public void setCheckedOrganizationalList(List<String> checkedOrganizationalList) {
        this.checkedOrganizationalList = checkedOrganizationalList;
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

    @Override
    public String toString() {
        return "AuthUserInfoDTO{" + "id=" + id + ", serialNo='" + serialNo + '\'' + ", username='" + username + '\'' + ", mobile='" + mobile + '\'' + ", openid='" + openid + '\''
               + ", email='" + email + '\'' + ", status=" + status + ", deleteFlag=" + deleteFlag + ", passwd='" + passwd + '\'' + ", createUser='" + createUser + '\''
               + ", modifyUser='" + modifyUser + '\'' + ", gmtCreate=" + gmtCreate + ", gmtModify=" + gmtModify + ", roleList=" + roleList + ", permissionList=" + permissionList
               + ", checkedRoleList=" + checkedRoleList + ", checkedOrganizationalList=" + checkedOrganizationalList + '}';
    }
}
