/**
 * 
 */
package com.smile.start.web.controller;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.smile.start.commons.Constants;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.SingleResult;
import com.smile.start.service.auth.UserInfoService;

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
        String openId = request.getParameter("openId");
        if (StringUtils.isNoneBlank(openId)) {
            return userInfoService.getUserByOpenId(openId);
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
     * URLDecoder
     * @param encode
     * @return
     */
    protected String decode(String encode) {
        String decode = StringUtils.EMPTY;
        if (StringUtils.isBlank(encode)) {
            return StringUtils.EMPTY;
        }
        try {
            decode = URLDecoder.decode(encode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LoggerUtils.error(logger, "参数解码失败,code ={}", e, encode);
        }
        return decode;
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
     * 导出报表
     *
     * @param book
     * @return
     * @throws IOException
     */
    protected ResponseEntity<byte[]> export(String attachmentName, Workbook book) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        book.write(os);
        os.close();
        InputStream input = new ByteArrayInputStream(os.toByteArray(), 0, os.toByteArray().length);
        byte[] response = IOUtils.toByteArray(input);
        input.close();
        //定义媒体流头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(attachmentName, "UTF-8") + ".xls");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(response, headers, HttpStatus.CREATED);
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
