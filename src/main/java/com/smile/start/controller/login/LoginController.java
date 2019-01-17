/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.login;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.organization.Employee;
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
     * @param employee
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public BaseResult login(@RequestBody Employee employee) {
        BaseResult result = new BaseResult();
        boolean success = loginService.login(employee);
        result.setSuccess(success);
        if (!success) {
            result.setErrorMessage("登录认证失败!");
        }
        return result;
    }
}
