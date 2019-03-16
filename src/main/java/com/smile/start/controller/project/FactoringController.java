/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.project;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.project.FactoringDetail;
import com.smile.start.model.project.Project;
import com.smile.start.service.project.FactoringService;
import com.smile.start.service.project.ProjectService;

/**
 * 创建项目
 * @author smile.jing
 * @version $Id: FactoringController.java, v 0.1 Feb 24, 2019 1:54:41 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/factoring")
public class FactoringController extends BaseController {

    /** 保理明细服务 */
    @Resource
    private FactoringService factoringService;

    /**  */
    @Resource
    private ProjectService   projectService;

    /**
     * 创建项目
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "项目ID={}", id);
        model.addAttribute("id", id);
        return "project/factoring";
    }

    /**
     * 获取项目明细
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public FactoringDetail getProjectDetail(@PathVariable Long id) {
        Project project = projectService.getProjectDetail(id);
        FactoringDetail detail = project.getDetail();
        project.setDetail(null);
        LoggerUtils.info(logger, "data:{}", FastJsonUtils.toJSONString(detail));
        return detail;
    }

    /**
     * 创建保理项目
     * @param detail
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult create(HttpServletRequest request, @RequestBody FactoringDetail detail) {
        User user = getUserByToken(request);
        detail.getProject().setUser(user);
        LoggerUtils.info(logger, "请求参数={}", FastJsonUtils.toJSONString(detail));
        return factoringService.create(detail);
    }

    /**
     * 更新项目
     * @param request
     * @param detail
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(HttpServletRequest request, @RequestBody FactoringDetail detail) {
        LoggerUtils.info(logger, "请求参数={}", FastJsonUtils.toJSONString(detail));
        return factoringService.modify(detail);
    }
}
