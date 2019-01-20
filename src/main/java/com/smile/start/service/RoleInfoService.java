package com.smile.start.service;

import com.smile.start.dto.AuthRoleInfoDTO;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:40, RoleInfoService.java
 * @since 1.8
 */
public interface RoleInfoService {

    /**
     * 根据主键查询角色信息
     *
     * @param id
     * @return
     */
    AuthRoleInfoDTO get(Long id);

    /**
     * 新增角色信息
     *
     * @param authRoleInfoDTO
     * @return
     */
    Long insert(AuthRoleInfoDTO authRoleInfoDTO);

    /**
     * 更新角色信息
     *
     * @param authRoleInfoDTO
     */
    void update(AuthRoleInfoDTO authRoleInfoDTO);

    /**
     * 删除角色信息
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 查询指定用户角色信息
     * @param userSerialNo
     * @return
     */
    List<AuthRoleInfoDTO> findByUserSerialNo(String userSerialNo);
}
