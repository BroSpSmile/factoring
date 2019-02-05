package com.smile.start.model.auth;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/1/28 20:44, RolePermission.java
 * @since 1.8
 */
public class RolePermission implements Serializable {
    private static final long serialVersionUID = 5728621710496136491L;

    private Long id;
    private String serialNo;
    private String roleSerialNo;
    private String permissionSerialNo;

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

    public String getRoleSerialNo() {
        return roleSerialNo;
    }

    public void setRoleSerialNo(String roleSerialNo) {
        this.roleSerialNo = roleSerialNo;
    }

    public String getPermissionSerialNo() {
        return permissionSerialNo;
    }

    public void setPermissionSerialNo(String permissionSerialNo) {
        this.permissionSerialNo = permissionSerialNo;
    }
}
