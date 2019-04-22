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

import com.smile.start.model.enums.ProjectItemType;
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
    @Insert("insert project_item (project_id,item_type,item_name,item_value,attach_type) values(#{projectId},#{itemType},#{itemName},#{itemValue},#{attachType})")
    long insert(ProjectItem iterm);

    /**
     * 
     * @param item
     * @return
     */
    @Delete("delete from project_item where id = #{id}")
    int delete(ProjectItem item);

    /**
     * 获取
     * @param project
     * @return
     */
    @Select("select * from project_item where project_id = #{id}")
    List<ProjectItem> getItems(Project project);

    /**
     * 
     * @param projectId
     * @param type
     * @return
     */
    @Select("select * from project_item where project_id = #{projectId} and item_type = #{type}")
    List<ProjectItem> getTypeItems(Long projectId, ProjectItemType type);

    /**
     * 删除指定类型附件
     * @param projectId
     * @param type
     * @return
     */
    @Delete("delete from project_item where project_id = #{projectId} and item_type = #{type}")
    int deleteItems(Long projectId, ProjectItemType type);
}
