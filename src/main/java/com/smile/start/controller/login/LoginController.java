/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.controller.BaseController;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.LoginRequestDTO;
import com.smile.start.model.base.SingleResult;
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

    /**
     * 登录页
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    /**
     * 登录
     * @param loginRequestDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<AuthUserInfoDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        SingleResult<AuthUserInfoDTO> result = new SingleResult<>();
        AuthUserInfoDTO authUserInfoDTO = loginService.login(loginRequestDTO, response);
        result.setData(authUserInfoDTO);
        return result;
    }
}
