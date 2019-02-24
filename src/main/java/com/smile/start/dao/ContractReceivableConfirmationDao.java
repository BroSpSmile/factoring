package com.smile.start.dao;

import com.smile.start.model.contract.ContractReceivableConfirmation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:42, ContractReceivableConfirmation.java
 * @since 1.8
 */
@Mapper
public interface ContractReceivableConfirmationDao {

    /**
     * 新增应收账款转让确认函
     *
     * @param contractReceivableConfirmation
     * @return
     */
    @Insert("insert into contract_receivable_confirmation (serial_no,contract_serial_no,assignor,sign_date,obligor,business_contract_name,receivable_assignee_money,receivable_recovery_money,receivable_expiry_date,contract_receivable,contract_receivable_upper,assignor_abligor_receivable,assignor_abligor_receivable_upper,receivable_assignee_money_paid,receivable_assignee_money_paid_upper,assignor_commit_date,assignee_account_name,assignee_bank_name,assignee_account,confirmation_address) values (#{serialNo},#{contractSerialNo},#{assignor},#{signDate},#{obligor},#{businessContractName},#{receivableAssigneeMoney},#{receivableRecoveryMoney},#{receivableExpiryDate},#{contractReceivable},#{contractReceivableUpper},#{assignorAbligorReceivable},#{assignorAbligorReceivableUpper},#{receivableAssigneeMoneyPaid},#{receivableAssigneeMoneyPaidUpper},#{assignorCommitDate},#{assigneeAccountName},#{assigneeBankName},#{assigneeAccount},#{confirmationAddress})")
    long insert(ContractReceivableConfirmation contractReceivableConfirmation);

    /**
     * 更新应收账款转让确认函
     * @param contractReceivableConfirmation
     * @return
     */
    @Update("update contract_receivable_confirmation set serial_no=#{serialNo},contract_serial_no=#{contractSerialNo},assignor=#{assignor},sign_date=#{signDate},obligor=#{obligor},business_contract_name=#{businessContractName},receivable_assignee_money=#{receivableAssigneeMoney},receivable_recovery_money=#{receivableRecoveryMoney},receivable_expiry_date=#{receivableExpiryDate},contract_receivable=#{contractReceivable},contract_receivable_upper=#{contractReceivableUpper},assignor_abligor_receivable=#{assignorAbligorReceivable},assignor_abligor_receivable_upper=#{assignorAbligorReceivableUpper},receivable_assignee_money_paid=#{receivableAssigneeMoneyPaid},receivable_assignee_money_paid_upper=#{receivableAssigneeMoneyPaidUpper},assignor_commit_date=#{assignorCommitDate},assignee_account_name=#{assigneeAccountName},assignee_bank_name=#{assigneeBankName},assignee_account=#{assigneeAccount},confirmation_address=#{confirmationAddress} where id=#{id}")
    int update(ContractReceivableConfirmation contractReceivableConfirmation);

    /**
     * 按合同业务流水删除确认函
     * @param contractSerialNo
     */
    @Delete("delete from contract_receivable_confirmation where contract_serial_no = #{contractSerialNo}")
    void deleteByContractSerialNo(String contractSerialNo);
}
