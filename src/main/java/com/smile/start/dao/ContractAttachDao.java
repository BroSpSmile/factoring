package com.smile.start.dao;

import com.smile.start.model.contract.ContractAttach;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/24 12:45, ContractAttachDao.java
 * @since 1.8
 */
@Mapper
public interface ContractAttachDao {
    /**
     * 新增合同附件
     *
     * @param contractAttach
     * @return
     */
    @Insert("insert into contract_attach (serial_no,contract_serial_no,attach_name,file_id,attach_type) values (#{serialNo},#{contractSerialNo},#{attachName},#{fileId},#{attachType})")
    long insert(ContractAttach contractAttach);

    /**
     * 按合同业务流水查询附件
     * @param contractSerialNo
     */
    @Select("select * from contract_attach where contract_serial_no = #{contractSerialNo}")
    List<ContractAttach> findByContractSerialNo(String contractSerialNo);

    /**
     * 按合同业务流水删除附件
     * @param contractSerialNo
     * @return
     */
    @Delete("delete from contract_attach where contract_serial_no = #{contractSerialNo}")
    void deleteByContractSerialNo(String contractSerialNo);
}
