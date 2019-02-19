/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.ProjectModel;
import com.smile.start.model.project.Past;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectMeeting;
import com.smile.start.service.AbstractService;
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
    private ProjectService projectService;

    /** 会议服务 */
    @Resource
    private MeetingService meetingService;

    /** 
     * @see com.smile.start.service.project.PastService#save(com.smile.start.model.project.Past)
     */
    @Override
    @Transactional
    public BaseResult save(Past past) {
        Project project = new Project();
        project.setId(past.getProjectId());
        project.setModel(ProjectModel.valueOf(past.getProjectModel()));
        List<ProjectMeeting> pms = Lists.newArrayListWithCapacity(past.getMeetingIds().size());
        past.getMeetingIds().forEach(meeting -> pms.add(new ProjectMeeting(past.getProjectId(), meeting)));
        BaseResult result = projectService.updateProject(project);
        if (result.isSuccess()) {
            result = meetingService.relationMeeting(pms);
        }
        return result;
    }

}
