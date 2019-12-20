/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao.audit;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditRecord;

/**
 * AuditRecordDao
 * @author smile.jing
 * @version $Id: AuditRecordDao.java, v 0.1 Mar 2, 2019 10:43:56 PM smile.jing Exp $
 */
@Mapper
public interface AuditRecordDao {
    /**
     * 
     * @param record
     * @return
     */
    @Insert("insert into audit_record (audit,type,auditor,result,remark,status,audit_time) values (#{audit.id},#{type},#{auditor.id},#{result},#{remark},#{status},#{auditTime})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(AuditRecord record);

    /**
     * 
     * @param audit
     * @return
     */
    @Results(id = "queryMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "audit", property = "audit.id"),
                                        @Result(column = "auditor", property = "auditor.id"), @Result(column = "username", property = "auditor.username") })
    @Select("select t1.*,t2.username from audit_record t1 inner join auth_user_info t2 on t1.auditor = t2.id  where t1.audit = #{id} order by id desc")
    List<AuditRecord> query(Audit audit);

    /**
     * 
     * @param auditId
     * @param type
     * @return
     */
    @Results(id = "getLastMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "audit", property = "audit.id"),
                                          @Result(column = "auditor", property = "auditor.id"), @Result(column = "username", property = "auditor.username") })
    @Select("select r.* ,u.username from audit_record  r " + "inner join auth_user_info u on r.auditor = u.id "
            + "where audit =#{auditId} and type = #{type} order by id desc limit 1")
    AuditRecord getLast(Long auditId, String type);
}
