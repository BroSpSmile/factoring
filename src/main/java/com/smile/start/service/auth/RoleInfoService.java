package com.smile.start.service.auth;

import com.github.pagehelper.PageInfo;
import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.dto.RoleSearchDTO;
import com.smile.start.model.auth.PermissionSetting;
import com.smile.start.model.base.PageRequest;

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
     * 根据角色编号获取角色信息
     * @param serialNo
     * @return
     */
    AuthRoleInfoDTO getBySerialNo(String serialNo);

    /**
     * 查询所有角色信息
     * @return
     */
    PageInfo<AuthRoleInfoDTO> findAll(PageRequest<RoleSearchDTO> page);

    /**
     * 查询所有角色信息
     * @return
     */
    List<AuthRoleInfoDTO> findAll();

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

    /**
     * 保存权限信息
     * @param permissionSetting
     */
    void savePermission(PermissionSetting permissionSetting);
}
