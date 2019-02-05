package com.smile.start.dao;

import com.smile.start.dto.OrganizationalSearchDTO;
import com.smile.start.model.auth.Organizational;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/29 16:46, OrganizationalDao.java
 * @since 1.8
 */
@Mapper
public interface OrganizationalDao {

    /**
     * 新增组织架构
     *
     * @param organizational
     * @return
     */
    @Insert("insert into auth_organizational (serial_no,organizational_name,parent_serial_no,delete_flag,remark,create_user,modify_user,gmt_create,gmt_modify) values (#{serialNo},#{organizationalName},#{parentSerialNo},#{deleteFlag},#{remark},#{createUser},#{modifyUser},#{gmtCreate},#{gmtModify})")
    long insert(Organizational organizational);

    /**
     * 更新组织架构
     * @param organizational
     * @return
     */
    @Update("update auth_organizational set organizational_name=#{organizationalName},parent_serial_no=#{parentSerialNo},delete_flag=#{deleteFlag},remark=#{remark},modify_user=#{modifyUser},gmt_modify=#{gmtModify} where id=#{id}")
    int update(Organizational organizational);

    /**
     * 根据ID获取组织架构
     * @param id
     * @return
     */
    @Select("select * from auth_organizational where id = #{id}")
    Organizational get(Long id);

    /**
     * 根据编号查询组织架构
     * @param serialNo
     * @return
     */
    @Select("select * from auth_organizational where serial_no=#{serialNo}")
    Organizational findBySerialNo(String serialNo);

    /**
     * 查询全部组织架构
     *
     * @return
     */
    @Select("select * from auth_organizational")
    List<Organizational> findAll();

    /**
     * 分页查询
     * @param organizationalSearchDTO
     * @return
     */
    @Select("<script>" + "select * from auth_organizational where 1=1 and delete_flag = 0"
            + "<if test = 'organizationalName!=null'> and organizational_name = #{organizationalName}</if>"
            + "</script>")
    List<Organizational> findByParam(OrganizationalSearchDTO organizationalSearchDTO);

    /**
     * 根据用户编号查询组织
     * @param userSerialNo
     * @return
     */
    @Select("select * from auth_organizational o, auth_user_organizational uo where o.serial_no = uo.organizational_serial_no and uo.user_serial_no=#{userSerialNo}")
    List<Organizational> findByUserSerialNo(String userSerialNo);

}
