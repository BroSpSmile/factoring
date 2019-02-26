/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smile.start.commons.Constants;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;

/**
 * 
 * @author smile.jing
 * @version $Id: LogoutController.java, v 0.1 Feb 22, 2019 3:56:32 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/logout")
public class LogoutController extends BaseController {

    /**
     * 登出
     * @param request
     * @param response
     * @return
     */
    @GetMapping
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            LoggerUtils.info(logger, "没有cookie");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.TOKEN_COOKIE_KEY)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    LoggerUtils.info(logger, "被删除的cookie:{}", cookie.getName());
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        return "login";
    }
}
