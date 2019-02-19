/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.meeting.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.commons.DateUtil;
import com.smile.start.dao.MeetingDao;
import com.smile.start.dao.ProjectMeetingDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.MeetingStatus;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.meeting.MeetingExt;
import com.smile.start.model.meeting.MeetingSearch;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectMeeting;
import com.smile.start.service.AbstractService;
import com.smile.start.service.UserInfoService;
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
    private MeetingDao        meetingDao;

    /** 关联DAO */
    @Resource
    private ProjectMeetingDao projectMeetingDao;

    /** 用户服务 */
    @Resource
    private UserInfoService   userInfoService;

    /** 
     * @see com.smile.start.service.meeting.MeetingService#search(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<Meeting> search(PageRequest<MeetingSearch> search) {
        PageHelper.startPage(search.getPageNum(), search.getPageSize(), "id desc");
        List<MeetingExt> meetingExts = meetingDao.findByParam(search.getCondition());
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<Meeting> result = new PageInfo<>(toMeeting(meetingExts));
        return result;
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#getMeetings(com.smile.start.model.meeting.MeetingSearch)
     */
    @Override
    public List<Meeting> getMeetings(MeetingSearch search) {
        search.setStatus(MeetingStatus.END);
        search.setBeginTime(DateUtil.addDays(new Date(), -90));
        List<MeetingExt> meetingExts = meetingDao.findByParam(search);
        return toMeeting(meetingExts);
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#get(java.lang.Long)
     */
    @Override
    public Meeting get(Long id) {
        MeetingExt ext = meetingDao.get(id);
        return toMeeting(ext);
    }

    /**
     * 转换为meeting
     * @param meetingExts
     * @return
     */
    private List<Meeting> toMeeting(List<MeetingExt> meetingExts) {
        List<Meeting> meetings = Lists.newArrayListWithCapacity(meetingExts.size());
        meetingExts.forEach(ext -> {
            ext.setOriginator(userInfoService.getUserById(ext.getOriginator().getId()));
            if (StringUtils.isNoneBlank(ext.getParticipantNoList())) {
                for (Long id : getIds(ext.getParticipantNoList())) {
                    ext.addParticipant(userInfoService.getUserById(id));
                }
            }
            meetings.add(ext);
        });
        return meetings;
    }

    /**
     * 
     * @param meetingExt
     * @return
     */
    private Meeting toMeeting(MeetingExt meetingExt) {
        meetingExt.setOriginator(userInfoService.getUserById(meetingExt.getOriginator().getId()));
        if (StringUtils.isNoneBlank(meetingExt.getParticipantNoList())) {
            for (Long id : getIds(meetingExt.getParticipantNoList())) {
                meetingExt.addParticipant(userInfoService.getUserById(id));
            }
        }
        return meetingExt;
    }

    /**
     * id数组
     * @param idLists
     * @return
     */
    private Long[] getIds(String idLists) {
        String[] idStrings = idLists.split(",");
        Long[] ids = (Long[]) ConvertUtils.convert(idStrings, Long.class);
        return ids;

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

    /** 
     * @see com.smile.start.service.meeting.MeetingService#updateMeeting(com.smile.start.model.meeting.MeetingExt)
     */
    @Override
    public BaseResult updateMeeting(MeetingExt meeting) {
        meeting.toParticipantNoList();
        int effect = meetingDao.update(meeting);
        BaseResult result = new BaseResult();
        if (effect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011002");
            result.setErrorMessage("更新会议失败,请重试!");
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#saveMinutes(com.smile.start.model.meeting.Meeting)
     */
    @Override
    @Transactional
    public BaseResult saveMinutes(Meeting meeting) {
        int effect = meetingDao.saveMinutes(meeting);
        if(effect>0) {
            for(Project project:meeting.getProjects()) {
                ProjectMeeting pm = new ProjectMeeting();
                pm.setMeetingId(meeting.getId());
                pm.setProjectId(project.getId());
                List<ProjectMeeting> result = projectMeetingDao.find(pm);
                if (result.size() > 0) {
                    throw new RuntimeException("当前项目已关联此会议纪要");
                }
                long peffect = projectMeetingDao.insert(pm);
                if(peffect<0) {
                    throw new RuntimeException("插入会议纪要失败");
                }
            }
        }
        return new BaseResult();
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#relationMeeting(com.smile.start.model.project.ProjectMeeting)
     */
    @Override
    public BaseResult relationMeeting(List<ProjectMeeting> pms) {
        for (ProjectMeeting pm : pms) {
            List<ProjectMeeting> result = projectMeetingDao.find(pm);
            if (result.size() > 0) {
                throw new RuntimeException("当前项目已关联此会议纪要");
            }
            projectMeetingDao.insert(pm);
        }
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        return result;
    }

}
