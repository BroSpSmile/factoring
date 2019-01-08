/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.organization;

import java.io.Serializable;
import java.util.Date;

import com.smile.start.model.enums.Sex;

/**
 * 人员信息
 * 
 * @author smile.jing
 * @version $Id: Person.java, v 0.1 Jan 8, 2019 10:20:58 PM smile.jing Exp $
 */
public class Person implements Serializable {

	/** UID */
	private static final long serialVersionUID = -4632886788356279142L;

	/** 姓名 */
	private String name;

	/** 年龄 */
	private int age;

	/** 生日 */
	private Date birthday;

	/** 性别 */
	private Sex sex;

	/** 证件号 */
	private String certificate;

	/** 联系电话 */
	private String mobile;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\", \"age\":\"" + age + "\", \"birthday\":\"" + birthday + "\", \"sex\":\""
				+ sex + "\", \"certificate\":\"" + certificate + "\", \"mobile\":\"" + mobile + "\"}  ";
	}

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for property <tt>age</tt>.
	 * 
	 * @return property value of age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Setter method for property <tt>age</tt>.
	 * 
	 * @param age value to be assigned to property age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Getter method for property <tt>birthday</tt>.
	 * 
	 * @return property value of birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Setter method for property <tt>birthday</tt>.
	 * 
	 * @param birthday value to be assigned to property birthday
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Getter method for property <tt>sex</tt>.
	 * 
	 * @return property value of sex
	 */
	public Sex getSex() {
		return sex;
	}

	/**
	 * Setter method for property <tt>sex</tt>.
	 * 
	 * @param sex value to be assigned to property sex
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}

	/**
	 * Getter method for property <tt>certificate</tt>.
	 * 
	 * @return property value of certificate
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * Setter method for property <tt>certificate</tt>.
	 * 
	 * @param certificate value to be assigned to property certificate
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
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

}
