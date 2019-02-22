/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.smile.start.model.project.ProjectMeeting;

/**
 * 
 * @author smile.jing
 * @version $Id: ProjectMeetingDao.java, v 0.1 Feb 18, 2019 10:51:47 PM smile.jing Exp $
 */
@Mapper
public interface ProjectMeetingDao {
    /**
     * 
     * @param projectMeeting
     * @return
     */
    @Insert("insert into project_meeting (project_id,meeting_id) values(#{projectId},#{meetingId})")
    long insert(ProjectMeeting projectMeeting);
    
    /**
     * 查询关联会议
     * @param pm
     * @return
     */
    @Select("select * from project_meeting where project_id=#{projectId} and meeting_id=#{meetingId}")
    List<ProjectMeeting> find(ProjectMeeting pm);
}
