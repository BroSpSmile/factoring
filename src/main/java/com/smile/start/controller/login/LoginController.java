/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smile.start.service.auth.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.LoginHandler;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.LoginRequestDTO;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.login.LoginUser;
import com.smile.start.service.login.LoginService;

/**
 * 登录controller
 * @author smile.jing
 * @version $Id: LoginController.java, v 0.1 Jan 17, 2019 4:39:02 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    /** 登录服务 */
    @Resource
    private LoginService loginService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 登录页
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    /**
     * 获取登录用户信息
     * @param request
     * @return
     */
    @GetMapping("/user")
    @ResponseBody
    public LoginUser getUser(HttpServletRequest request) {
        return userInfoService.getLoginUserByToken(getToken(request));
    }

    /**
     * 获取微信登录用户
     * @param request
     * @return
     */
    @GetMapping("/user/wx")
    @ResponseBody
    public User getUserByWx(HttpServletRequest request) {
        return getUserByToken(request);
    }

    /**
     * 登录
     * @param loginRequestDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<AuthUserInfoDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        String openId = request.getParameter("openId");
        if (StringUtils.isNotBlank(openId)) {
            loginRequestDTO.setOpenId(openId);
        }
        SingleResult<AuthUserInfoDTO> result = new SingleResult<>();
        long start = System.currentTimeMillis();
        logger.info("controller login start : {}", start);
        AuthUserInfoDTO authUserInfoDTO = loginService.login(loginRequestDTO, response);
        long end = System.currentTimeMillis();
        logger.info("controller login end : {}, cost : {}", end, (end - start));
        result.setData(authUserInfoDTO);
        return result;
    }
}
