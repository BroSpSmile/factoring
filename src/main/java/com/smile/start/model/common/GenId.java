/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.common;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author smile.jing
 * @version $Id: GenId.java, v 0.1 Jan 13, 2019 6:25:06 PM smile.jing Exp $
 */
public class GenId implements Serializable {

	/** UID */
	private static final long serialVersionUID = -3013632967578924801L;

	/** 编号 */
	private long id;

	/** 类型 */
	private String kind;

	/** 序号 */
	private long genIdNo;

	/** 更新时间 */
	private Date updateTime;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", \"kind\":\"" + kind + "\", \"genIdNo\":\"" + genIdNo + "\", \"updateTime\":\""
				+ updateTime + "\"}  ";
	}

	/**
	 * Getter method for property <tt>id</tt>.
	 * 
	 * @return property value of id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter method for property <tt>id</tt>.
	 * 
	 * @param id value to be assigned to property id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter method for property <tt>kind</tt>.
	 * 
	 * @return property value of kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Setter method for property <tt>kind</tt>.
	 * 
	 * @param kind value to be assigned to property kind
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * Getter method for property <tt>genIdNo</tt>.
	 * 
	 * @return property value of genIdNo
	 */
	public long getGenIdNo() {
		return genIdNo;
	}

	/**
	 * Setter method for property <tt>genIdNo</tt>.
	 * 
	 * @param genIdNo value to be assigned to property genIdNo
	 */
	public void setGenIdNo(long genIdNo) {
		this.genIdNo = genIdNo;
	}

	/**
	 * Getter method for property <tt>updateTime</tt>.
	 * 
	 * @return property value of updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * Setter method for property <tt>updateTime</tt>.
	 * 
	 * @param updateTime value to be assigned to property updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
