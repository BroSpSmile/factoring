/**
 * 
 */
package com.smile.start.controller;

import com.smile.start.model.base.ListResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.SingleResult;

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

    /**
     * 异常结果转换
     * @param e
     * @param clazz
     * @return
     */
    protected <T> SingleResult<T> toResult(Exception e, Class<T> clazz) {
        SingleResult<T> result = new SingleResult<T>();
        result.setSuccess(false);
        result.setErrorCode("VP00001001");
        result.setErrorMessage(e.getMessage());
        return result;
    }

    protected <T> ListResult<T> toListResult(Exception e, Class<T> clazz) {
        ListResult<T> result = new ListResult<T>();
        result.setSuccess(false);
        result.setErrorCode("VP00001001");
        result.setErrorMessage(e.getMessage());
        return result;
    }
}
