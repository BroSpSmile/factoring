package com.smile.start.dao;

import com.smile.start.model.dto.BankInfoSearchDTO;
import com.smile.start.model.common.BankInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/3/9 14:17, BankInfoDao.java
 * @since 1.8
 */
@Mapper
public interface BankInfoDao {

    /**
     * 新增银行信息
     *
     * @param bankInfo
     * @return
     */
    @Insert("insert into bank_info (serial_no,organizational_serial_no,bank_full_name,bank_short_name,bank_account,amount) "
            + "values (#{serialNo},#{organizationalSerialNo},#{bankFullName},#{bankShortName},#{bankAccount},#{amount})")
    long insert(BankInfo bankInfo);

    /**
     * 更新银行信息
     * @param bankInfo
     * @return
     */
    @Update("update bank_info set organizational_serial_no=#{organizationalSerialNo},bank_full_name=#{bankFullName},bank_short_name=#{bankShortName},"
            + "bank_account=#{bankAccount},amount=#{amount} where id=#{id}")
    int update(BankInfo bankInfo);

    /**
     * 根据ID获取银行信息
     * @param id
     * @return
     */
    @Select("select * from bank_info where id = #{id}")
    BankInfo get(Long id);

    /**
     * 查询全部银行信息
     *
     * @return
     */
    @Select("select * from bank_info")
    List<BankInfo> findAll();

    /**
     * 分页查询
     * @param bankInfoSearchDTO
     * @return
     */
    @Select("<script>" + "select bi.*,ao.organizational_name from bank_info bi,auth_organizational ao where bi.organizational_serial_no=ao.serial_no "
            + "<if test = 'bankFullName!=null'> and bi.bank_full_name like CONCAT('%',#{bankFullName},'%')</if>"
            + "<if test = 'bankShortName!=null'> and bi.bank_short_name like CONCAT('%',#{bankShortName},'%')</if>"
            + "<if test = 'bankAccount!=null'> and bi.bank_account like CONCAT('%',#{bankAccount},'%')</if>"
            + " and bi.organizational_serial_no "
            + "in <foreach item='item' index='index' collection='organizationalList' open='(' separator=',' close=')'> #{item} </foreach> "
            + "</script>")
    List<BankInfo> findByParam(BankInfoSearchDTO bankInfoSearchDTO);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from bank_info where id = #{id}")
    void delete(Long id);
}
