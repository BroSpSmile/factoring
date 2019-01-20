/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.meeting;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.meeting.MeetingSearch;

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
     * 创建会议
     * @param meeting
     * @return
     */
    BaseResult createMeeting(Meeting meeting);
}
