package com.smile.start.dao;

import com.smile.start.dto.PermissionSearchDTO;
import com.smile.start.model.auth.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/23 19:34, PermissionDao.java
 * @since 1.8
 */
@Mapper
public interface PermissionDao {

    /**
     * 新增权限
     *
     * @param permission
     * @return
     */
    @Insert("insert into auth_permission_info (serial_no,permission_code,permission_name,permission_type,remark,parent_serial_no,url,delete_flag,create_user,modify_user,gmt_create,gmt_modify) values (#{serialNo},#{permissionCode},#{permissionName},#{permissionType},#{remark},#{parentSerialNo},#{url},#{deleteFlag},#{createUser},#{modifyUser},#{gmtCreate},#{gmtModify})")
    long insert(Permission permission);

    /**
     * 更新权限
     * @param permission
     * @return
     */
    @Update("update auth_permission_info set permission_name=#{permissionName},permission_type=#{permissionType},remark=#{remark},parent_serial_no=#{parentSerialNo},url=#{url},delete_flag=#{deleteFlag},modify_user=#{modifyUser},gmt_modify=#{gmtModify} where id=#{id}")
    int update(Permission permission);

    /**
     * 根据ID获取权限
     * @param id
     * @return
     */
    @Select("select * from auth_permission_info where id = #{id}")
    Permission get(Long id);

    /**
     * 根据权限编号查询权限
     * @param serialNo
     * @return
     */
    @Select("select * from auth_permission_info where serial_no=#{serialNo}")
    List<Permission> findBySerialNo(String serialNo);

    /**
     * 查询全部权限
     *
     * @return
     */
    @Select("select * from auth_permission_info")
    List<Permission> findAll();

    /**
     * 分页查询
     * @param permissionSearchDTO
     * @return
     */
    @Select("<script>"
        + "select * from auth_permission_info where 1=1 and delete_flag = 0"
        + "<if test = 'permissionCode!=null'> and permission_code = #{permissionCode}</if>"
        + "<if test = 'permissionName!=null'> and permission_name = #{permissionName}</if>"
        + "</script>")
    List<Permission> findByParam(PermissionSearchDTO permissionSearchDTO);

    /**
     * 根据权限编号查询权限
     * @param userSerialNo
     * @return
     */
    @Select("select * from auth_permission_info p," +
        "auth_role_permission_info rp," +
        "auth_user_info u," +
        "auth_role_info r," +
        "auth_user_role_info ur " +
        "where p.serial_no = rp.permission_serial_no " +
        "and rp.role_serial_no = r.serial_no " +
        "and r.serial_no = ur.role_serial_no " +
        "and ur.user_serial_no = u.serial_no " +
        "and u.serial_no = #{serialNo}")
    List<Permission> findByUserSerialNo(String userSerialNo);

    /**
     * 根据父级权限编号查询权限
     * @param parentSerialNo
     * @return
     */
    @Select("select * from auth_permission_info where parent_serial_no=#{parentSerialNo}")
    List<Permission> findByParentSerialNo(String parentSerialNo);
}
