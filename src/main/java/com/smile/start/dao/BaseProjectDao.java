/**
 * VIP.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import com.smile.start.model.project.BaseProject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: BaseProjectDao
 * @date Date : 2019年10月03日 20:48
 */
@Mapper
public interface BaseProjectDao {

    /**
     * 新增项目
     *
     * @param project
     * @return
     */
    @Insert("insert into factoring_project (project_id,kind,project_name,person,create_time) "
            + "values (#{projectId},#{kind},#{projectName},#{operator.id},#{createTime})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(BaseProject<?> project);

}
