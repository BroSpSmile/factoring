/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.smile.start.model.enums.Step;
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
    @Insert("insert into project_step (project_id,step,status,create_time) values(#{project.id},#{step},#{status},#{createTime})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(StepRecord record);

    /**
     * 
     * @param record
     * @return
     */
    @Update("<script>" + "update project_step set step = #{step},status=#{status}" + "<if test='audit!=null'>, audit_id = #{audit.id}</if>,"
            + "modify_time = #{modifyTime} where id = #{id}" + "</script>")
    int update(StepRecord record);

    /**
     * 删除当前步骤记录
     * @param record
     * @return
     */
    @Delete("delete from project_step where project_id =#{project.id} and step = #{step}")
    int delete(StepRecord record);

    /**
     * 
     * @param projectId
     * @param step
     * @return
     */
    @Select("select * from project_step where project_id = #{projectId} and step = #{step}")
    StepRecord getStep(Long projectId, Step step);

    /**
     * 
     * @param project
     * @return
     */
    @Results(id = "queryMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "project_id", property = "project.id"),
                                        @Result(column = "audit_id", property = "audit.id") })
    @Select("select * from project_step where project_id = #{id}")
    List<StepRecord> query(Project project);
}
