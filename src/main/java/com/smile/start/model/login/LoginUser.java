package com.smile.start.model.login;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 * @author Joseph
 * @version v1.0 2019/2/24 18:36, LoginUser.java
 * @since 1.8
 */
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 2362548302075055343L;

    /**
     * 用户唯一流水号
     */
    private String serialNo;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户拥有的角色列表
     */
    private List<LoginUserRole> roleList;

    /**
     * 用户拥有的权限列表
     */
    private List<LoginUserPermission> permissionList;

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

    public List<LoginUserRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<LoginUserRole> roleList) {
        this.roleList = roleList;
    }

    public List<LoginUserPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<LoginUserPermission> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "serialNo='" + serialNo + '\'' +
                ", username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", openid='" + openid + '\'' +
                ", email='" + email + '\'' +
                ", roleList=" + roleList +
                ", permissionList=" + permissionList +
                '}';
    }
}
