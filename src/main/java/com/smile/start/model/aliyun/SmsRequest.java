/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.aliyun;

import java.util.Map;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: SmsRequest
 * @date Date : 2019年11月04日 16:11
 */
public class SmsRequest {

    /** 电话 */
    private String              mobile;

    /** 签名 */
    private String              sign;

    /** 短信模板 */
    private String              templateId;

    /** 交易id */
    private String              outId;

    /** 短信内容映射 */
    private Map<String, String> params;

    @Override
    public String toString() {
        return "{\"SmsRequest\":{" + "\"mobile\":\"" + mobile + '\"' + ",\"sign\":\"" + sign + '\"' + ",\"templateId\":\"" + templateId + '\"' + ",\"outId\":\"" + outId + '\"'
               + ",\"params\":" + params + "}}";

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
     * @param mobile value to be assigned to property  mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Getter method for property <tt>sign</tt>.
     *
     * @return property value of sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * Setter method for property <tt>sign</tt>.
     *
     * @param sign value to be assigned to property  sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * Getter method for property <tt>templateId</tt>.
     *
     * @return property value of templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Setter method for property <tt>templateId</tt>.
     *
     * @param templateId value to be assigned to property  templateId
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * Getter method for property <tt>outId</tt>.
     *
     * @return property value of outId
     */
    public String getOutId() {
        return outId;
    }

    /**
     * Setter method for property <tt>outId</tt>.
     *
     * @param outId value to be assigned to property  outId
     */
    public void setOutId(String outId) {
        this.outId = outId;
    }

    /**
     * Getter method for property <tt>params</tt>.
     *
     * @return property value of params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * Setter method for property <tt>params</tt>.
     *
     * @param params value to be assigned to property  params
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
