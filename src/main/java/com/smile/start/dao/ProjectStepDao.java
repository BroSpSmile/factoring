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

import com.smile.start.model.project.Project;
import com.smile.start.model.project.StepRecord;

/**
 * ProjectRecordDao
 * @author smile.jing
 * @version $Id: ProjectRecordDao.java, v 0.1 Mar 3, 2019 10:52:19 PM smile.jing Exp $
 */
@Mapper
public interface ProjectStepDao {
    /**
     * 
     * @param record
     * @return
     */
    @Insert("insert into project_step (project_id,progress,status,create_time) values(#{project.id},#{progress},#{status},#{createTime})")
    long insert(StepRecord record);

    /**
     * 
     * @param project
     * @return
     */
    @Results(id = "queryMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "project_id", property = "project.id") })
    @Select("select * from project_step where project_id = #{project.id}")
    List<StepRecord> query(Project project);
}
