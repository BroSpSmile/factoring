package com.smile.start.dao;

import com.smile.start.model.contract.ContractShareholderMeeting;
import org.apache.ibatis.annotations.*;

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
    @Insert("insert into contract_shareholder_meeting (serial_no,contract_serial_no,meeting_time,meeting_address,sp_company_name,attending_shareholders,shareholder_number,meeting_number,passing_rate,signature_date) values (#{serialNo},#{contractSerialNo},#{meetingTime},#{meetingAddress},#{spCompanyName},#{attendingShareholders},#{shareholderNumber},#{meetingNumber},#{passingRate},#{signatureDate})")
    long insert(ContractShareholderMeeting contractShareholderMeeting);

    /**
     * 更新股东会决议
     * @param contractShareholderMeeting
     * @return
     */
    @Update("update contract_shareholder_meeting set contract_serial_no=#{contractSerialNo},meeting_time=#{meetingTime},meeting_address=#{meetingAddress},sp_company_name=#{spCompanyName},attending_shareholders=#{attendingShareholders},shareholder_number=#{shareholderNumber},meeting_number=#{meetingNumber},passing_rate=#{passingRate},signature_date=#{signatureDate} where id=#{id}")
    int update(ContractShareholderMeeting contractShareholderMeeting);

    /**
     * 按合同业务流水查询股东会决议
     * @param contractSerialNo
     */
    @Select("select * from contract_shareholder_meeting where contract_serial_no = #{contractSerialNo}")
    ContractShareholderMeeting findByContractSerialNo(String contractSerialNo);

    /**
     * 按合同业务流水删除股东会决议
     * @param contractSerialNo
     */
    @Delete("delete from contract_shareholder_meeting where contract_serial_no = #{contractSerialNo}")
    void deleteByContractSerialNo(String contractSerialNo);
}
