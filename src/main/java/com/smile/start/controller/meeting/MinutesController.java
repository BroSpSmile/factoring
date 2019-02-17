/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.meeting;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.project.Project;
import com.smile.start.service.meeting.MeetingService;
import com.smile.start.service.project.ProjectService;

/**
 * 会议纪要
 * @author smile.jing
 * @version $Id: MinutesController.java, v 0.1 Feb 12, 2019 9:04:22 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/minutes")
public class MinutesController extends BaseController {

    /** 会议服务 */
    @Resource
    private MeetingService meetingService;

    /** 项目服务 */
    @Resource
    private ProjectService projectService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "立项申请项目ID={}", id);
        model.addAttribute("id", id);
        return "meeting/minutes";
    }

    /**
     * 获取项目
     * @return
     */
    @RequestMapping(value = "/project", method = RequestMethod.GET)
    @ResponseBody
    public List<Project> getProjects() {
        return projectService.queryUnarchivedProjects(new Project());
    }

    /**
     * 根据ID获取会议内容
     * @param id
     * @return
     */
    @RequestMapping(value = "/meeting/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Meeting getzMeeting(@PathVariable Long id) {
        return meetingService.get(id);
    }
}
