/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
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

import com.smile.start.model.project.Installment;

/**
 * InstallmentDao
 * @author smile.jing
 * @version $Id: InstallmentDao.java, v 0.1 Mar 6, 2019 8:51:36 PM smile.jing Exp $
 */
@Mapper
public interface InstallmentDao {
    /**
     * 插入
     * @param installment
     * @return
     */
    @Insert("insert into project_installment (detail,type,amount,installment_date,paied,invoice)"
            + "values(#{detail.id},#{type},#{amount},#{installmentDate},#{paied},#{invoice})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(Installment installment);

    /**
     * 更新
     * @param installment
     * @return
     */
    @Update("<script>" + "update project_installment set id = #{id}" + "<if test = 'detail!=null and detail.id!=null'>,detail = #{detail.id}</if>"
            + "<if test = 'type!=null'>,type = #{type}</if>" + "<if test = 'amount!=null'>,amount = #{amount}</if>"
            + "<if test = 'installmentDate!=null'>,installment_date = #{installmentDate}</if>" + "<if test = 'paied!=null'>,paied = #{paied}</if>"
            + "<if test = 'invoice!=null'>,invoice = #{invoice}</if>" + "where id = #{id}" + "</script>")
    int update(Installment installment);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from project_installment where id = #{id}")
    int delete(Long id);
    
    /**
     * deleteByType
     * @param detailId
     * @param type
     * @return
     */
    @Delete("delete from project_installment where detail = #{detail.id} and type = #{type}")
    int deleteByType(Installment installment);

    /**
     * 查询
     * @param detailId
     * @return
     */
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "detail", property = "detail.id") })
    @Select("select * from project_installment where detail = #{detailId}")
    List<Installment> queryByDetail(Long detailId);
}
