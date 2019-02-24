package com.smile.start.dao;

import com.smile.start.dto.ContractInfoSearchDTO;
import com.smile.start.dto.SignListTemplateSearchDTO;
import com.smile.start.model.contract.ContractInfo;
import com.smile.start.model.contract.SignListTemplate;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 13:31, ContractInfoDao.java
 * @since 1.8
 */
@Mapper
public interface ContractInfoDao {

    /**
     *
     * @param id
     * @return
     */
    @Select("select * from contract_info where id = #{id}")
    ContractInfo get(Long id);

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

    /**
     * 分页查询
     * @param contractInfoSearchDTO
     * @return
     */
    @Select("<script>" + "select * from contract_info where 1=1 "
            + "<if test = 'contractCode!=null'> and contract_code like CONCAT('%',#{contractCode},'%')</if>"
            + "<if test = 'contractName!=null'> and contract_name like CONCAT('%',#{contractName},'%')</if>"
            + "<if test = 'projectMode!=null'> and project_mode = #{projectMode}</if>"
            + "<if test = 'contractTemplate!=null'> and contract_template = #{contractTemplate}</if>"
            + "<if test = 'status!=null'> and status = #{status}</if>"
            + "</script>")
    List<ContractInfo> findByParam(ContractInfoSearchDTO contractInfoSearchDTO);

    /**
     * 删除合同信息
     * @param id
     */
    @Delete("delete from contract_info where id = #{id}")
    void delete(Long id);
}
