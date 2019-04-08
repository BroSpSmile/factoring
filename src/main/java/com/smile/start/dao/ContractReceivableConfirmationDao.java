package com.smile.start.dao;

import com.smile.start.model.contract.ContractReceivableConfirmation;
import org.apache.ibatis.annotations.*;

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
    @Insert("insert into contract_receivable_confirmation (serial_no,contract_serial_no,confirmation_code,assignor,sign_date,obligor,business_contract_name,unpaid_receivable_assignee_money,unpaid_receivable_assignee_money_upper,unpaid_receivable_assignee_money_type,receivable_assignee_money,receivable_assignee_money_upper,receivable_assignee_money_type,receivable_recovery_money,receivable_recovery_money_upper,receivable_expiry_date,contract_receivable,contract_receivable_upper,assignor_abligor_receivable,assignor_abligor_receivable_upper,unpaid_assignor_abligor_receivable,unpaid_assignor_abligor_receivable_upper,receivable_assignee_money_paid,receivable_assignee_money_paid_upper,assignor_commit_date,assignee_account_name,assignee_bank_name,assignee_account,confirmation_address,assignee_signature_date,assignor_company_name,assignor_signature_date,obligor_company_name,obligor_signature_date,name_of_subject,invoice_money,invoice_money_type) values (#{serialNo},#{contractSerialNo},#{confirmationCode},#{assignor},#{signDate},#{obligor},#{businessContractName},#{unpaidReceivableAssigneeMoney},#{unpaidReceivableAssigneeMoneyUpper},#{unpaidReceivableAssigneeMoneyType},#{receivableAssigneeMoney},#{receivableAssigneeMoneyUpper},#{receivableAssigneeMoneyType},#{receivableRecoveryMoney},#{receivableRecoveryMoneyUpper},#{receivableExpiryDate},#{contractReceivable},#{contractReceivableUpper},#{assignorAbligorReceivable},#{assignorAbligorReceivableUpper},#{unpaidAssignorAbligorReceivable},#{unpaidAssignorAbligorReceivableUpper},#{receivableAssigneeMoneyPaid},#{receivableAssigneeMoneyPaidUpper},#{assignorCommitDate},#{assigneeAccountName},#{assigneeBankName},#{assigneeAccount},#{confirmationAddress},#{assigneeSignatureDate},#{assignorCompanyName},#{assignorSignatureDate},#{obligorCompanyName},#{obligorSignatureDate},#{nameOfSubject},#{invoiceMoney},#{invoiceMoneyType})")
    long insert(ContractReceivableConfirmation contractReceivableConfirmation);

    /**
     * 更新应收账款转让确认函
     * @param contractReceivableConfirmation
     * @return
     */
    @Update("update contract_receivable_confirmation set contract_serial_no=#{contractSerialNo},confirmation_code=#{confirmationCode},assignor=#{assignor},sign_date=#{signDate},obligor=#{obligor},business_contract_name=#{businessContractName},unpaid_receivable_assignee_money=#{unpaidReceivableAssigneeMoney},unpaid_receivable_assignee_money_upper=#{unpaidReceivableAssigneeMoneyUpper},unpaid_receivable_assignee_money_type=#{unpaidReceivableAssigneeMoneyType},receivable_assignee_money=#{receivableAssigneeMoney},receivable_assignee_money_upper=#{receivableAssigneeMoneyUpper},receivable_assignee_money_type=#{receivableAssigneeMoneyType},receivable_recovery_money=#{receivableRecoveryMoney},receivable_recovery_money_upper=#{receivableRecoveryMoneyUpper},receivable_expiry_date=#{receivableExpiryDate},contract_receivable=#{contractReceivable},contract_receivable_upper=#{contractReceivableUpper},assignor_abligor_receivable=#{assignorAbligorReceivable},assignor_abligor_receivable_upper=#{assignorAbligorReceivableUpper},unpaid_assignor_abligor_receivable=#{unpaidAssignorAbligorReceivable},unpaid_assignor_abligor_receivable_upper=#{unpaidAssignorAbligorReceivableUpper},receivable_assignee_money_paid=#{receivableAssigneeMoneyPaid},receivable_assignee_money_paid_upper=#{receivableAssigneeMoneyPaidUpper},assignor_commit_date=#{assignorCommitDate},assignee_account_name=#{assigneeAccountName},assignee_bank_name=#{assigneeBankName},assignee_account=#{assigneeAccount},confirmation_address=#{confirmationAddress},assignee_signature_date=#{assigneeSignatureDate},assignor_company_name=#{assignorCompanyName},assignor_signature_date=#{assignorSignatureDate},obligor_company_name=#{obligorCompanyName},obligor_signature_date=#{obligorSignatureDate},name_of_subject=#{nameOfSubject},invoice_money=#{invoiceMoney},invoice_money_type=#{invoiceMoneyType} where id=#{id}")
    int update(ContractReceivableConfirmation contractReceivableConfirmation);

    /**
     * 按合同业务流水删除确认函
     * @param contractSerialNo
     */
    @Delete("delete from contract_receivable_confirmation where contract_serial_no = #{contractSerialNo}")
    void deleteByContractSerialNo(String contractSerialNo);

    /**
     * 按合同业务流水查询确认函
     * @param contractSerialNo
     */
    @Select("select * from contract_receivable_confirmation where contract_serial_no = #{contractSerialNo}")
    ContractReceivableConfirmation findByContractSerialNo(String contractSerialNo);
}
