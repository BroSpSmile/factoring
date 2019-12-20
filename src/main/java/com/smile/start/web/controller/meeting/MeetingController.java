/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.meeting;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.web.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.MeetingStatus;
import com.smile.start.model.meeting.MeetingExt;
import com.smile.start.model.meeting.MeetingSearch;
import com.smile.start.service.meeting.MeetingService;

/**
 * 
 * @author smile.jing
 * @version $Id: MeetingController.java, v 0.1 Jan 17, 2019 7:38:08 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/meeting")
public class MeetingController extends BaseController {

    /** 会议服务 */
    @Resource
    private MeetingService meetingService;

    /**
     * index
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id)) {
            model.addAttribute("id", id);
        }
        return "meeting/meeting";
    }

    /**
    * 分页查询
    * @param query
    * @return
    */
    @PostMapping(value = "/query")
    @ResponseBody
    public PageInfo<MeetingExt> query(HttpServletRequest request, @RequestBody PageRequest<MeetingSearch> search) {
        List<String> types = search.getCondition().getType();
        for (String type : types) {
            if (StringUtils.equals(type, "launch")) {
                search.getCondition().setLaunchId(getUserByToken(request).getId());
            }
            if (StringUtils.equals(type, "partake")) {
                search.getCondition().setPartakeId(getUserByToken(request).getId());
            }
        }
        return meetingService.search(search);
    }

    /**
     * 创建会议
     * @param meeting
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult create(HttpServletRequest request, @RequestBody MeetingExt meeting) {
        LoggerUtils.info(logger, "创建会议={}", meeting);
        User user = getUserByToken(request);
        if (null != user) {
            meeting.setOriginator(user);
        }
        meeting.setStatus(MeetingStatus.PLAN);
        return meetingService.createMeeting(meeting);
    }

    /**
     * 更新会议
     * @param request
     * @param meeting
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(HttpServletRequest request, @RequestBody MeetingExt meeting) {
        LoggerUtils.info(logger, "更新会议={}", meeting);
        User user = getUserByToken(request);
        if (null != user) {
            meeting.setOriginator(user);
        }
        return meetingService.updateMeeting(meeting);
    }
}
