package com.smile.start.model.auth;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/1/27 19:49, UserRole.java
 * @since 1.8
 */
public class UserRole implements Serializable {
    private static final long serialVersionUID = 2533363766116047221L;

    private Long id;
    private String serialNo;
    private String userSerialNo;
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
