/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.meeting.MeetingExt;
import com.smile.start.model.meeting.MeetingSearch;

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
    @Insert("insert meeting (theme,begin_time,end_time,status,place,content,remind,originator,participant) values(#{theme},#{beginTime},#{endTime},#{status},#{place},#{content},#{remind},#{originator.id},#{participantNoList})")
    long insert(Meeting meeting);

    /**
     * 
     * @param meeting
     * @return
     */
    @Update("update meeting set theme=#{theme},begin_time=#{beginTime},end_time=#{endTime},status=#{status},place=#{place},content=#{content},remind=#{remind},originator=#{originator.id},participant=#{participantNoList} where id=#{id}")
    int update(MeetingExt meeting);

    /**
     * 查询会议
     * @param search
     * @return
     */
    @Results(id = "meetingMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "originator", property = "originator.id"),
                                          @Result(column = "participant", property = "participantNoList") })
    @Select("<script>" + "select * from meeting where 1=1" + "<if test = 'status!=null'> and status = #{status}</if>"
            + "<if test = 'theme!=null'> and theme like concat('%',#{theme}, '%')</if>" + "<if test = 'beginTime!=null'> and begin_time &gt; #{beginTime}</if>"
            + "<if test = 'endTime!=null'> and begin_time &lt; #{endTime}</if>" + "<if test = 'launchId!=null'> and originator = #{launchId}</if>"
            + "<if test = 'partakeId!=null'> and participant like concat('%',#{partakeId}, '%')</if>" + "</script>")
    List<MeetingExt> findByParam(MeetingSearch search);
}