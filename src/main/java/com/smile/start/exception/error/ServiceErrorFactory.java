/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.exception.error;

import com.smile.start.commons.ErrorMessageEnum;
import com.smile.start.exception.VipError;

/**
 * 错误工厂
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: ServiceErrorFactory
 * @date Date : 2019年11月03日 17:51
 */
public class ServiceErrorFactory extends AbstractErrorFactory {

    /**
     * 私有构造函数
     */
    private ServiceErrorFactory() {
        super();
    }

    /**
     * 获取单例对象
     * @return 返回单例
     */
    public static ServiceErrorFactory getInstance() {
        return ServiceErrorFactoryHolder.INSTANCE;
    }

    /**
     *单例持有对象 
     */
    private static final class ServiceErrorFactoryHolder {
        private static final ServiceErrorFactory INSTANCE = new ServiceErrorFactory();
    }

    /**
     * 错误枚举转换
     * @param errorMessage 枚举
     * @return 错误对象
     */
    public VipError createError(ErrorMessageEnum errorMessage) {
        return createError(errorMessage.getCode(), errorMessage.getMessage());
    }

    /**
     * 默认异常
     * @return 异常对象
     */
    public VipError getDefaultError() {
        return createError(ErrorMessageEnum.DEFUALT);
    }
}
