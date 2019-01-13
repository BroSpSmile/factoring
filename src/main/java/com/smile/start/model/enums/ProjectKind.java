/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author smile.jing
 * @version $Id: ProjectKind.java, v 0.1 Jan 8, 2019 10:01:33 PM smile.jing Exp
 *          $
 */
public enum ProjectKind {
	/**  */
	FACTORING("FACTORING", "保理", "FCT"),
	/**  */
	FUND("FUND", "母基金", "FND"),
	/**  */
	INVESTMENT("INVESTMENT", "直投", "INV");

	ProjectKind(String code, String desc, String scode) {
		this.code = code;
		this.desc = desc;
		this.scode = scode;
	}

	/**
	 * getByCode
	 * 
	 * @param code
	 * @return
	 */
	public static ProjectKind getByCode(String code) {
		ProjectKind[] values = ProjectKind.values();
		for (ProjectKind value : values) {
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

	/** 简码 */
	private String scode;

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

	/**
	 * Getter method for property <tt>scode</tt>.
	 * 
	 * @return property value of scode
	 */
	public String getScode() {
		return scode;
	}

}
