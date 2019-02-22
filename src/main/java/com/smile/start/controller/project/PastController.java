/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.project;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.meeting.MeetingSearch;
import com.smile.start.model.project.Past;
import com.smile.start.model.project.Project;
import com.smile.start.service.meeting.MeetingService;
import com.smile.start.service.project.PastService;
import com.smile.start.service.project.ProjectService;

/**
 * 三重一大
 * @author smile.jing
 * @version $Id: PastController.java, v 0.1 Feb 17, 2019 7:44:33 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/past")
public class PastController extends BaseController {

    /** 项目服务 */
    @Resource
    private ProjectService projectService;

    /** 会议服务 */
    @Resource
    private MeetingService meetingService;

    /** pastService */
    @Resource
    private PastService    pastService;

    /**
     * 跳转页面
     * @return
     */
    @GetMapping
    public String index() {
        return "project/past";
    }

    /**
     * 保存三重一大
     * @param past
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult Saved(@RequestBody Past past) {
        LoggerUtils.info(logger, "请求参数={}", FastJsonUtils.toJSONString(past));
        return pastService.save(past);
    }

    /**
     * 获取项目
     * @return
     */
    @GetMapping("/project")
    @ResponseBody
    List<Project> getProjects(HttpServletRequest request) {
        Project project = new Project();
        User user = getUserByToken(request);
        project.setUser(user);
        return projectService.queryUnarchivedProjects(project);
    }

    /**
     * 获取会议
     * @return
     */
    @GetMapping("/meeting")
    @ResponseBody
    List<Meeting> getMeetings() {
        MeetingSearch search = new MeetingSearch();
        return meetingService.getMeetings(search);
    }
}
