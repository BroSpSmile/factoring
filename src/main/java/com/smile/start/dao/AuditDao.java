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
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

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
     * @param audit
     * @return
     */
    @Insert("insert audit (audit_type,create_time,applicant,project,step,role) " + "values(#{auditType},#{createTime},#{applicant.id},#{project.id},#{step},#{role.serialNo})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(Audit audit);

    /**
     * 更新角色
     * @param audit
     * @return
     */
    @Update("update audit set step =#{step} , role = #{role.serialNo} where id = #{id}")
    int updateRole(Audit audit);

    /**
     * 条件查询
     * @param audit
     * @return
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
     * 
     * @param id
     * @return
     */
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "applicant", property = "applicant.id"),
                                      @Result(column = "project", property = "project.id"), @Result(column = "role", property = "role.serialNo") })
    @Select("select * from audit where id = #{id}")
    Audit get(Long id);
}
