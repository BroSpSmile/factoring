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

import com.smile.start.model.loan.Loan;
import com.smile.start.model.loan.LoanGroup;

/**
 * 放款Dao
 * @author smile.jing
 * @version $Id: LoanDao.java, v 0.1 Feb 27, 2019 3:58:47 PM smile.jing Exp $
 */
@Mapper
public interface LoanDao {
    /**
     * 插入
     * @param loan
     * @return
     */
    @Insert("insert loan (type,department,user,create_time,project_id,subscription_amount,payments,chinese_amount,accumulativeyments,unpaid,payment_purpose)"
            + "values(#{type},#{department},#{user},#{createTime},#{project.id},#{subscriptionAmount},#{payments},#{chineseAmount},#{accumulativeyments},#{unpaid},#{paymentPurpose})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(Loan loan);

    /**
     * 插入分组信息
     * @param group
     * @return
     */
    @Insert("insert loan_item (loan_id,payee_name,payee_bank_name,payee_account_no,payer_name,payer_bank_name,payer_account_no,payments)"
            + "values(#{loanId},#{payeeName},#{payeeBankName},#{payeeAccountNo},#{payerName},#{payerBankName},#{payerAccountNo},#{payments})")
    long insertItem(LoanGroup group);

    /**
     * 更新
     * @param loan
     * @return
     */
    @Update("update loan set type = #{type},department = #{department},user = #{user},create_time = #{createTime},project_id = #{project.id},"
            + "subscription_amount = #{subscriptionAmount},payments = #{payments},chinese_amount = #{chineseAmount},accumulativeyments = #{accumulativeyments} ,"
            + "unpaid = #{unpaid} ,payment_purpose = #{paymentPurpose} where id = #{id}")
    int update(Loan loan);

    /**
     * 
     * @param loan
     * @return
     */
    @Delete("delete from loan where id = #{id}")
    int delete(Long id);

    /**
     * 删除分组
     * @param loanId
     * @return
     */
    @Delete("delete from loan_item where loan_id = #{loanId}")
    int deleteItem(Long loanId);

    /**
     * get
     * @param id
     * @return
     */
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "project_id", property = "project.id") })
    @Select("select * from loan where id = #{id}")
    Loan get(Long id);

    /**
     * 
     * @param projectId
     * @return
     */
    @Results(id = "getByProjectMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "project_id", property = "project.id") })
    @Select("select * from loan where project_id = #{projectId} order by id desc limit 1")
    Loan getByProject(Long projectId);

    /**
     * query
     * @param loan
     * @return
     */
    @Results(id = "queryMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "project_id", property = "project.id") })
    @Select("<script>" + "select * from loan where 1=1 " + "<if test='department!=null and department!=\"\"'> and department = #{department}</if>"
            + "<if test='user!=null and user!=\"\"'> and user = #{user}</if>" + "<if test='project!=null and project.id!=null'> and project_id = #{project.id}</if>"
            + "<if test='payeeName!=null and payeeName!=\"\"'> and payee_name = #{payeeName}</if>"
            + "<if test='payerName!=null and payerName!=\"\"'> and payer_name = #{payerName}</if>" + "</script>")
    List<Loan> query(Loan loan);

    /**
     * 
     * @param loanId
     * @return
     */
    @Select("select * from loan_item where loan_id = #{loanId}")
    List<LoanGroup> getByLoan(Long loanId);
}
