package com.smile.start.model.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/1/20 14:39, PermissionSearchDTO.java
 * @since 1.8
 */
public class PermissionSearchDTO implements Serializable {
    private static final long serialVersionUID = 5238576682097304809L;

    private String permissionCode;
    private String permissionName;
    private Integer permissionType;

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

    @Override
    public String toString() {
        return "PermissionSearchDTO{" +
                "permissionCode='" + permissionCode + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", permissionType='" + permissionType + '\'' +
                '}';
    }
}
