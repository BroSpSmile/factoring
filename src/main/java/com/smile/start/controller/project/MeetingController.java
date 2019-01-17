/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.project;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.meeting.MeetingSearch;
import com.smile.start.service.meeting.MeetingService;

/**
 * 
 * @author smile.jing
 * @version $Id: MeetingController.java, v 0.1 Jan 9, 2019 5:15:06 PM smile.jing
 *          Exp $
 */
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
    public String index() {
        return "project/meeting";
    }

    /**
    * 分页查询
    * @param query
    * @return
    */
    @PostMapping(value = "/query")
    @ResponseBody
    public PageInfo<Meeting> query(@RequestBody PageRequest<MeetingSearch> search) {
        System.out.println(FastJsonUtils.toJSONString(search));
        return meetingService.search(search);
    }
}
