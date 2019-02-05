/**
 * 
 */
package com.smile.start.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smile.start.commons.Constants;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.SingleResult;
import com.smile.start.service.UserInfoService;

/**
 * @author jingyongxiang
 *
 */
public class BaseController {

    /** logger */
    protected Logger          logger = LoggerFactory.getLogger(getClass());

    /** 用户服务 */
    @Resource
    protected UserInfoService userInfoService;

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

    /**
     * 异常结果转换
     * @param e
     * @param clazz
     * @return
     */
    protected <T> ListResult<T> toListResult(Exception e, Class<T> clazz) {
        ListResult<T> result = new ListResult<T>();
        result.setSuccess(false);
        result.setErrorCode("VP00001001");
        result.setErrorMessage(e.getMessage());
        return result;
    }

    /**
     * 根据token获取用户
     * @param request
     * @return
     */
    protected User getUserByToken(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNoneBlank(token)) {
            User user = userInfoService.getUserByToken(token);
            return user;
        }
        return null;
    }

    /**
     * 获取token
     * @param request
     * @return
     */
    protected String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.TOKEN_COOKIE_KEY)) {
                    return cookie.getValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }

}
