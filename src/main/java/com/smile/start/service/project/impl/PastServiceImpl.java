/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.smile.start.dao.ProjectMeetingDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.MeetingKind;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.enums.StepStatus;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.project.Past;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectMeeting;
import com.smile.start.service.AbstractService;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.meeting.MeetingService;
import com.smile.start.service.project.PastService;
import com.smile.start.service.project.ProjectService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: PastServiceImpl.java, v 0.1 Feb 18, 2019 11:15:10 PM smile.jing Exp $
 */
@Service
public class PastServiceImpl extends AbstractService implements PastService {
    /** 项目服务 */
    @Resource
    private ProjectService    projectService;

    /** 会议服务 */
    @Resource
    private MeetingService    meetingService;

    /** 流程引擎 */
    @Resource
    private ProcessEngine     processEngine;

    /** pmDao */
    @Resource
    private ProjectMeetingDao pmDao;

    /** 
     * @see com.smile.start.service.project.PastService#save(com.smile.start.model.project.Past)
     */
    @Override
    @Transactional
    public BaseResult save(Past past) {
        Project project = new Project();
        project.setId(past.getProjectId());
        project.setStep(3);
        List<ProjectMeeting> pms = Lists.newArrayListWithCapacity(past.getMeetingIds().size());
        past.getMeetingIds().forEach(meeting -> pms.add(new ProjectMeeting(past.getProjectId(), meeting)));
        BaseResult result = meetingService.relationMeeting(pms);
        if (result.isSuccess()) {
            if (getMeetings(past.getProjectId()).size() > 2) {
                //三重一大全部关联完成后更改项目状态
                project.setProgress(Progress.PASTMEETING);
                processEngine.changeStatus(project, StepStatus.COMPLETED);
                processEngine.next(project);
            } else {
                project.setProgress(Progress.LATERMEETING);
                processEngine.changeStatus(project, StepStatus.PROCESSING);
            }

            result = projectService.turnover(project);
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.project.PastService#getMeetings(java.lang.Long)
     */
    @Override
    public List<Meeting> getMeetings(Long projectId) {
        List<Meeting> meetings = pmDao.findMeeting(projectId);
        meetings = meetings.stream().filter(meeting -> !MeetingKind.APPROVAL.equals(meeting.getKind())).collect(Collectors.toList());
        return meetings;
    }

}
