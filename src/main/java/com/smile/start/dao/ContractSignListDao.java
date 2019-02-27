package com.smile.start.dao;

import com.smile.start.model.contract.ContractSignList;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:44, ContractSignListDao.java
 * @since 1.8
 */
@Mapper
public interface ContractSignListDao {

    /**
     * 新增合同签署清单
     *
     * @param contractSignList
     * @return
     */
    @Insert("insert into contract_sign_list (serial_no,contract_serial_no,sign_list_name,status,is_required) values (#{serialNo},#{contractSerialNo},#{signListName},#{status},#{isRequired})")
    long insert(ContractSignList contractSignList);

    /**
     * 更新合同签署清单
     * @param contractSignList
     */
    @Update("update contract_sign_list set serial_no=#{serialNo},contract_serial_no=#{contractSerialNo},sign_list_name=#{signListName},status=#{status},is_required=#{isRequired} where id=#{id}")
    void update(ContractSignList contractSignList);

    /**
     * 按合同业务流水删除签署清单
     * @param contractSerialNo
     * @return
     */
    @Delete("delete from contract_sign_list where contract_serial_no = #{contractSerialNo}")
    int deleteByContractSerialNo(String contractSerialNo);

    /**
     * 按合同业务流水查询确认函
     * @param contractSerialNo
     */
    @Select("select * from contract_sign_list where contract_serial_no = #{contractSerialNo}")
    List<ContractSignList> findByContractSerialNo(String contractSerialNo);
}
