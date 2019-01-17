/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.organization;

import java.io.Serializable;

/**
 * 雇员信息
 * 
 * @author smile.jing
 * @version $Id: Employee.java, v 0.1 Jan 8, 2019 10:20:29 PM smile.jing Exp $
 */
public class Employee implements Serializable {

    /** UID */
    private static final long serialVersionUID = 7285237871533718166L;

    /** 个人信息 */
    private Person            person;

    /** 登录用户名 */
    private String            userName;

    /** 登录密码 */
    private String            password;

    /** 小程序OpenId */
    private String            openId;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"person\":\"" + person + "\", \"userName\":\"" + userName + "\", \"password\":\"" + password + "\", \"openId\":\"" + openId + "\"}  ";
    }

    /**
     * Getter method for property <tt>person</tt>.
     * 
     * @return property value of person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Setter method for property <tt>person</tt>.
     * 
     * @param person value to be assigned to property person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>password</tt>.
     * 
     * @return property value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for property <tt>password</tt>.
     * 
     * @param password value to be assigned to property password
     */
    public void setPassword(String password) {
        this.password = password;
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
