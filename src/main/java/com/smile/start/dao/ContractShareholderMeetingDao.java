package com.smile.start.dao;

import com.smile.start.model.contract.ContractFasa;
import com.smile.start.model.contract.ContractShareholderMeeting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Joseph
 * @version v1.0 2019/3/23 16:14, ContractShareholderMeetingDao.java
 * @since 1.8
 */
@Mapper
public interface ContractShareholderMeetingDao {

    /**
     * 新增股东会决议
     *
     * @param contractShareholderMeeting
     * @return
     */
    @Insert("insert into contract_shareholder_meeting (serial_no,contract_serial_no,metting_time,metting_address,sp_company_name,attending_shareholders,meeting_number,passing_rate,signature_date) values (#{serialNo},#{contractSerialNo},#{mettingTime},#{mettingAddress},#{spCompanyName},#{attendingShareholders},#{meetingNumber},#{passingRate},#{signatureDate})")
    long insert(ContractShareholderMeeting contractShareholderMeeting);

    /**
     * 更新股东会决议
     * @param contractShareholderMeeting
     * @return
     */
    @Update("update contract_shareholder_meeting set serial_no=#{serialNo},contract_serial_no=#{contractSerialNo},metting_time=#{mettingTime},metting_address=#{mettingAddress},sp_company_name=#{spCompanyName},attending_shareholders=#{attendingShareholders},meeting_number=#{meetingNumber},passing_rate=#{passingRate},signature_date=#{signatureDate} where id=#{id}")
    int update(ContractShareholderMeeting contractShareholderMeeting);

    /**
     * 按合同业务流水查询股东会决议
     * @param contractSerialNo
     */
    @Select("select * from contract_shareholder_meeting where contract_serial_no = #{contractSerialNo}")
    ContractShareholderMeeting findByContractSerialNo(String contractSerialNo);
}
