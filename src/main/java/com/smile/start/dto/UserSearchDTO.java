package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/1/20 14:39, UserSearchDTO.java
 * @since 1.8
 */
public class UserSearchDTO implements Serializable {
    private static final long serialVersionUID = -2314194234025826739L;

    private String username;
    private String mobile;
    private Integer status;
    private String organizational;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrganizational() {
        return organizational;
    }

    public void setOrganizational(String organizational) {
        this.organizational = organizational;
    }

    @Override
    public String toString() {
        return "UserSearchDTO{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                ", organizational='" + organizational + '\'' +
                '}';
    }
}
