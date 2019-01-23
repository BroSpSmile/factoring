package com.smile.start.dao;

import com.smile.start.dto.UserSearchDTO;
import com.smile.start.model.auth.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/23 19:17, UserDao.java
 * @since 1.8
 */
@Mapper
public interface UserDao {

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Insert("insert into auth_user_info (serial_no,username,mobile,openid,email,status,delete_flag,passwd,create_user,modify_user,gmt_create,gmt_modify) values (#{serialNo},#{username},#{mobile},#{openid},#{email},#{status},#{deleteFlag},#{passwd},#{createUser},#{modifyUser},#{gmtCreate},#{gmtModify})")
    long insert(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Update("update auth_user_info set mobile = #{mobile},email=#{email},status=#{status},delete_flag=#{deleteFlag},passwd=#{passwd},modify_user=#{modifyUser},gmt_modify=#{gmtModify} where id=#{id}")
    int update(User user);

    /**
     * 根据ID获取用户
     * @param id
     * @return
     */
    @Select("select * from auth_user_info where id = #{id}")
    User get(Long id);

    /**
     * 根据用户编号查询用户
     * @param serialNo
     * @return
     */
    @Select("select * from auth_user_info where serial_no=#{serialNo}")
    List<User> findBySerialNo(String serialNo);

    /**
     * 查询全部用户
     *
     * @return
     */
    @Select("select * from auth_user_info")
    List<User> findAll();

    /**
     * 分页查询
     * @param userSearchDTO
     * @return
     */
    @Select("<script>" + "select * from auth_user_info where 1=1 " + "<if test = 'username!=null'> and username = #{username}</if>"
            + "<if test = 'mobile!=null'> and mobile = #{mobile}</if>" + "<if test = 'email!=null'> and email = #{email}</if>"
            + "<if test = 'status!=null'> and status = #{status}</if>" + "<if test = 'deleteFlag!=null'> and delete_flag = #{deleteFlag}</if>" + "</script>")
    List<User> findByParam(UserSearchDTO userSearchDTO);
}
