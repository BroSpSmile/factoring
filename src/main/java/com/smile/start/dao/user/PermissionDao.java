package com.smile.start.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.smile.start.model.auth.Permission;
import com.smile.start.model.dto.PermissionSearchDTO;

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
    @Insert("insert into auth_permission_info (serial_no,permission_code,permission_name,permission_type,remark,parent_serial_no,url,delete_flag,sort,create_user,modify_user,gmt_create,gmt_modify) values (#{serialNo},#{permissionCode},#{permissionName},#{permissionType},#{remark},#{parentSerialNo},#{url},#{deleteFlag},#{sort},#{createUser},#{modifyUser},#{gmtCreate},#{gmtModify})")
    long insert(Permission permission);

    /**
     * 更新权限
     * @param permission
     * @return
     */
    @Update("update auth_permission_info set permission_code=#{permissionCode},permission_name=#{permissionName},permission_type=#{permissionType},remark=#{remark},parent_serial_no=#{parentSerialNo},url=#{url},delete_flag=#{deleteFlag},sort=#{sort},modify_user=#{modifyUser},gmt_modify=#{gmtModify} where id=#{id}")
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
    Permission findBySerialNo(String serialNo);

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
    @Select("<script>" + "select * from auth_permission_info where 1=1 and delete_flag = 0" + "<if test = 'permissionCode!=null'> and permission_code = #{permissionCode}</if>"
            + "<if test = 'permissionName!=null'> and permission_name = #{permissionName}</if>" + "<if test = 'permissionType!=null'> and permission_type = #{permissionType}</if>"
            + "</script>")
    List<Permission> findByParam(PermissionSearchDTO permissionSearchDTO);

    /**
     * 根据用户编号查询权限
     * @param userSerialNo
     * @return
     */
    @Select("select * from auth_permission_info p," + "auth_role_permission_info rp," + "auth_user_info u," + "auth_role_info r," + "auth_user_role_info ur "
            + "where p.serial_no = rp.permission_serial_no " + "and rp.role_serial_no = r.serial_no " + "and r.serial_no = ur.role_serial_no "
            + "and ur.user_serial_no = u.serial_no " + "and u.serial_no = #{serialNo}")
    List<Permission> findByUserSerialNo(String userSerialNo);

    /**
     * 根据用户编号查询所有顶级权限
     * @param userSerialNo
     * @return
     */
    @Select("select * from auth_permission_info p," + "auth_role_permission_info rp," + "auth_user_info u," + "auth_role_info r," + "auth_user_role_info ur "
            + "where p.serial_no = rp.permission_serial_no " + "and rp.role_serial_no = r.serial_no " + "and r.serial_no = ur.role_serial_no "
            + "and ur.user_serial_no = u.serial_no " + "and p.parent_serial_no = '' " + "and u.serial_no = #{serialNo}")
    List<Permission> findParentByUserSerialNo(String userSerialNo);

    /**
     * 查询最顶级菜单
     * @param userSerialNo
     * @return
     */
    @Select("SELECT DISTINCT p.*  FROM auth_permission_info p " + "inner join auth_role_permission_info rp on p.serial_no = rp.permission_serial_no "
            + "inner join auth_user_role_info ur on ur.role_serial_no = rp.role_serial_no "
            + "where ur.user_serial_no = #{userSerialNo} and p.parent_serial_no ='' order by p.permission_code")
    List<Permission> findTopPermission(String userSerialNo);

    /**
     * 根据父级权限编号查询权限
     * @param parentSerialNo
     * @return
     */
    @Select("select * from auth_permission_info where parent_serial_no=#{parentSerialNo} order by sort")
    List<Permission> findByParentSerialNo(String parentSerialNo);
}
