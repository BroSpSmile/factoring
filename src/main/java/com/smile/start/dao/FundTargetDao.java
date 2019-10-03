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

import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.fund.FundTargetQuery;

/**
 * 直投项目DAO
 * @author smile.jing
 * @version $Id: FundTargetDao.java, v 0.1 2019年8月10日 下午5:42:41 smile.jing Exp $
 */
@Mapper
public interface FundTargetDao {
    /**
     * 新增项目
     * 
     * @param project
     * @return
     */
    @Insert("insert into fund_project (project_id,project_name,project_step,industry,location,main_business,member_a,member_b,investment,"
            + "investment_part,share_hoding_rate,pre_val,post_val,investemnt_time,company_sort_name,company_full_name,"
            + "controller_owner,registered_capital,chairman,address,project_channel,create_time,operator) "
            + "values (#{projectId},#{projectName},#{projectStep},#{industry},#{location},#{mainBusiness},#{memberA.id},#{memberB.id},#{investment},"
            + "#{investmentPart},#{shareHodingRate},#{preVal},#{postVal},#{investemntTime},#{companySortName},#{companyFullName},"
            + "#{controllerOwner},#{registeredCapital},#{chairman},#{address},#{projectChannel},current_timestamp,#{operator.id})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(FundTarget target);

    /**
     * 更新
     *
     * @param id
     * @param progress
     * @return
     */
    @Update("<script> update fund_project set id = #{id}" + "<if test = 'projectName!=null and \"\"!=projectName'>,project_name = #{projectName}</if>"
            + "<if test = 'industry!=null and \"\"!=industry'>,industry = #{industry}</if>"
            + "<if test = 'mainBusiness!=null and \"\"!=mainBusiness'>,main_business = #{mainBusiness}</if>"
            + "<if test = 'projectStep!=null'>,project_step = #{projectStep}</if>"
            + "<if test = 'memberA!=null and memberA.id!=null'>,member_a = #{memberA.id}</if>"
            + "<if test = 'memberB!=null and memberB.id!=null'>,member_b = #{memberB.id}</if>"
            + "<if test = 'location!=null and \"\"!=location'>,location = #{location}</if>" + "<if test = 'investment!=null and \"\"!=investment'>,investment = #{investment}</if>"
            + "<if test = 'investmentPart!=null and \"\"!=investmentPart'>,investment_part = #{investmentPart}</if>"
            + "<if test = 'shareHodingRate!=null and \"\"!=shareHodingRate'>,share_hoding_rate = #{shareHodingRate}</if>" + "<if test = 'preVal!=null'>,pre_val = #{preVal}</if>"
            + "<if test = 'postVal!=null'>,post_val = #{postVal}</if>" + "<if test = 'investemntTime!=null'>,investemnt_time = #{investemntTime}</if>"
            + "<if test = 'companySortName!=null and \"\"!=companySortName'>,company_sort_name = #{companySortName}</if>"
            + "<if test = 'companyFullName!=null and \"\"!=companyFullName'>,company_full_name = #{companyFullName}</if>"
            + "<if test = 'controllerOwner!=null and \"\"!=controllerOwner'>,controller_owner = #{companySortName}</if>"
            + "<if test = 'companySortName!=null and \"\"!=companySortName'>,company_sort_name = #{controllerOwner}</if>"
            + "<if test = 'registeredCapital!=null'>,registered_capital = #{registeredCapital}</if>" + "<if test = 'chairman!=null and \"\"!=chairman'>,chairman = #{chairman}</if>"
            + "<if test = 'address!=null and \"\"!=address'>,address = #{address}</if>"
            + "<if test = 'projectChannel!=null and \"\"!=projectChannel'>,project_channel = #{projectChannel}</if>" + "where id = #{id}" + "</script>")
    int updateTarget(FundTarget target);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from fund_project where id = #{id}")
    int delete(Long id);

    /**
     * 根据标的ID获取标的
     * @param target
     * @return
     */
    @Select("select * from fund_project where id = #{projectId} limit 1")
    FundTarget getByProjectId(FundTarget target);

    /**
     * 根据ID获取标的
     * @param target
     * @return
     */
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "operator", property = "operator.id"),
                                      @Result(column = "member_a", property = "memberA.id"),@Result(column = "member_b", property = "memberB.id")})
    @Select("select * from fund_project where id = #{id} limit 1")
    FundTarget get(Long id);

    /**
     * 
     * @param target
     * @return
     */
    @Results(id = "queryMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "operator", property = "operator.id"),
                                        @Result(column = "member_a", property = "memberA.id"),@Result(column = "member_b", property = "memberB.id")})
    @Select("<script>" + "select * from fund_project where 1 = 1" + "<if test = 'projectId!=null and \"\"!=projectId'>,project_id = #{projectId}</if>"
            + "<if test = 'projectName!=null and \"\"!=projectName'>,project_name = #{projectName}</if>"
            + "<if test = 'projectStep!=null and \"\"!=projectStep'>,project_step = #{projectStep}</if>"
            + "<if test = 'stateDate!=null'>,to_days(investemnt_time) &gt;= to_days(#{stateDate})</if>"
            + "<if test = 'endDate!=null'>,to_days(investemnt_time) &lt;= #{endDate}</if>"
            + "<if test = 'investmentPart!=null and \"\"!=investmentPart'>,investment_part = #{investmentPart}</if>" + "</script>")
    List<FundTarget> queryFundTarget(FundTargetQuery query);
}
