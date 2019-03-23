package com.smile.start.dao;

import com.smile.start.model.contract.ContractFasa;
import org.apache.ibatis.annotations.*;

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
    @Insert("insert into contract_fasa (serial_no,contract_serial_no,fasa_code,fp_company_name,fp_residence,fp_legal_person,fp_post_code,fp_telephone,fp_fax,sign_address,sing_date,advisory_service_money,advisory_service_money_upper,advisory_service_money_appointment,sp_bank_name,sp_account,expiry_date_month) values (#{serialNo},#{contractSerialNo},#{fasaCode},#{fpCompanyName},#{fpResidence},#{fpLegalPerson},#{fpPostCode},#{fpTelephone},#{fpFax},#{signAddress},#{singDate},#{advisoryServiceMoney},#{advisoryServiceMoneyUpper},#{advisoryServiceMoneyAppointment},#{spBankName},#{spAccount},#{expiryDateMonth})")
    long insert(ContractFasa contractFasa);

    /**
     * 更新财务顾问服务协议
     * @param contractFasa
     * @return
     */
    @Update("update contract_fasa set serial_no=#{serialNo},contract_serial_no=#{contractSerialNo},fasa_code=#{fasaCode},fp_company_name=#{fpCompanyName},fp_residence=#{fpResidence},fp_legal_person=#{fpLegalPerson},fp_post_code=#{fpPostCode},fp_telephone=#{fpTelephone},fp_fax=#{fpFax},sign_address=#{signAddress},sing_date=#{singDate},advisory_service_money=#{advisoryServiceMoney},advisory_service_money_upper=#{advisoryServiceMoneyUpper},advisory_service_money_appointment=#{advisoryServiceMoneyAppointment},sp_bank_name=#{spBankName},sp_account=#{spAccount},expiry_date_month=#{expiryDateMonth} where id=#{id}")
    int update(ContractFasa contractFasa);

    /**
     * 按合同业务流水查询财务顾问服务协议
     * @param contractSerialNo
     */
    @Select("select * from contract_fasa where contract_serial_no = #{contractSerialNo}")
    ContractFasa findByContractSerialNo(String contractSerialNo);
}
