package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/1/29 16:58, OrganizationalSearchDTO.java
 * @since 1.8
 */
public class OrganizationalSearchDTO implements Serializable {
    private static final long serialVersionUID = 9222490880219704733L;

    private String organizationalName;

    public String getOrganizationalName() {
        return organizationalName;
    }

    public void setOrganizationalName(String organizationalName) {
        this.organizationalName = organizationalName;
    }

    @Override
    public String toString() {
        return "OrganizationalSearchDTO{" +
                "organizationalName='" + organizationalName + '\'' +
                '}';
    }
}
