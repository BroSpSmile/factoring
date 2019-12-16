/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.controller.finance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smile.start.controller.BaseController;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundFinanceController
 * @date Date : 2019年11月10日 19:46
 */
@Controller
@RequestMapping("fundFinanceManage")
public class FundFinanceController extends BaseController {

    /**
     * 跳转
     *
     * @return
     */
    @GetMapping
    public String index() {
        return "finance/fundFinanceManage";
    }

}
