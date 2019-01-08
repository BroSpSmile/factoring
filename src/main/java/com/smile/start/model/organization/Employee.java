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
	private Person person;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"person\":\"" + person + "\"}  ";
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

}
