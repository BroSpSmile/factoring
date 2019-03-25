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

import com.smile.start.model.project.FactoringDetail;
import com.smile.start.model.project.FactoringsInfo;

/**
 * 保理项目明细Dao
 * @author smile.jing
 * @version $Id: FactoringDetailDao.java, v 0.1 Feb 24, 2019 7:32:41 PM smile.jing Exp $
 */
@Mapper
public interface FactoringDetailDao {

    /**
     * 新增项目明细
     * @param detail
     * @return
     */
    @Insert("insert into factoring_detail " + "(project_id,creditor,debtor,sign_date,base_contract,assignee,receivable,"
            + "drop_amount,duration,remittance_day,total_factoring_fee,return_rate,remark) "
            + "values(#{project.id},#{creditor},#{debtor},#{signDate},#{baseContract},#{assignee},#{receivable},#{dropAmount},"
            + "#{duration},#{remittanceDay},#{totalFactoringFee},#{returnRate},#{remark})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(FactoringDetail detail);

    /**
     * 根据项目ID查询保理项目明细
     * @param projectId
     * @return
     */
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "project_id", property = "project.id") })
    @Select("select * from factoring_detail where project_id = #{projectId}")
    FactoringDetail getByProject(Long projectId);

    /**
     * 更新
     * @param detail
     * @return
     */
    @Update("<script> update factoring_detail set id = #{id}" + "<if test = 'creditor!=null and \"\"!=creditor'>,creditor = #{creditor}</if>"
            + "<if test = 'debtor!=null and \"\"!=debtor'>,debtor = #{debtor}</if>" + "<if test = 'signDate!=null'>,sign_date = #{signDate}</if>"
            + "<if test = 'baseContract!=null'>,base_contract = #{baseContract}</if>" + "<if test = 'assignee!=null'>,assignee = #{assignee}</if>"
            + "<if test = 'receivable!=null'>,receivable = #{receivable}</if>" + "<if test = 'dropAmount!=null'>,drop_amount = #{dropAmount}</if>"
            + "<if test = 'duration!=0'>,duration = #{duration}</if>" + "<if test = 'remittanceDay!=null'>,remittance_day = #{remittanceDay}</if>"
            + "<if test = 'totalFactoringFee!=null'>,total_factoring_fee = #{totalFactoringFee}</if>" + "<if test = 'returnRate!=null'>,return_rate = #{returnRate}</if>"
            + "<if test = 'remark!=null and remark!=\"\"'>,remark = #{remark}</if>" + "where id = #{id}" + "</script>")
    int update(FactoringDetail detail);

    /**
     * 
     * @return
     */
    @Select("SELECT" + "    DATE_FORMAT( p.create_time, '%Y-%m' ) months," + "    count( 1 ) total ," + "    sum( d.assignee ) investment,"
            + "    sum( d.total_factoring_fee ) profit " + "FROM factoring_project p" + "    INNER JOIN factoring_detail d ON p.id = d.project_id " + "GROUP BY months")
    List<FactoringsInfo> factoringsInfos();
}
