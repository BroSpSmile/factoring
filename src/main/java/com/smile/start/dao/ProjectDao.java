package com.smile.start.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.smile.start.model.project.Project;

/**
 * @author smile.jing
 */
@Mapper
public interface ProjectDao {

	/**
	 * 新增项目
	 * 
	 * @param project
	 * @return
	 */
	@Insert("insert into factoring_project (project_id,kind,project_name,person,progress) values (#{projectId},#{kind},#{projectName},#{person},#{progress})")
	long insert(Project project);

	/**
	 * 查询全部项目
	 * 
	 * @return
	 */
	@Select("select * from factoring_project")
	List<Project> findAll();
	
	/**
	 * 分页查询
	 * @param project
	 * @return
	 */
	@Select("<script>"
            + "select * from factoring_project where 1=1 "
	        + "<if test = 'projectId!=null'> and project_id = #{projectId}</if>"
	        + "<if test = 'kind!=null'> and kind = #{kind}</if>"
	        + "<if test = 'projectName!=null'> and project_name = #{projectName}</if>"
	        + "<if test = 'person!=null'> and person = #{person}</if>"
	        + "<if test = 'progress!=null'> and progress = #{progress}</if>"
	        + "</script>")
	List<Project> findByParam(Project project);
}