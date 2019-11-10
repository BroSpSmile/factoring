/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.aliyun;

import com.smile.start.exception.ServiceException;
import com.smile.start.model.aliyun.SmsRequest;
import com.smile.start.model.base.BaseResult;

/**
 * 短信客户端
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: SmsClient
 * @date Date : 2019年11月04日 16:10
 */
public interface SmsClient {

    /**
     * 发送短信
     * @param request
     * @return
     * @throws ServiceException
     */
    BaseResult send(SmsRequest request) throws ServiceException;
}
