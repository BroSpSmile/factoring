package com.smile.start.model.base;

/**
 * 默认结果，返回单数据
 * 
 * @author smile.jing
 * @version $Id: SingleResult.java, v 0.1 Jan 13, 2019 8:38:52 PM smile.jing Exp
 * 
 */
public class SingleResult<T> extends BaseResult {

	/**  */
	private static final long serialVersionUID = 4101179590769575819L;

	/** id */
	private T data;

	/**
	 * 
	 */
	public SingleResult() {
		super();
	}

	/**
	 * @param data
	 */
	public SingleResult(T data) {
		super();
		this.data = data;
	}

	/**
	 * @param errorCode
	 * @param errorMessage
	 */
	public SingleResult(T data, String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
		this.data = data;
	}

	/**
	 * @param success
	 * @param errorCode
	 * @param errorMessage
	 */
	public SingleResult(T data, boolean success, String errorCode, String errorMessage) {
		super(success, errorCode, errorMessage);
		this.data = data;
	}

	/**
	 * Getter for data
	 * 
	 * @return
	 */
	public T getData() {
		return data;
	}

	/**
	 * Setter for data
	 * 
	 * @param data
	 */
	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		String str = super.toString();
		StringBuilder sb = new StringBuilder();
		sb.append(str).append(",").append(data);
		return sb.toString();
	}
}
