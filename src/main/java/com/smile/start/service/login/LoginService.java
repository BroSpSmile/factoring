/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.login;

import com.smile.start.model.organization.Employee;

/**
 * 登录服务
 * @author smile.jing
 * @version $Id: LoginService.java, v 0.1 Jan 17, 2019 4:43:23 PM smile.jing Exp $
 */
public interface LoginService {
    /**
     * 用户登录
     * @param employee
     * @return
     */
    boolean login(Employee employee);
}
