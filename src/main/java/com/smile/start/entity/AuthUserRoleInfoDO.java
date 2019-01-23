package com.smile.start.entity;

import javax.persistence.*;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 15:41, AuthUserRoleInfo.java
 * @since 1.8
 */
@Entity
@Table(name = "auth_user_role_info", catalog = "orange_factoring")
public class AuthUserRoleInfoDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_no", nullable = false, length = 64)
    private String serialNo;

    @Column(name = "user_serial_no", nullable = false, length = 64)
    private String userSerialNo;

    @Column(name = "role_serial_no", nullable = false, length = 64)
    private String roleSerialNo;

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

    public String getUserSerialNo() {
        return userSerialNo;
    }

    public void setUserSerialNo(String userSerialNo) {
        this.userSerialNo = userSerialNo;
    }

    public String getRoleSerialNo() {
        return roleSerialNo;
    }

    public void setRoleSerialNo(String roleSerialNo) {
        this.roleSerialNo = roleSerialNo;
    }
}
