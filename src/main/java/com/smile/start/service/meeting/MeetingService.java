/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.meeting;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.meeting.MeetingExt;
import com.smile.start.model.meeting.MeetingSearch;
import com.smile.start.model.project.ProjectMeeting;

/**
 * 会议服务
 * @author smile.jing
 * @version $Id: MeetingService.java, v 0.1 Jan 17, 2019 7:49:16 PM smile.jing Exp $
 */
public interface MeetingService {
    /**
     * 会议搜索服务
     * @param search
     * @return
     */
    PageInfo<Meeting> search(PageRequest<MeetingSearch> search);

    /**
     * 获取会议
     * @param search
     * @return
     */
    List<Meeting> getMeetings(MeetingSearch search);

    /**
     * 根据ID获取会议内容
     * @param id
     * @return
     */
    Meeting get(Long id);

    /**
     * 创建会议
     * @param meeting
     * @return
     */
    BaseResult createMeeting(MeetingExt meeting);

    /**
     * 更新会议
     * @param meeting
     * @return
     */
    BaseResult updateMeeting(MeetingExt meeting);

    /**
     * 保存会议纪要
     * @param meeting
     * @return
     */
    BaseResult saveMinutes(Meeting meeting);

    /**
     * 关联会议
     * @param pms
     * @return
     */
    BaseResult relationMeeting(List<ProjectMeeting> pms);

    /**
     * 会议调度任务
     * @return
     */
    BaseResult schedule();
}
