/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.report;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smile.start.controller.BaseController;

/**
 * 利润报表
 * @author smile.jing
 * @version $Id: ProfitController.java, v 0.1 Feb 27, 2019 10:28:08 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/profit")
public class ProfitController extends BaseController {
    /**
     * 
     * @return
     */
    @GetMapping
    public String index() {
        return "report/profit";
    }
}
