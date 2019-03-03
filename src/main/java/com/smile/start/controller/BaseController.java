/**
 * 
 */
package com.smile.start.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * @param fileName
     * @param file
     * @param response
     * @throws UnsupportedEncodingException 
     * @throws FileNotFoundException 
     */
    public void download(String fileName, File file, HttpServletResponse response) throws UnsupportedEncodingException, FileNotFoundException {
        InputStream is = new FileInputStream(file);
        download(fileName, is, response);
    }

    /**
     * @param fileName
     * @param is
     * @param response
     * @throws UnsupportedEncodingException 
     */
    public void download(String fileName, InputStream is, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Type", "application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(is);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
