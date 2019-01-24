package com.smile.start.dao;

import com.smile.start.model.auth.Token;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Joseph
 * @version v1.0 2019/1/24 19:21, TokenDao.java
 * @since 1.8
 */
@Mapper
public interface TokenDao {

    /**
     * 新增token
     *
     * @param token
     * @return
     */
    @Insert("insert into auth_login_token (serial_no,mobile,openid,token,token_expire,gmt_create) values (#{serialNo},#{mobile},#{openid},#{token},#{tokenExpire},#{gmtCreate})")
    long insert(Token token);

    /**
     * 根据token编号查询
     * @param token
     * @return
     */
    @Select("select * from auth_login_token where token=#{token}")
    Token findByToken(String token);
}
