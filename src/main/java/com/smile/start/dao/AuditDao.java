/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.smile.start.model.project.ApplyHistory;
import com.smile.start.model.project.ApplyHistoryParam;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditParam;

/**
 * AuditDao
 * @author smile.jing
 * @version $Id: AuditDao.java, v 0.1 Mar 2, 2019 10:05:41 PM smile.jing Exp $
 */
@Mapper
public interface AuditDao {

    /**
     * 新增
     * @param audit 审核对象
     * @return long
     */
    @Insert("insert audit (audit_type,create_time,applicant,project,step,role) " + "values(#{auditType},#{createTime},#{applicant.id},#{project.id},#{step},#{role.serialNo})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(Audit audit);

    /**
     * 更新角色
     * @param audit 审核对象
     * @return int
     */
    @Update("update audit set step =#{step} , role = #{role.serialNo} where id = #{id}")
    int updateRole(Audit audit);

    /**
     * 更新角色
     * @param audit 审核对象
     * @return List
     */
    @Update("update audit set step =#{step} , role = #{role.serialNo}, applicant = #{applicant.id} where id = #{id}")
    int updateRoleAndApplicant(Audit audit);

    /**
     * 条件查询
     * @param param 查询参数
     * @return List
     */
    @Results(id = "queryMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "applicant", property = "applicant.id"),
                                        @Result(column = "project", property = "project.id"), @Result(column = "role", property = "role.serialNo") })
    @Select("<script>" + "select * from audit t1 where 1 =1 " + "<if test='null != applicant and null!= applicant.id'>and t1.applicant = #{applicant.id}</if>"
            + "<if test='null != projectId and \"\" != projectId'>and exists (select * from factoring_project t2 where t1.project = t2.id and t2.project_id = #{projectId})</if>"
            + "<if test='null != auditType'>and t1.audit_type = #{auditType}</if>" + "<if test='null != beginTime'>and t1.create_time &gt; #{beginTime}</if>"
            + "<if test='null != endTime'>and t1.create_time &lt; #{endTime}</if>"
            + "<if test='null != audit and null!=audit.serialNo'>and exists (select * from auth_user_role_info t3 where t1.role = t3.role_serial_no and t3.user_serial_no = #{audit.serialNo})</if>"
            + "</script>")
    List<Audit> query(AuditParam param);

    /**
     * 审核历史查询
     * @param param 查询参数
     * @return 返回审核历史记录
     */
    @Results(id = "queryHistoryMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "applicant", property = "applicant.id"),
                                               @Result(column = "project", property = "project.id"), @Result(column = "role", property = "role.serialNo") })
    @Select("<script>" + "select * from audit t1 where step = -1 " + "<if test='null != applicant and null!= applicant.id'>and t1.applicant = #{applicant.id}</if>"
            + "<if test='null != projectId and \"\" != projectId'>and exists (select * from factoring_project t2 where t1.project = t2.id and t2.project_id = #{projectId})</if>"
            + "<if test='null != auditType'>and t1.audit_type = #{auditType}</if>" + "<if test='null != beginTime'>and t1.create_time &gt; #{beginTime}</if>"
            + "<if test='null != endTime'>and t1.create_time &lt; #{endTime}</if>"
            + "<if test='null != audit and null!=audit.id'>and exists (select * from audit_record t3 where t1.id = t3.audit and t3.auditor = #{audit.id})</if>" + "</script>")
    List<Audit> queryHistory(AuditParam param);

    /**
     *
     * @param id id
     * @return Audit
     */
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "applicant", property = "applicant.id"),
                                      @Result(column = "project", property = "project.id"), @Result(column = "role", property = "role.serialNo"),
                                      @Result(column = "username", property = "applicant.username") })
    @Select("select t1.*,t2.username from audit t1 inner join auth_user_info t2 on t1.applicant = t2.id where t1.id = #{id}")
    Audit get(Long id);

    /**
     *
     * @param project 项目
     * @param type 类型
     * @return Audit
     */
    @Results(id = "getByProjectMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "applicant", property = "applicant.id"),
                                               @Result(column = "project", property = "project.id"), @Result(column = "role", property = "role.serialNo") })
    @Select("select * from audit where project = #{project} and audit_type= #{type}")
    Audit getByProjectAndType(Long project, String type);

    /**
     * 查询提交申请历史
     * @param param 查询参数
     * @return List
     */
    @Select("<script>" + "SELECT fp.id,a.audit_type,a.create_time " + "FROM audit_record ar,audit a,auth_user_info aui,factoring_project fp " + "WHERE ar.audit=a.id "
            + "AND a.applicant=aui.id " + "AND a.project=fp.id " + "AND ar.type='提出申请' " + "<if test = 'projectId != null'> and fp.project_id = #{projectId}</if>"
            + "<if test = 'auditType != null'> and a.audit_type = #{auditType}</if>" + "<if test = 'applyUser != null'> and aui.serial_no = #{applyUser}</if>"
            + "<if test = 'beginTime != null'> and a.create_time &gt; #{beginTime}</if>" + "<if test = 'endTime != null'> and a.create_time &lt; #{endTime}</if>" + "</script>")
    List<ApplyHistory> queryApplyHistory(ApplyHistoryParam param);
}
