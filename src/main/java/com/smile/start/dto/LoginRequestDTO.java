package com.smile.start.dto;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/1/24 19:38, LoginRequestDTO.java
 * @since 1.8
 */
public class LoginRequestDTO implements Serializable {

    /** UID */
    private static final long serialVersionUID = 7933582709364817902L;

    /** 手机号 */
    private String            mobile;

    /** 密码 */
    private String            passwd;

    /** 微信OpenId */
    private String            openId;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (mobile != null ? "mobile\":\"" + mobile + "\", \"" : "") + (passwd != null ? "passwd\":\"" + passwd + "\", \"" : "")
               + (openId != null ? "openId\":\"" + openId : "") + "\"}  ";
    }

    /**
     * Getter method for property <tt>mobile</tt>.
     * 
     * @return property value of mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Setter method for property <tt>mobile</tt>.
     * 
     * @param mobile value to be assigned to property mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Getter method for property <tt>passwd</tt>.
     * 
     * @return property value of passwd
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Setter method for property <tt>passwd</tt>.
     * 
     * @param passwd value to be assigned to property passwd
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Getter method for property <tt>openId</tt>.
     * 
     * @return property value of openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * Setter method for property <tt>openId</tt>.
     * 
     * @param openId value to be assigned to property openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
