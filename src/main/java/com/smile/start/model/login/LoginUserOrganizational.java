package com.smile.start.model.login;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/3/20 21:26, LoginUserOrganizational.java
 * @since 1.8
 */
public class LoginUserOrganizational implements Serializable {
    private static final long serialVersionUID = -5900866897789653662L;

    private String serialNo;
    private String organizationalName;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getOrganizationalName() {
        return organizationalName;
    }

    public void setOrganizationalName(String organizationalName) {
        this.organizationalName = organizationalName;
    }
}
