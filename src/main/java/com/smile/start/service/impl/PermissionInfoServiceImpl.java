package com.smile.start.service.impl;

import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.PermissionDao;
import com.smile.start.dto.AuthPermissionInfoDTO;
import com.smile.start.enums.DeleteFlagEnum;
import com.smile.start.mapper.PermissionInfoMapper;
import com.smile.start.model.auth.Permission;
import com.smile.start.service.PermissionInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, PermissionInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class PermissionInfoServiceImpl implements PermissionInfoService {

    @Resource
    private PermissionDao permissionDao;

    @Resource
    private PermissionInfoMapper permissionInfoMapper;

    /**
     * 根据主键查询权限信息
     *
     * @param id
     * @return
     */
    @Override
    public AuthPermissionInfoDTO get(Long id) {
        return permissionInfoMapper.do2dto(permissionDao.get(id));
    }

    /**
     * 新增权限信息
     *
     * @param authPermissionInfoDTO
     * @return
     */
    @Override
    public Long insert(AuthPermissionInfoDTO authPermissionInfoDTO) {
        final Permission permission = permissionInfoMapper.dto2do(authPermissionInfoDTO);
        Date nowDate = new Date();
        permission.setGmtCreate(nowDate);
        permission.setGmtModify(nowDate);
        permission.setSerialNo(SerialNoGenerator.generateSerialNo("P", 7));
        return permissionDao.insert(permission);
    }

    /**
     * 更新权限信息
     *
     * @param authPermissionInfoDTO
     */
    @Override
    public void update(AuthPermissionInfoDTO authPermissionInfoDTO) {
        final Permission permission = permissionInfoMapper.dto2do(authPermissionInfoDTO);
        permission.setGmtModify(new Date());
        permissionDao.update(permission);
    }

    /**
     * 删除权限信息
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        final Permission permission = permissionDao.get(id);
        permission.setDeleteFlag(DeleteFlagEnum.DLETED.getValue());
        permissionDao.update(permission);
    }

    /**
     * 查询指定用户权限信息
     * @param userSerialNo
     * @return
     */
    @Override
    public List<AuthPermissionInfoDTO> findByUserSerialNo(String userSerialNo) {
        final List<Permission> permissionList = permissionDao.findByUserSerialNo(userSerialNo);
        return permissionInfoMapper.doList2dtoList(permissionList);
    }
}
