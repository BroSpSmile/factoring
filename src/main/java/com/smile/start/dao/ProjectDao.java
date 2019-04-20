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

import com.smile.start.model.meeting.Meeting;
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
    @Insert("insert into factoring_project (project_id,kind,project_name,person,progress,model,step) "
            + "values (#{projectId},#{kind},#{projectName},#{user.id},#{progress},#{model},#{step})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(Project project);

    /**
     * 更新
     * @param project
     * @return
     */
    @Update("<script>" + "update factoring_project" + " set id=#{id}" + "<if test = 'projectId!=null'>,project_id = #{projectId}</if>"
            + "<if test = 'projectName!=null'>, project_name = #{projectName}</if>" + "<if test = 'progress!=null'> , progress = #{progress}</if>"
            + "<if test = 'step!=null'> , step = #{step}</if>" + "<if test = 'model!=null'> , model = #{model}</if>" + " where id=#{id} " + "</script>")
    int update(Project project);

    /**
     * 更新
     *
     * @param id
     * @param progress
     * @return
     */
    @Update("update factoring_project set progress = #{progress} where id = #{id}")
    int updateProjectProgress(Long id, String progress);

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
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "person", property = "user.id") })
    @Select("select * from factoring_project where id = #{id}")
    Project get(Long id);

    /**
     * 根据项目ID查询项目
     * @param projectId
     * @return
     */
    @Results(id = "findByProjectIdMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "person", property = "user.id") })
    @Select("select * from factoring_project where project_id=#{projectId}")
    List<Project> findByProjectId(String projectId);

    /**
     * 查询全部项目
     * 
     * @return
     */
    @Results(id = "findAllMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "person", property = "user.id") })
    @Select("select * from factoring_project")
    List<Project> findAll();

    /**
     * 查询所有未归档项目
     * @return
     */
    @Results(id = "findUnarchivedProjectsMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "person", property = "user.id") })
    @Select("<script>" + "select * from factoring_project where progress !='FILECOMPLETE'" + "<if test = 'user!=null'>and person = #{user.id} </if> " + "</script>")
    List<Project> findUnarchivedProjects(Project project);

    /**
     * 分页查询
     * @param project
     * @return
     */
    @Results(id = "findByParamMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "person", property = "user.id"),
                                              @Result(column = "username", property = "user.username") })
    @Select("<script>" + "select t1.*,t2.username from factoring_project t1 left join auth_user_info t2  on t1.person = t2.id " + "where 1=1 "
            + "<if test = 'projectId!=null'> and t1.project_id = #{projectId}</if>" + "<if test = 'kind!=null and kind!=\"\"'> and t1.kind = #{kind}</if>"
            + "<if test = 'id!=-1'> and t1.id = #{id}</if>" + "<if test = 'projectName!=null'> and t1.project_name = #{projectName}</if>"
            + "<if test = 'user!=null'> and t1.person = #{user.id}</if>" + "<if test = 'createTime!=null'> and to_days(t1.create_time) = to_days(#{createTime})</if>"
            + "<if test = 'step!=null'> and t1.step = #{step}</if>" + "<if test = 'progresses!=null'> and t1.progress in  "
            + "<foreach collection='progresses' item='item' open='(' separator=',' close=')'>" + "#{item} " + "</foreach></if>" + "</script>")
    List<Project> findByParam(Project project);

    /**
     * 分页查询
     * @param project
     * @return
     */
    @Results(id = "queryFactoringProjectMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "person", property = "user.id"),
                                                        @Result(column = "username", property = "user.username"), @Result(column = "creditor", property = "detail.creditor"),
                                                        @Result(column = "debtor", property = "detail.debtor"), @Result(column = "sign_date", property = "detail.signDate"),
                                                        @Result(column = "base_contract", property = "detail.baseContract"),
                                                        @Result(column = "assignee", property = "detail.assignee"), @Result(column = "receivable", property = "detail.receivable"),
                                                        @Result(column = "drop_amount", property = "detail.dropAmount"), @Result(column = "duration", property = "detail.duration"),
                                                        @Result(column = "remittance_day", property = "detail.remittanceDay"),
                                                        @Result(column = "total_factoring_fee", property = "detail.totalFactoringFee"),
                                                        @Result(column = "return_rate", property = "detail.returnRate") })
    @Select("<script>" + "select t1.*,t2.username,t3.creditor,t3.debtor,t3.sign_date,t3.base_contract,t3.assignee,t3.receivable,"
            + "t3.drop_amount,t3.duration,t3.remittance_day, t3.total_factoring_fee ,t3.return_rate,t3.remark, t3.loan_audit_pass_time " + "from factoring_project t1 "
            + "left join auth_user_info t2  on t1.person = t2.id " + "inner join factoring_detail t3 on t1.id = t3.project_id" + " where 1=1 "
            + "<if test = 'projectId!=null'> and t1.project_id = #{projectId}</if>" + "<if test = 'kind!=null and kind!=\"\"'> and t1.kind = #{kind}</if>"
            + "<if test = 'id!=-1'> and t1.id = #{id}</if>" + "<if test = 'projectName!=null'> and t1.project_name = #{projectName}</if>"
            + "<if test = 'user!=null'> and t1.person = #{user.id}</if>" + "<if test = 'createTime!=null'> and to_days(t1.create_time) = to_days(#{createTime})</if>"
            + "<if test = 'step!=null'> and t1.step = #{step}</if>" + "</script>")
    List<Project> queryFactoringProject(Project project);

    /**
     * 根据会议查找关联项目
     * @param meeting
     * @return
     */
    @Results(id = "findByMeetingMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "person", property = "user.id") })
    @Select("select t1.* from factoring_project t1 inner join project_meeting t2 on t1.id = t2.project_id where t2.meeting_id = #{id}")
    List<Project> findByMeeting(Meeting meeting);
}