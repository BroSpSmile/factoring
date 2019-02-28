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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.project.Project;
import com.smile.start.service.project.ProjectService;

/**
 * 立项申请
 * @author smile.jing
 * @version $Id: ApplyController.java, v 0.1 Jan 26, 2019 5:16:31 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/apply")
public class ApplyController extends BaseController {

    /** 项目服务 */
    @Resource
    private ProjectService projectService;

    /**
     * 页面跳转
     * @param id
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "立项申请项目ID={}", id);
        model.addAttribute("id", id);
        return "project/apply";
    }

    /**
     * 申请提价
     * @param project
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult apply(@RequestBody Project project) {
        LoggerUtils.info(logger, "立项申请project={}", FastJsonUtils.toJSONString(project));
        project.setProgress(Progress.TUNEUP);
        return projectService.apply(project);
    }
}
