package com.smile.start.model.base;

import java.util.Collection;

/**
 * 多数据集结果对象
 * 
 * @author smile.jing
 * @version $Id: ListResult.java, v 0.1 Jan 13, 2019 8:36:58 PM smile.jing Exp $
 */
public class ListResult<T> extends BaseResult {

	/**  */
	private static final long serialVersionUID = -1663737152129232903L;

	/** 多数据集结果 */
	private Collection<T> values;

	/**
	 * 无参构造函数
	 */
	public ListResult() {
		super();
	}

	/**
	 * 无参构造函数
	 */
	public ListResult(Collection<T> values) {
		super();
		this.values = values;
	}

	/**
	 * @param errorCode
	 * @param errorMessage
	 */
	public ListResult(Collection<T> values, String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
		this.values = values;
	}

	/**
	 * @param success
	 * @param errorCode
	 * @param errorMessage
	 */
	public ListResult(Collection<T> values, boolean success, String errorCode, String errorMessage) {
		super(success, errorCode, errorMessage);
		this.values = values;
	}

	/**
	 * Getter for data
	 * 
	 * @return
	 */
	public Collection<T> getValues() {
		return values;
	}

	/**
	 * Setter for data
	 * 
	 * @param values
	 */
	public void setValues(Collection<T> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		String str = super.toString();
		StringBuilder sb = new StringBuilder();
		sb.append(str).append(",");
		sb.append(values);
		return sb.toString();
	}

}
