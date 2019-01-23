package com.smile.start.entity;

import javax.persistence.*;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 15:44, AuthRolePermissionInfo.java
 * @since 1.8
 */
@Entity
@Table(name = "auth_role_permission_info", catalog = "orange_factoring")
public class AuthRolePermissionInfoDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_no", nullable = false, length = 64)
    private String serialNo;

    @Column(name = "role_serial_no", nullable = false, length = 64)
    private String roleSerialNo;

    @Column(name = "permission_serial_no", nullable = false, length = 64)
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
