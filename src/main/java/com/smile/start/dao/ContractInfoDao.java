package com.smile.start.dao;

import com.smile.start.model.contract.ContractInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 13:31, ContractInfoDao.java
 * @since 1.8
 */
@Mapper
public interface ContractInfoDao {

    /**
     * 新增合同基本信息
     *
     * @param contractInfo
     * @return
     */
    @Insert("insert into contract_info (serial_no,contract_code,contract_name,project_mode,contract_template,status,create_user,modify_user,gmt_create,gmt_modify) values (#{serialNo},#{contractCode},#{contractName},#{projectMode},#{contractTemplate},#{status},#{createUser},#{modifyUser},#{gmtCreate},#{gmtModify})")
    long insert(ContractInfo contractInfo);

    /**
     * 更新合同基本信息
     * @param contractInfo
     * @return
     */
    @Update("update contract_info set serial_no=#{serialNo},contract_code=#{contractCode},contract_name=#{contractName},project_mode=#{projectMode},contract_template=#{contractTemplate},status=#{status},modify_user=#{modifyUser},gmt_modify=#{gmtModify} where id=#{id}")
    int update(ContractInfo contractInfo);
}
