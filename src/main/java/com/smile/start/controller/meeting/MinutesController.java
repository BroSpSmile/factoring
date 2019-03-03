/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.meeting;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.DocUtil;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.project.Project;
import com.smile.start.service.meeting.MeetingService;
import com.smile.start.service.project.ProjectService;

import freemarker.template.TemplateException;

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
     * 保存会议纪要
     * @param meeting
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult saved(@RequestBody Meeting meeting) {
        LoggerUtils.info(logger, "会议纪要请求参数={}", FastJsonUtils.toJSONString(meeting));
        return meetingService.saveMinutes(meeting);
    }

    /**
     * 下载会议纪要
     * @param request
     * @param response
     * @throws TemplateException 
     * @throws IOException 
     */
    @GetMapping("/download/{id}")
    public void downloand(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws IOException, TemplateException {
        Meeting meeting = meetingService.get(id);
        Map<String, Object> data = Maps.newHashMap();
        data.put("head_year", "2019");
        data.put("number", "01");
        data.put("content", meeting.getMinutes());
        data.put("meeting_data", DateUtil.format(new Date(), DateUtil.chineseDtFormat));
        File file = DocUtil.createDoc(meeting.getTheme(), "meeting.xml", data);
        if (file.exists()) {
            download(meeting.getTheme() + ".doc", file, response);
        }
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
