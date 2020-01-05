/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.factoring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.web.controller.BaseController;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FactoringLoanController
 * @date Date : 2020年01月05日 11:07
 */
@Controller
@RequestMapping("/factoringLoan")
public class FactoringLoanController extends BaseController {
    /**
     * 跳转视图
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "项目ID={}", id);
        model.addAttribute("id", id);
        return "factoring/loan";
    }

}
