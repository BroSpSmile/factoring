/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao.fund;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.smile.start.model.fund.FundInfos;
import com.smile.start.model.fund.FundProject;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.BaseProjectQuery;

/**
 * 直投项目DAO
 *
 * @author smile.jing
 * @version $Id: FundTargetDao.java, v 0.1 2019年8月10日 下午5:42:41 smile.jing Exp $
 */
@Mapper
public interface FundTargetDao {
    /**
     * 新增项目
     *
     * @param target
     * @return
     */
    @Insert("insert into fund_project (project_id,project_name,project_step,industry,location,main_business,member_a,member_b,investment,"
            + "investment_part,share_hoding_rate,pre_val,post_val,investemnt_time,company_sort_name,company_full_name,"
            + "controller_owner,registered_capital,chairman,address,project_channel,create_time) "
            + "values (#{projectId},#{projectName},#{projectStep},#{industry},#{location},#{mainBusiness},#{memberA.id},#{memberB.id},#{investment},"
            + "#{investmentPart},#{shareHodingRate},#{preVal},#{postVal},#{investemntTime},#{companySortName},#{companyFullName},"
            + "#{controllerOwner},#{registeredCapital},#{chairman},#{address},#{projectChannel},current_timestamp)")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(FundTarget target);

    /**
     * 更新
     *
     * @param target
     * @return
     */
    @Update("<script> update fund_project set id = #{id}" + "<if test = 'industry!=null and \"\"!=industry'>,industry = #{industry}</if>"
            + "<if test = 'mainBusiness!=null and \"\"!=mainBusiness'>,main_business = #{mainBusiness}</if>" + "<if test = 'projectStep!=null'>,project_step = #{projectStep}</if>"
            + "<if test = 'memberA!=null and memberA.id!=null'>,member_a = #{memberA.id}</if>" + "<if test = 'memberB!=null and memberB.id!=null'>,member_b = #{memberB.id}</if>"
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
     *
     * @param id
     * @return
     */
    @Delete("delete from fund_project where id = #{id}")
    int delete(Long id);

    /**
     * 根据项目ID获取直投列表ID
     * 
     * @param id
     * @return
     */
    @Select("select f.id from fund_project f inner join factoring_project p on p.project_id = f.project_id  where p.id = #{id} limit 1")
    long getFundId(Long id);

    /**
     * 根据标的ID获取标的
     *
     * @param target
     * @return
     */
    @Select("select f.* from fund_project f inner join factoring_project p  on p.project_id = f.project_id where p.project_id = #{projectId} limit 1")
    FundTarget getByProjectId(FundTarget target);

    /**
     * 根据ID获取标的
     *
     * @param id
     * @return
     */
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "operator", property = "operator.id"),
                                      @Result(column = "member_a", property = "memberA.id"), @Result(column = "member_b", property = "memberB.id") })
    @Select("select * from fund_project where id = #{id} limit 1")
    FundTarget get(Long id);

    /**
     * @param query
     * @return
     */
    @Results(id = "queryMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "operator", property = "operator.id"),
                                        @Result(column = "fid", property = "detail.id"), @Result(column = "fpid", property = "detail.projectId"),
                                        @Result(column = "address", property = "detail.address"), @Result(column = "chairman", property = "detail.chairman"),
                                        @Result(column = "project_step", property = "detail.projectStep"),
                                        @Result(column = "company_full_name", property = "detail.companyFullName"),
                                        @Result(column = "company_sort_name", property = "detail.companySortName"),
                                        @Result(column = "controller_owner", property = "detail.controllerOwner"), @Result(column = "industry", property = "detail.industry"),
                                        @Result(column = "investemnt_time", property = "detail.investemntTime"), @Result(column = "location", property = "detail.location"),
                                        @Result(column = "main_business", property = "detail.mainBusiness"), @Result(column = "member_a", property = "detail.memberA.id"),
                                        @Result(column = "member_b", property = "detail.memberB.id"), @Result(column = "post_val", property = "detail.postVal"),
                                        @Result(column = "pre_val", property = "detail.preVal"), @Result(column = "project_channel", property = "detail.projectChannel"),
                                        @Result(column = "investment", property = "detail.investment"), @Result(column = "investment_part", property = "detail.investmentPart"),
                                        @Result(column = "registered_capital", property = "detail.registeredCapital"),
                                        @Result(column = "share_hoding_rate", property = "detail.shareHodingRate") })
    @Select("<script>" + "select p.*,f.id fid,f.project_id fpid,f.address,f.chairman,f.company_full_name,f.company_sort_name,"
            + "f.controller_owner,f.industry,f.investemnt_time,f.location,f.main_business,f.member_a,f.member_b,f.post_val,"
            + "f.pre_val,f.project_channel,f.project_step,f.registered_capital,f.share_hoding_rate,f.investment,f.investment_part "
            + "from factoring_project p inner join fund_project f on p.project_id = f.project_id " + "where 1 = 1"
            + "<if test = 'projectId!=null and \"\"!=projectId'>and p.project_id = #{projectId}</if>"
            + "<if test = 'projectName!=null and \"\"!=projectName'>and p.project_name = #{projectName}</if>"
            + "<if test = 'detail!=null and detail.projectStep!=null'>and f.project_step = #{detail.projectStep}</if>"
            + "<if test = 'stateDate!=null'>and to_days(investemnt_time) &gt;= to_days(#{stateDate})</if>"
            + "<if test = 'endDate!=null'>and to_days(investemnt_time) &lt;= #{endDate}</if>"
            + "<if test = 'detail!=null and detail.investmentPart!=null and \"\"!=detail.investmentPart'>and f.investment_part = #{detail.investmentPart}</if>" + "</script>")
    List<FundProject> queryFundTarget(BaseProjectQuery<FundTarget> query);

    /**
     *
     * @return
     */
    @Select("SELECT" + "    DATE_FORMAT( p.create_time, '%Y-%m' ) months," + "    count( 1 ) total ," + "    sum( d.investment ) investment " + "FROM factoring_project p"
            + "    INNER JOIN fund_project d ON p.project_id = d.project_id  WHERE d.project_step !='STOP' GROUP BY months")
    List<FundInfos> queryFundInfos();
}
