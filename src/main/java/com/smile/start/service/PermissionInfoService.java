package com.smile.start.service;

import com.github.pagehelper.PageInfo;
import com.smile.start.dto.AuthPermissionInfoDTO;
import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.dto.PermissionSearchDTO;
import com.smile.start.dto.RoleSearchDTO;
import com.smile.start.model.base.PageRequest;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:40, PermissionInfoService.java
 * @since 1.8
 */
public interface PermissionInfoService {

    /**
     * 根据主键查询权限信息
     *
     * @param id
     * @return
     */
    AuthPermissionInfoDTO get(Long id);

    /**
     * 查询所有权限信息
     * @return
     */
    PageInfo<AuthPermissionInfoDTO> findAll(PageRequest<PermissionSearchDTO> page);

    /**
     * 新增权限信息
     *
     * @param authPermissionInfoDTO
     * @return
     */
    Long insert(AuthPermissionInfoDTO authPermissionInfoDTO);

    /**
     * 更新权限信息
     *
     * @param authPermissionInfoDTO
     */
    void update(AuthPermissionInfoDTO authPermissionInfoDTO);

    /**
     * 删除权限信息
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 查询指定用户权限信息
     * @param userSerialNo
     * @return
     */
    List<AuthPermissionInfoDTO> findByUserSerialNo(String userSerialNo);
}
