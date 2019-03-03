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
                                      @Result(column = "auditor", property = "auditor.id")})
    @Select("select * from audit_record where audit = #{id} order by id desc")
    List<AuditRecord> query(Audit audit);
}
