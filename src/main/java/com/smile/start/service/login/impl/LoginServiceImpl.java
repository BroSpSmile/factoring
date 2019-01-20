/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.login.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.smile.start.model.organization.Employee;
import com.smile.start.service.AbstractService;
import com.smile.start.service.login.LoginService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: LoginServiceImpl.java, v 0.1 Jan 17, 2019 5:53:07 PM smile.jing Exp $
 */
@Service
public class LoginServiceImpl extends AbstractService implements LoginService {

    /** 
     * @see com.smile.start.service.login.LoginService#login(com.smile.start.model.organization.Employee)
     */
    @Override
    public boolean login(Employee employee) {
        if (StringUtils.equals(employee.getUserName(), "admin") && StringUtils.equals(employee.getPassword(), "123456")) {
            return true;
        }
        return false;
    }

}
