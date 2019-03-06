/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.finance;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smile.start.controller.BaseController;
import com.smile.start.service.finance.FinanceService;

/**
 * 财务操作
 * @author Jacky
 * @version $Id: FinanceOperateController.java, v 0.1 2019年3月6日 下午3:23:21 Jacky Exp $
 */
@Controller
@RequestMapping("/financeOperate")
public class FinanceOperateController extends BaseController {

    /**  */
    @Resource
    private FinanceService financeService;

    /**
     * 跳转
     * @return
     */
    @GetMapping
    public String index() {
        return "finance/operate";
    }

}
