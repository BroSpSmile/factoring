package com.smile.start.dao;

import com.smile.start.model.contract.ContractExtendInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:35, ContractExtendInfoDao.java
 * @since 1.8
 */
@Mapper
public interface ContractExtendInfoDao {

    /**
     * 新增合同扩展信息
     *
     * @param contractExtendInfo
     * @return
     */
    @Insert("insert into contract_extend_info (serial_no,contract_serial_no,contract_code,sp_company_name,sp_residence,sp_legal_person,sp_contact_address,sp_post_code,sp_telephone,sp_fax,obligor,sign_date,contract_name,receivable_assignee_money,receivable_assignee_money_upper,receivable_recovery_money,receivable_recovery_money_upper,fp_account_name,fp_bank_name,fp_account,sp_account_name,sp_bank_name,sp_account,ompulsory_rescission_date) values (#{serialNo},#{contractSerialNo},#{contractCode},#{spCompanyName},#{spResidence},#{spLegalPerson},#{spContactAddress},#{spPostCode},#{spTelephone},#{spFax},#{obligor},#{signDate},#{contractName},#{receivableAssigneeMoney},#{receivableAssigneeMoneyUpper},#{receivableRecoveryMoney},#{receivableRecoveryMoneyUpper},#{fpAccountName},#{fpBankName},#{fpAccount},#{spAccountName},#{spBankName},#{spAccount},#{ompulsoryRescissionDate})")
    long insert(ContractExtendInfo contractExtendInfo);

    /**
     * 更新合同扩展信息
     * @param contractExtendInfo
     * @return
     */
    @Update("update contract_extend_info set serial_no=#{serialNo},contract_serial_no=#{contractSerialNo},sp_company_name=#{spCompanyName},sp_residence=#{spResidence},sp_legal_person=#{spLegalPerson},sp_contact_address=#{spContactAddress},sp_post_code=#{spPostCode},sp_telephone=#{spTelephone},sp_fax=#{spFax},obligor=#{obligor},sign_date=#{signDate},contract_name=#{contractName},receivable_assignee_money=#{receivableAssigneeMoney},receivable_assignee_money_upper=#{receivableAssigneeMoneyUpper},receivable_recovery_money=#{receivableRecoveryMoney},receivable_recovery_money_upper=#{receivableRecoveryMoneyUpper},fp_account_name=#{fpAccountName},fp_bank_name=#{fpBankName},fp_account=#{fpAccount},sp_account_name=#{spAccountName},sp_bank_name=#{spBankName},sp_account=#{spAccount},ompulsory_rescission_date=#{ompulsoryRescissionDate} where id=#{id}")
    int update(ContractExtendInfo contractExtendInfo);

    /**
     * 按合同业务流水删除扩展信息
     * @param contractSerialNo
     */
    @Delete("delete from contract_extend_info where contract_serial_no = #{contractSerialNo}")
    void deleteByContractSerialNo(String contractSerialNo);

    /**
     * 按合同业务流水查询扩展信息
     * @param contractSerialNo
     */
    @Select("select * from contract_extend_info where contract_serial_no = #{contractSerialNo}")
    ContractExtendInfo findByContractSerialNo(String contractSerialNo);
}
