package com.smile.start.dao.factoring;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.smile.start.model.contract.ContractSignList;

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
    @Insert("insert into contract_sign_list (serial_no,project_id,contract_serial_no,sign_list_name,status,is_required,category,copies,page_count,is_original_copy,remark,filing_status,sort) values (#{serialNo},#{projectId},#{contractSerialNo},#{signListName},#{status},#{isRequired},#{category},#{copies},#{pageCount},#{isOriginalCopy},#{remark},#{filingStatus},#{sort})")
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
    void deleteByContractSerialNo(String contractSerialNo);

    /**
     * 按合同业务流水查询签署清单
     * @param contractSerialNo
     */
    @Select("select * from contract_sign_list where contract_serial_no = #{contractSerialNo}")
    List<ContractSignList> findByContractSerialNo(String contractSerialNo);

    /**
     * 根据项目查询签署清单
     * @param projectId
     * @return
     */
    @Select("select * from contract_sign_list where project_id = #{projectId} ORDER BY category,sort")
    List<ContractSignList> findByProjectId(Long projectId);

    /**
     * 更新签署清单归档信息
     * @param contractSignList
     * @return
     */
    @Update("update contract_sign_list set filing_status = #{filingStatus},sign_list_name=#{signListName},category=#{category},is_original_copy=#{isOriginalCopy},copies=#{copies},page_count=#{pageCount},remark=#{remark},sort=#{sort} where serial_no = #{serialNo}")
    long updateFilingStatus(ContractSignList contractSignList);

    /**
     * 指定合同流水、签署清单名称查询
     * @param contractSerialNo
     * @param signListName
     * @return
     */
    @Select("select * from contract_sign_list where contract_serial_no = #{contractSerialNo} and sign_list_name = #{signListName}")
    ContractSignList findByContractSerialNoAndName(String contractSerialNo, String signListName);
}
