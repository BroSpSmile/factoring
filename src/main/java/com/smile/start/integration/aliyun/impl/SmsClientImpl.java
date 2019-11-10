/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.aliyun.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.smile.start.commons.ErrorMessageEnum;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.exception.ServiceException;
import com.smile.start.exception.error.ServiceErrorFactory;
import com.smile.start.integration.aliyun.SmsClient;
import com.smile.start.model.aliyun.SmsRequest;
import com.smile.start.model.aliyun.SmsResult;
import com.smile.start.model.base.BaseResult;

/**
 * 实现
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: SmsClientImpl
 * @date Date : 2019年11月04日 16:17
 */
@Service
public class SmsClientImpl implements SmsClient, InitializingBean {

    /** accessKey */
    @Value("${aliyun.oss.accessKeyId}")
    private String     accessKey;

    /** accessSecret */
    @Value("${aliyun.oss.accessKeySecret}")
    private String     accessSecret;

    /** 短信客户端 */
    private IAcsClient client;

    /** logger */
    public Logger      logger = LoggerFactory.getLogger(getClass());

    /**
     * @see InitializingBean
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
        client = new DefaultAcsClient(profile);
        if (null == client) {
            throw new ServiceException(ServiceErrorFactory.getInstance().createError(ErrorMessageEnum.DEFUALT));
        }
    }

    /**
     * 发送短信
     *
     * @param request
     * @return
     */
    @Override
    public BaseResult send(SmsRequest request) throws ServiceException {
        CommonRequest commonRequest = toSmsRequest(request);
        try {
            CommonResponse response = client.getCommonResponse(commonRequest);
            if (null != response && StringUtils.isNotBlank(response.getData())) {
                SmsResult result = FastJsonUtils.fromJSONString(response.getData(), SmsResult.class);
                BaseResult br = new BaseResult();
                br.setSuccess(StringUtils.endsWithIgnoreCase(result.getCode(), "OK"));
                br.setErrorCode(result.getCode());
                br.setErrorMessage(result.getMessage());
                return br;
            } else {
                LoggerUtils.error(logger, "发送短信发生异常,请求参数:{}", request.toString());
                throw new ServiceException(ServiceErrorFactory.getInstance().createError(ErrorMessageEnum.SMS_ERROR));
            }
        } catch (ClientException e) {
            LoggerUtils.error(logger, "发送短信发生异常,请求参数:{}", e.getMessage(), request.toString());
            throw new ServiceException(ServiceErrorFactory.getInstance().createError(ErrorMessageEnum.SMS_ERROR), e);
        }
    }

    /**
     * 转换请求入参
     * @param request
     * @return
     */
    private CommonRequest toSmsRequest(SmsRequest request) {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setDomain("dysmsapi.aliyuncs.com");
        commonRequest.setVersion("2017-05-25");
        commonRequest.setAction("SendSms");
        commonRequest.putQueryParameter("RegionId", "cn-hangzhou");
        commonRequest.putQueryParameter("PhoneNumbers", request.getMobile());
        commonRequest.putQueryParameter("SignName", request.getSign());
        commonRequest.putQueryParameter("TemplateCode", request.getTemplateId());
        commonRequest.putQueryParameter("TemplateParam", FastJsonUtils.toJSONString(request.getParams()));
        commonRequest.putQueryParameter("OutId", request.getOutId());
        return commonRequest;
    }

}
