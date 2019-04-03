/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.report;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.controller.BaseController;
import com.smile.start.model.project.Profit;
import com.smile.start.service.project.FactoringService;

/**
 * 利润报表
 * @author smile.jing
 * @version $Id: ProfitController.java, v 0.1 Feb 27, 2019 10:28:08 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/profit")
public class ProfitController extends BaseController {

    /** 保理服务 */
    @Resource
    private FactoringService factoringService;

    /**
     * 
     * @return
     */
    @GetMapping
    public String index() {
        return "report/profit";
    }

    /**
     * 获取利润报表
     * @param month
     * @return
     */
    @GetMapping("/{month}")
    @ResponseBody
    public Profit getProsfit(@PathVariable String month) {
        return factoringService.getProfit(month);
    }
}
