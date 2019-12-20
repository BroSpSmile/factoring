package com.smile.start.dao.factoring;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.smile.start.model.contract.ContractInfo;
import com.smile.start.model.dto.ContractAuditSearchDTO;
import com.smile.start.model.dto.ContractInfoSearchDTO;

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
     *
     * @param projectId
     * @return
     */
    @Select("select * from contract_info where project_id = #{projectId}")
    ContractInfo getByProjectId(Long projectId);

    /**
     *
     * @param serialNo
     * @return
     */
    @Select("select * from contract_info where serial_no = #{serialNo}")
    ContractInfo findBySerialNo(String serialNo);

    /**
     * 新增合同基本信息
     *
     * @param contractInfo
     * @return
     */
    @Insert("insert into contract_info (project_id,serial_no,project_mode,contract_template,status,delete_flag,seal_status,factoring_contract,confirmation_letter,registration_agreement,financial_agreement,shareholder_resolution,create_user,modify_user,gmt_create,gmt_modify) values (#{projectId},#{serialNo},#{projectMode},#{contractTemplate},#{status},#{deleteFlag},#{sealStatus},#{factoringContract},#{confirmationLetter},#{registrationAgreement},#{financialAgreement},#{shareholderResolution},#{createUser},#{modifyUser},#{gmtCreate},#{gmtModify})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(ContractInfo contractInfo);

    /**
     * 更新合同基本信息
     * @param contractInfo
     * @return
     */
    @Update("update contract_info set project_id=#{projectId},project_mode=#{projectMode},contract_template=#{contractTemplate},status=#{status},delete_flag=#{deleteFlag},seal_status=#{sealStatus},seal_finish_time=#{sealFinishTime},factoring_contract=#{factoringContract},confirmation_letter=#{confirmationLetter},registration_agreement=#{registrationAgreement},financial_agreement=#{financialAgreement},shareholder_resolution=#{shareholderResolution},modify_user=#{modifyUser},gmt_modify=#{gmtModify} where id=#{id}")
    int update(ContractInfo contractInfo);

    /**
     * 分页查询
     * @param contractInfoSearchDTO
     * @return
     */
    @Select("<script>" + "select ci.*,fp.project_id project_code,fp.project_name from contract_info ci,factoring_project fp where ci.project_id = fp.id and delete_flag = 0 "
            + "<if test = 'contractCode!=null'> and ci.contract_code like CONCAT('%',#{contractCode},'%')</if>"
            + "<if test = 'contractName!=null'> and ci.contract_name like CONCAT('%',#{contractName},'%')</if>"
            + "<if test = 'projectMode!=null'> and ci.project_mode = #{projectMode}</if>"
            + "<if test = 'contractTemplate!=null'> and ci.contract_template = #{contractTemplate}</if>" + "<if test = 'status!=null'> and ci.status = #{status}</if>"
            + "<if test = 'projectId!=null'> and ci.project_id = #{projectId}</if>" + "</script>")
    List<ContractInfo> findByParam(ContractInfoSearchDTO contractInfoSearchDTO);

    /**
     * 删除合同信息
     * @param id
     */
    @Delete("delete from contract_info where id = #{id}")
    void delete(Long id);

    /**
     * 此方法只供合同审核列表用，根据当前登录用户查询待审核的合同列表
     * @param contractAuditSearchDTO
     * @return
     */
    @Select("<script>" + "select distinct ci.*,fp.project_name from auth_user_role_info uri,flow_status fs,contract_info ci,factoring_project fp "
            + "where uri.user_serial_no = #{userSerialNo} " + "and ci.project_id = fp.id " + "and fs.role_serial_no = uri.role_serial_no " + "and ci.status = fs.flow_status "
            + "<if test = 'contractCode!=null'> and ci.contract_code like CONCAT('%',#{contractCode},'%')</if>"
            + "<if test = 'contractName!=null'> and ci.contract_name like CONCAT('%',#{contractName},'%')</if>"
            + "<if test = 'projectMode!=null'> and ci.project_mode = #{projectMode}</if>" + "</script>")
    List<ContractInfo> findAuditList(ContractAuditSearchDTO contractAuditSearchDTO);
}
