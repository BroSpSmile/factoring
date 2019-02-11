package com.smile.start.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
     * 更新
     * @param project
     * @return
     */
    @Update("<script>" + "update factoring_project" + " set id=#{id}" + "<if test = 'projectId!=null'>,project_id = #{projectId}</if>"
            + "<if test = 'projectName!=null'>,project_name = #{projectName}</if>" + "<if test = 'person!=null'> , person = #{person}</if>"
            + "<if test = 'progress!=null'> , progress = #{progress}</if>" + "<if test = 'model!=null'> , model = #{model}</if>" + " where id=#{id} " + "</script>")
    int update(Project project);

    /**
     * 更新
     *
     * @param projectId
     * @param progress
     * @return
     */
    @Update("update factoring_project  set  progress = #{progress}  where project_id=#{projectId}")
    int updateProjectProgress(String projectId, String progress);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from factoring_project where id = #{id}")
    int delete(Long id);

    /**
     * 根据ID获取项目
     * @param id
     * @return
     */
    @Select("select * from factoring_project where id = #{id}")
    Project get(Long id);

    /**
     * 根据项目ID查询项目
     * @param projectId
     * @return
     */
    @Select("select * from factoring_project where project_id=#{projectId}")
    List<Project> findByProjectId(String projectId);

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
    @Select("<script>" + "select * from factoring_project where 1=1 " + "<if test = 'projectId!=null'> and project_id = #{projectId}</if>"
            + "<if test = 'kind!=null'> and kind = #{kind}</if>" + "<if test = 'projectName!=null'> and project_name = #{projectName}</if>"
            + "<if test = 'person!=null'> and person = #{person}</if>" + "<if test = 'progress!=null'> and progress = #{progress}</if>"
            + "<if test = 'progresses!=null'> and progress in  "
            + "<foreach collection='progresses' item='item' open='(' separator=',' close=')'>"
            +  "#{item} "
            +  "</foreach>"
            +  "</if></script>")
    List<Project> findByParam(Project project);
}