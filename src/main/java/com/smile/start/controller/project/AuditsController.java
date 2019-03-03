/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smile.start.controller.BaseController;

/**
 * 
 * @author smile.jing
 * @version $Id: AuditsController.java, v 0.1 Mar 3, 2019 2:05:37 AM smile.jing Exp $
 */
@Controller
@RequestMapping("/audits")
public class AuditsController extends BaseController {

    /**
     * 页面索引
     * @return
     */
    @GetMapping
    public String index() {
        return "project/audits";
    }
}
