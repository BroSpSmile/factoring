/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.meeting.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.commons.DateUtil;
import com.smile.start.dao.MeetingDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.MeetingStatus;
import com.smile.start.model.enums.MinutesKind;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.meeting.MeetingExt;
import com.smile.start.model.meeting.MeetingSearch;
import com.smile.start.model.organization.Employee;
import com.smile.start.model.organization.Person;
import com.smile.start.service.AbstractService;
import com.smile.start.service.meeting.MeetingService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: MeetingServiceImpl.java, v 0.1 Jan 17, 2019 7:51:56 PM smile.jing Exp $
 */
@Service
public class MeetingServiceImpl extends AbstractService implements MeetingService {

    /** 会议dao */
    @Resource
    private MeetingDao meetingDao;

    /** 
     * @see com.smile.start.service.meeting.MeetingService#search(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<Meeting> search(PageRequest<MeetingSearch> search) {
        PageInfo<Meeting> resultInfo = new PageInfo<Meeting>();
        resultInfo.setTotal(100L);
        resultInfo.setPageSize(10);
        Meeting meeting = new Meeting();
        meeting.setBeginTime(new Date());
        meeting.setEndTime(DateUtil.addMinutes(meeting.getBeginTime(), 30));
        meeting.setTheme("三中全会");
        meeting.setStatus(MeetingStatus.PLAN);
        meeting.setMinutesKind(MinutesKind.RESOLUTIONMODEL);
        meeting.setPlace("人民大会堂");
        meeting.setRemind(30);
        Employee one = new Employee();
        Person person = new Person();
        person.setName("景勇翔");
        one.setPerson(person);
        List<Employee> allEmployees = Lists.newArrayList();
        allEmployees.add(one);
        allEmployees.add(one);
        List<Meeting> meetings = Lists.newArrayList();
        meetings.add(meeting);
        resultInfo.setList(meetings);
        return resultInfo;
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#createMeeting(com.smile.start.model.meeting.Meeting)
     */
    @Override
    public BaseResult createMeeting(MeetingExt meeting) {
        meeting.toParticipantNoList();
        long effect = meetingDao.insert(meeting);
        BaseResult result = new BaseResult();
        if (effect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011002");
            result.setErrorMessage("新增会议失败,请重试!");
        }
        return result;
    }

}
