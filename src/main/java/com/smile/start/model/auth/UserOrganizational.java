package com.smile.start.model.auth;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/1/30 19:18, UserOrganizational.java
 * @since 1.8
 */
public class UserOrganizational implements Serializable {
    private static final long serialVersionUID = 8105858653143196228L;

    private Long id;
    private String serialNo;
    private String userSerialNo;
    private String organizationalSerialNo;

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

    public String getOrganizationalSerialNo() {
        return organizationalSerialNo;
    }

    public void setOrganizationalSerialNo(String organizationalSerialNo) {
        this.organizationalSerialNo = organizationalSerialNo;
    }
}
