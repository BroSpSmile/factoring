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

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public AuthUserInfoDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        String md5Password = DigestUtils.md5Hex(loginRequestDTO.getPasswd());
        loginRequestDTO.setPasswd(md5Password);
        logger.info("service login start...");
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
        logger.info("insert token start...");
        tokenDao.insert(token);
        logger.info("insert token end...");
        response.addCookie(new Cookie(Constants.TOKEN_COOKIE_KEY, tokenStr));
        if (StringUtils.isNotBlank(loginRequestDTO.getOpenId())) {
            login.setOpenid(loginRequestDTO.getOpenId());
            userDao.update(login);
        }
        login.setOpenid(loginRequestDTO.getOpenId());
        AuthUserInfoDTO authUserInfoDTO = userInfoService.get(login.getId());
        logger.info("service login end...");
        return authUserInfoDTO;
    }
}
