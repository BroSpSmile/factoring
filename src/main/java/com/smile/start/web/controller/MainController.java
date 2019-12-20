/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author smile.jing
 * @version $Id: MainController.java, v 0.1 Mar 25, 2019 12:14:55 AM smile.jing Exp $
 */
@Controller
@RequestMapping("main")
public class MainController extends BaseController {
    
    /**
     * 首页
     * @return
     */
    @GetMapping
    public String index() {
        return "layout/main";
    }
}
