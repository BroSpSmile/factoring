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
 * 资产报表
 * @author smile.jing
 * @version $Id: AssetsController.java, v 0.1 Feb 27, 2019 10:26:10 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/assets")
public class AssetsController extends BaseController {

    /**
     * 页面
     * @return
     */
    @GetMapping
    public String index() {
        return "report/assets";
    }
}
