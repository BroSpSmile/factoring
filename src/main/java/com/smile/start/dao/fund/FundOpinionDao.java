/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.dao.fund;

import org.apache.ibatis.annotations.*;

import com.smile.start.model.fund.FundOpinion;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundOpinionDao
 * @date Date : 2020年01月06日 16:43
 */
@Mapper
public interface FundOpinionDao {
    /**
     * 新增项目
     *
     * @param opinion
     * @return
     */
    @Insert("insert into fund_opinion (project_id,transfer,team,industry,core,profit,biz,compete,finance," + "demand,advantage,risk,opinion,remark) "
            + "values (#{project.id},#{transfer},#{team},#{industry},#{core},#{profit},#{biz},#{compete},#{finance}," + "#{demand},#{advantage},#{risk},#{opinion},#{remark})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(FundOpinion opinion);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Delete("delete from fund_opinion where id = #{id}")
    int delete(Long id);

    /**
     * 更新
     *
     * @param opinion
     * @return
     */
    @Update("<script> update fund_opinion set id = #{id} " + "<if test = 'transfer!=null'>,transfer = #{transfer}</if>"
            + "<if test = 'team!=null and \"\"!=team'>,team = #{team}</if>" + "<if test = 'industry!=null and \"\"!=industry'>,industry = #{industry}</if>"
            + "<if test = 'core!=null and \"\"!=core'>,core = #{core}</if>" + "<if test = 'profit!=null and \"\"!=profit'>,profit = #{profit}</if>"
            + "<if test = 'biz!=null and \"\"!=biz'>,biz = #{biz}</if>" + "<if test = 'compete!=null and \"\"!=compete'>,compete = #{compete}</if>"
            + "<if test = 'finance!=null and \"\"!=finance'>,finance = #{finance}</if>" + "<if test = 'demand!=null and \"\"!=demand'>,demand = #{demand}</if>"
            + "<if test = 'advantage!=null and \"\"!=advantage'>,advantage = #{advantage}</if>" + "<if test = 'risk!=null and \"\"!=risk'>,risk = #{risk}</if>"
            + "<if test = 'opinion!=null and \"\"!=opinion'>,opinion = #{opinion}</if>" + "<if test = 'remark!=null and \"\"!=remark'>,remark = #{remark}</if>"
            + " where id = #{id}" + "</script>")
    int update(FundOpinion opinion);

    /**
     * 根据项目ID获取信息
     *
     * @param id
     * @return
     */
    @Results(id = "getFundOpinionMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "project_id", property = "project.id") })
    @Select("select * from fund_opinion where project_id = #{id} limit 1")
    FundOpinion getFundOpinion(Long id);
}
