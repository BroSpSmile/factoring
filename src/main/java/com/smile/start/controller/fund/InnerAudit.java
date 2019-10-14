/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.controller.fund;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;

/**
 * 内核页面
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: InnerAudit
 * @date Date : 2019年10月14日 22:24
 */
@Controller
@RequestMapping("innerAudit")
public class InnerAudit extends BaseController {
    /**
     * 页面路由
     *
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "项目ID={}", id);
        model.addAttribute("id", id);
        return "fund/innerAudit";
    }
}
