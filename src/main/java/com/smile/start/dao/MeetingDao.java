/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.smile.start.model.meeting.Meeting;

/**
 * MeetingDao
 * @author smile.jing
 * @version $Id: MeetingDao.java, v 0.1 Jan 28, 2019 11:03:18 PM smile.jing Exp $
 */
@Mapper
public interface MeetingDao {
    /**
     * 插入会议
     * @param meeting
     * @return
     */
    @Insert("insert meeting (theme,begin_time,end_time,status,place,content,remind,originator) values(#{theme},#{beginTime},#{endTime},#{status},#{place},#{content},#{remind},#{originator.id})")
    int insert(Meeting meeting);
}
