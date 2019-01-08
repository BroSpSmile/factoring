/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 性别
 * 
 * @author smile.jing
 * @version $Id: Sex.java, v 0.1 Jan 8, 2019 10:21:51 PM smile.jing Exp $
 */
public enum Sex {

	/**  */
	MALE("MALE", "男"),
	/**  */
	FEMALE("FEMALE", "女");

	Sex(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * getByCode
	 * 
	 * @param code
	 * @return
	 */
	public Sex getByCode(String code) {
		Sex[] values = Sex.values();
		for (Sex value : values) {
			if (StringUtils.equals(code, value.code)) {
				return value;
			}
		}
		return null;
	}

	/** 状态码 */
	private String code;

	/** 状态描述 */
	private String desc;

	/**
	 * Getter method for property <tt>code</tt>.
	 * 
	 * @return property value of code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Getter method for property <tt>desc</tt>.
	 * 
	 * @return property value of desc
	 */
	public String getDesc() {
		return desc;
	}
}
