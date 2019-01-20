package com.smile.start.commons;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 15:15, ResponseResult.java
 * @since 1.8
 */
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = -3872130951913767412L;

    /**
     * 请求结果，true:成功，false:失败
     */
    private Boolean success;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
