/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.smile.start.model.meeting.Meeting;
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
    
    /**
     * 删除会议
     * @param pm
     * @return
     */
    @Delete("delete from project_meeting where project_id=#{projectId} and meeting_id=#{meetingId}")
    int delete(ProjectMeeting pm);

    /**
     * 查询项目已关联会议
     * @param projectId
     * @return
     */
    @Select("select t1.* from meeting t1 inner join project_meeting t2 on t1.id = t2.meeting_id where t2.project_id = #{projectId}")
    List<Meeting> findMeeting(Long projectId);
}
