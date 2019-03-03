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

import com.smile.start.model.project.AuditRecord;
import com.smile.start.model.project.AuditRecordItem;

/**
 * 
 * @author smile.jing
 * @version $Id: AuditRecordItemDao.java, v 0.1 Mar 2, 2019 10:51:57 PM smile.jing Exp $
 */
@Mapper
public interface AuditRecordItemDao {

    /**
     *新增
     * @param item
     * @return
     */
    @Insert("insert into audit_record_item (record,item_name,item_value) values (#{record.id},#{itemName},#{itemValue})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(AuditRecordItem item);

    /**
     * 
     * @param record
     * @return
     */
    @Results(id = "queryMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "record", property = "record.id") })
    @Select("select * from audit_record_item where record = #{id}")
    List<AuditRecordItem> query(AuditRecord record);
}
