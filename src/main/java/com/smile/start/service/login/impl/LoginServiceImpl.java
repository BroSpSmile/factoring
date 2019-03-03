/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.login.impl;

import com.smile.start.commons.Asserts;
import com.smile.start.commons.Constants;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.TokenDao;
import com.smile.start.dao.UserDao;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.LoginRequestDTO;
import com.smile.start.model.auth.Token;
import com.smile.start.model.auth.User;

import org.springframework.stereotype.Service;

import com.smile.start.service.AbstractService;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.login.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * 实现
 * @author smile.jing
 * @version $Id: LoginServiceImpl.java, v 0.1 Jan 17, 2019 5:53:07 PM smile.jing Exp $
 */
@Service
public class LoginServiceImpl extends AbstractService implements LoginService {

    @Resource
    private UserDao         userDao;

    @Resource
    private TokenDao        tokenDao;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 登录
     * @param loginRequestDTO
     * @param response
     * @return
     */
    @Override
    public AuthUserInfoDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        //TODO 密码暂时用明文，等用户新增功能做好
        //String md5Passwd = MD5Encoder.encode(loginRequestDTO.getPasswd().getBytes());
        //loginRequestDTO.setPasswd(md5Passwd);
        final User login = userDao.login(loginRequestDTO);
        Asserts.notNull(login, "用户名或密码错误");
        String tokenStr = UUID.randomUUID().toString();
        Token token = new Token();
        Date nowDate = new Date();
        token.setToken(tokenStr);
        token.setSerialNo(SerialNoGenerator.generateSerialNo("T", 7));
        token.setMobile(loginRequestDTO.getMobile());
        token.setGmtCreate(nowDate);
        token.setTokenExpire(DateUtil.addMinutes(nowDate, 600));
        tokenDao.insert(token);
        response.addCookie(new Cookie(Constants.TOKEN_COOKIE_KEY, tokenStr));
        return userInfoService.get(login.getId());
    }

}
