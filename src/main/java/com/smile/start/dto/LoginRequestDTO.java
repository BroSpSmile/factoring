package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/1/24 19:38, LoginRequestDTO.java
 * @since 1.8
 */
public class LoginRequestDTO implements Serializable {
    private static final long serialVersionUID = 7933582709364817902L;

    private String mobile;
    private String passwd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
            "mobile='" + mobile + '\'' +
            '}';
    }
}
