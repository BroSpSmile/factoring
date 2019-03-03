/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectItem;

/**
 * ProjectItemDao
 * @author smile.jing
 * @version $Id: ProjectItemDao.java, v 0.1 Jan 28, 2019 9:45:20 PM smile.jing Exp $
 */
@Mapper
public interface ProjectItemDao {
    /**
     * 插入
     * @param iterm
     * @return
     */
    @Insert("insert project_item (project_id,item_type,item_name,item_value) values(#{projectId},#{itemType},#{itemName},#{itemValue})")
    long insert(ProjectItem iterm);

    /**
     * 获取
     * @param project
     * @return
     */
    @Select("select * from project_item where project_id = #{id}")
    List<ProjectItem> getItems(Project project);
}
