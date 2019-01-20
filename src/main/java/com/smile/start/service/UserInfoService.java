package com.smile.start.service;

import com.smile.start.dto.AuthUserInfoDTO;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:40, UserInfoService.java
 * @since 1.8
 */
public interface UserInfoService {

    /**
     * 根据主键查询用户信息
     * @param id
     * @return
     */
    AuthUserInfoDTO get(Long id);

    /**
     * 新增用户信息
     * @param authUserInfoDTO
     * @return
     */
    Long insert(AuthUserInfoDTO authUserInfoDTO);

    /**
     * 更新用户信息
     * @param authUserInfoDTO
     */
    void update(AuthUserInfoDTO authUserInfoDTO);

    /**
     * 删除用户信息
     * @param id
     */
    void delete(Long id);
}
