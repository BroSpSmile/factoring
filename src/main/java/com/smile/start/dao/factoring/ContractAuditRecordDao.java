package com.smile.start.dao.factoring;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.smile.start.model.contract.ContractAuditRecord;

/**
 * @author Joseph
 * @version v1.0 2019/2/25 16:51, ContractAuditRecordDao.java
 * @since 1.8
 */
@Mapper
public interface ContractAuditRecordDao {

    /**
     * 新增审核记录
     *
     * @param contractAuditRecord
     * @return
     */
    @Insert("insert into contract_audit_record (serial_no,contract_serial_no,operation_status,operation_type,operation_user,operation_time,remark) values (#{serialNo},#{contractSerialNo},#{operationStatus},#{operationType},#{operationUser},#{operationTime},#{remark})")
    long insert(ContractAuditRecord contractAuditRecord);

    /**
     * 按合同业务流水查询审核历史
     * @param contractSerialNo
     */
    @Select("select * from contract_audit_record where contract_serial_no = #{contractSerialNo} order by operation_time desc")
    List<ContractAuditRecord> findByContractSerialNo(String contractSerialNo);
}
