/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.dao.wechat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.smile.start.model.wechat.AccessToken;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AccessTokenDao
 * @date Date : 2019年12月16日 20:13
 */
@Mapper
public interface AccessTokenDao {

    /**
     * 更新token
     * @param token 
     * @return
     */
    @Update("update access_token set access_token = #{accessToken},expires = #{expires} where id = #{id}")
    int update(AccessToken token);

    /**
     * 获取全部token
     * @return
     */
    @Select("select * from access_token")
    List<AccessToken> getAllToken();

    /**
     * 根据agentId获取token
     * @param agentId
     * @return
     */
    @Select("select * from access_token where agentId = #{agentId} limit 1")
    AccessToken getToken(long agentId);
}
