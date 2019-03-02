package com.smile.start.service;

import com.github.pagehelper.PageInfo;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.UserSearchDTO;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.login.LoginUser;

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
     * 查询所有用户信息
     * @return
     */
    PageInfo<AuthUserInfoDTO> findAll(PageRequest<UserSearchDTO> page);

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

    /**
     * 验证token是否有效
     * @param token
     * @return
     */
    boolean validateToken(String token);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    User getUserByToken(String token);

    /**
     * 根据ID获取用户信息
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 根据token获取登录用户信息，包括角色、权限信息
     * @param token
     * @return
     */
    LoginUser getLoginUserByToken(String token);

    /**
     * 根据用户编号查询用户
     * @param serialNo
     * @return
     */
    AuthUserInfoDTO findBySerialNo(String serialNo);
}
