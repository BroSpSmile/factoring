/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.combobox;

/**
 * 
 * @author smile.jing
 * @version $Id: Item.java, v 0.1 Jan 8, 2019 9:19:43 PM smile.jing Exp $
 */
public class Item {
	/** 值 */
	private String value;

	/** 文本 */
	private String text;

	/**
	 * 无参构造函数
	 */
	public Item() {
		super();
	}

	/**
	 * 带参构造函数
	 * 
	 * @param value
	 * @param text
	 */
	public Item(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}

	/**
	 * Getter method for property <tt>value</tt>.
	 * 
	 * @return property value of value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter method for property <tt>value</tt>.
	 * 
	 * @param value value to be assigned to property value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Getter method for property <tt>text</tt>.
	 * 
	 * @return property value of text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter method for property <tt>text</tt>.
	 * 
	 * @param text value to be assigned to property text
	 */
	public void setText(String text) {
		this.text = text;
	}

}
