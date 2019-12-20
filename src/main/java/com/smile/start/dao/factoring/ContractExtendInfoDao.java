package com.smile.start.dao.factoring;

import org.apache.ibatis.annotations.*;

import com.smile.start.model.contract.ContractExtendInfo;

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
    @Insert("insert into contract_extend_info (serial_no,contract_serial_no,contract_code,sp_company_name,sp_residence,sp_legal_person,sp_contact_address,sp_post_code,sp_telephone,sp_fax,obligor,sign_date,base_sign_date,contract_name,receivable_money,receivable_money_upper,receivable_money_type,receivable_money_additional,obligor_enjoy_money,obligor_enjoy_money_upper,receivable_assignee_money,receivable_assignee_money_upper,receivable_recovery_money,receivable_recovery_money_upper,receivable_recovery_money_type,receivable_recovery_money_paytime,fp_account_name,fp_bank_name,fp_account,sp_account_name,sp_bank_name,sp_account,compulsory_rescission_date,fp_signature_date,sp_signature_date,billing_start_date,interest_rate) values (#{serialNo},#{contractSerialNo},#{contractCode},#{spCompanyName},#{spResidence},#{spLegalPerson},#{spContactAddress},#{spPostCode},#{spTelephone},#{spFax},#{obligor},#{signDate},#{baseSignDate},#{contractName},#{receivableMoney},#{receivableMoneyUpper},#{receivableMoneyType},#{receivableMoneyAdditional},#{obligorEnjoyMoney},#{obligorEnjoyMoneyUpper},#{receivableAssigneeMoney},#{receivableAssigneeMoneyUpper},#{receivableRecoveryMoney},#{receivableRecoveryMoneyUpper},#{receivableRecoveryMoneyType},#{receivableRecoveryMoneyPaytime},#{fpAccountName},#{fpBankName},#{fpAccount},#{spAccountName},#{spBankName},#{spAccount},#{compulsoryRescissionDate},#{fpSignatureDate},#{spSignatureDate},#{billingStartDate},#{interestRate})")
    long insert(ContractExtendInfo contractExtendInfo);

    /**
     * 更新合同扩展信息
     * @param contractExtendInfo
     * @return
     */
    @Update("update contract_extend_info set contract_serial_no=#{contractSerialNo},sp_company_name=#{spCompanyName},sp_residence=#{spResidence},sp_legal_person=#{spLegalPerson},sp_contact_address=#{spContactAddress},sp_post_code=#{spPostCode},sp_telephone=#{spTelephone},sp_fax=#{spFax},obligor=#{obligor},sign_date=#{signDate},base_sign_date=#{baseSignDate},contract_name=#{contractName},receivable_money=#{receivableMoney},receivable_money_upper=#{receivableMoneyUpper},receivable_money_type=#{receivableMoneyType},obligor_enjoy_money=#{obligorEnjoyMoney},obligor_enjoy_money_upper=#{obligorEnjoyMoneyUpper},receivable_assignee_money=#{receivableAssigneeMoney},receivable_assignee_money_upper=#{receivableAssigneeMoneyUpper},receivable_recovery_money=#{receivableRecoveryMoney},receivable_recovery_money_upper=#{receivableRecoveryMoneyUpper},receivable_recovery_money_type=#{receivableRecoveryMoneyType},receivable_recovery_money_paytime=#{receivableRecoveryMoneyPaytime},fp_account_name=#{fpAccountName},fp_bank_name=#{fpBankName},fp_account=#{fpAccount},sp_account_name=#{spAccountName},sp_bank_name=#{spBankName},sp_account=#{spAccount},compulsory_rescission_date=#{compulsoryRescissionDate},fp_signature_date=#{fpSignatureDate},sp_signature_date=#{spSignatureDate},billing_start_date=#{billingStartDate},interest_rate=#{interestRate},receivable_money_additional=#{receivableMoneyAdditional} where id=#{id}")
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
