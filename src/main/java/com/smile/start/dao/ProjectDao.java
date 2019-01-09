package com.smile.start.dao;

import java.util.List;

import com.smile.start.model.project.Project;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author smile.jing
 */
@Mapper
public interface ProjectDao {

    /**
     * 查询全部项目
     * 
     * @return
     */
    @Select("SELECT * FROM factoring_project")
    List<Project> findAll();
}