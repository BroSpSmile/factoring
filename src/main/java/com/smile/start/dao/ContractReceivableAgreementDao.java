package com.smile.start.dao;

import com.smile.start.model.contract.ContractReceivableAgreement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:39, ContractReceivableAgreementDao.java
 * @since 1.8
 */
@Mapper
public interface ContractReceivableAgreementDao {

    /**
     * 新增应收账款转让登记协议
     *
     * @param contractReceivableAgreement
     * @return
     */
    @Insert("insert into contract_receivable_agreement (serial_no,contract_serial_no,sp_name,sp_residence,sp_legal_person,sp_contact_address,sp_post_code,sp_telephone,sp_fax,contract_sign_date) values (#{serialNo},#{contractSerialNo},#{spName},#{spResidence},#{spLegalPerson},#{spContactAddress},#{spPostCode},#{spTelephone},#{spFax},#{contractSignDate})")
    long insert(ContractReceivableAgreement contractReceivableAgreement);

    /**
     * 更新应收账款转让登记协议
     * @param contractReceivableAgreement
     * @return
     */
    @Update("update contract_receivable_agreement set serial_no=#{serialNo},contract_serial_no=#{contractSerialNo},sp_name=#{spName},sp_residence=#{spResidence},sp_legal_person=#{spLegalPerson},sp_contact_address=#{spContactAddress},sp_post_code=#{spPostCode},sp_telephone=#{spTelephone},sp_fax=#{spFax},contract_sign_date=#{contractSignDate} where id=#{id}")
    int update(ContractReceivableAgreement contractReceivableAgreement);
}
