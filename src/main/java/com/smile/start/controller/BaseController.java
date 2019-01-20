/**
 * 
 */
package com.smile.start.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smile.start.model.base.BaseResult;

/**
 * @author jingyongxiang
 *
 */
public class BaseController {

    /** logger */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 异常结果转换
     * @param e
     * @return
     */
    protected BaseResult toResult(Exception e) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        result.setErrorCode("VP00001001");
        result.setErrorMessage(e.getMessage());
        return result;
    }
}
