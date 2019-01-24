/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.login;

import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.LoginRequestDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录服务
 * @author smile.jing
 * @version $Id: LoginService.java, v 0.1 Jan 17, 2019 4:43:23 PM smile.jing Exp $
 */
public interface LoginService {

    /**
     * 登录
     * @param loginRequestDTO
     * @param response
     * @return
     */
    AuthUserInfoDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse response);
}
