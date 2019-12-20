package com.smile.start.dao.factoring;

import org.apache.ibatis.annotations.*;

import com.smile.start.model.contract.ContractFasa;

/**
 * @author Joseph
 * @version v1.0 2019/3/23 15:18, ContractFasaDao.java
 * @since 1.8
 */
@Mapper
public interface ContractFasaDao {

    /**
     * 新增财务顾问服务协议
     *
     * @param contractFasa
     * @return
     */
    @Insert("insert into contract_fasa (serial_no,contract_serial_no,fasa_code,fp_company_name,fp_residence,fp_legal_person,fp_post_code,fp_telephone,fp_fax,sign_address,sign_date,advisory_service_money,advisory_service_money_upper,advisory_service_money_appointment,sp_bank_name,sp_account,expiry_date_month,fp_signature_date,sp_signature_date) values (#{serialNo},#{contractSerialNo},#{fasaCode},#{fpCompanyName},#{fpResidence},#{fpLegalPerson},#{fpPostCode},#{fpTelephone},#{fpFax},#{signAddress},#{signDate},#{advisoryServiceMoney},#{advisoryServiceMoneyUpper},#{advisoryServiceMoneyAppointment},#{spBankName},#{spAccount},#{expiryDateMonth},#{fpSignatureDate},#{spSignatureDate})")
    long insert(ContractFasa contractFasa);

    /**
     * 更新财务顾问服务协议
     * @param contractFasa
     * @return
     */
    @Update("update contract_fasa set contract_serial_no=#{contractSerialNo},fp_company_name=#{fpCompanyName},fp_residence=#{fpResidence},fp_legal_person=#{fpLegalPerson},fp_post_code=#{fpPostCode},fp_telephone=#{fpTelephone},fp_fax=#{fpFax},sign_address=#{signAddress},sign_date=#{signDate},advisory_service_money=#{advisoryServiceMoney},advisory_service_money_upper=#{advisoryServiceMoneyUpper},advisory_service_money_appointment=#{advisoryServiceMoneyAppointment},sp_bank_name=#{spBankName},sp_account=#{spAccount},expiry_date_month=#{expiryDateMonth},fp_signature_date=#{fpSignatureDate},sp_signature_date=#{spSignatureDate} where id=#{id}")
    int update(ContractFasa contractFasa);

    /**
     * 按合同业务流水查询财务顾问服务协议
     * @param contractSerialNo
     */
    @Select("select * from contract_fasa where contract_serial_no = #{contractSerialNo}")
    ContractFasa findByContractSerialNo(String contractSerialNo);

    /**
     * 按合同业务流水删除财务顾问服务协议
     * @param contractSerialNo
     */
    @Delete("delete from contract_fasa where contract_serial_no = #{contractSerialNo}")
    void deleteByContractSerialNo(String contractSerialNo);
}
