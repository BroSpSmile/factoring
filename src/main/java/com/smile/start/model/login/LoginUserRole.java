package com.smile.start.model.login;

import java.io.Serializable;

/**
 * 登录用户角色信息
 * @author Joseph
 * @version v1.0 2019/2/24 18:38, LoginUserRole.java
 * @since 1.8
 */
public class LoginUserRole implements Serializable {
    private static final long serialVersionUID = -3432048096659014900L;

    /**
     * 角色唯一流水号
     */
    private String serialNo;

    /**
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

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

    @Override
    public String toString() {
        return "LoginUserRole{" +
                "serialNo='" + serialNo + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }
}
