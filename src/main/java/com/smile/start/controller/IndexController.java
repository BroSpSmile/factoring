package com.smile.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @author Smile
 * @version $Id: IndexController.java, v 0.1 2018年9月25日 下午3:54:39 Smile Exp $
 */
@Controller
public class IndexController {
    /**
     * 
     * @return
     */
    @GetMapping({ "", "/index" })
    public String index() {
        return "index";
    }
}
