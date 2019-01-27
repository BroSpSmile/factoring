package com.smile.start.dao;

import com.smile.start.dto.RoleSearchDTO;
import com.smile.start.model.auth.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/23 19:28, Role.java
 * @since 1.8
 */
@Mapper
public interface RoleDao {

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @Insert("insert into auth_role_info (serial_no,role_code,role_name,role_desc,delete_flag,create_user,modify_user,gmt_create,gmt_modify) values (#{serialNo},#{roleCode},#{roleName},#{roleDesc},#{deleteFlag},#{createUser},#{modifyUser},#{gmtCreate},#{gmtModify})")
    long insert(Role role);

    /**
     * 更新角色
     * @param role
     * @return
     */
    @Update("update auth_role_info set role_name=#{roleName},role_desc=#{roleDesc},delete_flag=#{deleteFlag},modify_user=#{modifyUser},gmt_modify=#{gmtModify} where id=#{id}")
    int update(Role role);

    /**
     * 根据ID获取角色
     * @param id
     * @return
     */
    @Select("select * from auth_role_info where id = #{id}")
    Role get(Long id);

    /**
     * 根据角色编号查询角色
     * @param serialNo
     * @return
     */
    @Select("select * from auth_role_info where serial_no=#{serialNo}")
    List<Role> findBySerialNo(String serialNo);

    /**
     * 根据角色编号查询角色
     * @param roleCode
     * @return
     */
    @Select("select * from auth_role_info where role_code=#{roleCode}")
    Role findByRoleCode(String roleCode);

    /**
     * 查询全部角色
     *
     * @return
     */
    @Select("select * from auth_role_info where delete_flag = 0")
    List<Role> findAll();

    /**
     * 分页查询
     * @param roleSearchDTO
     * @return
     */
    @Select("<script>" + "select * from auth_role_info where 1=1 and delete_flag = 0"
        + "<if test = 'roleCode!=null'> and role_code = #{roleCode}</if>"
        + "<if test = 'roleName!=null'> and role_name = #{roleName}</if>"
        + "</script>")
    List<Role> findByParam(RoleSearchDTO roleSearchDTO);

    /**
     * 根据用户编号查询角色
     * @param userSerialNo
     * @return
     */
    @Select("select * from auth_role_info r, auth_user_role_info ri where r.serial_no = ri.role_serial_no and ri.user_serial_no=#{userSerialNo}")
    List<Role> findByUserSerialNo(String userSerialNo);
}
