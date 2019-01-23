package com.smile.start.service.impl;

import com.smile.start.dto.AuthPermissionInfoDTO;
import com.smile.start.entity.AuthPermissionInfoDO;
import com.smile.start.enums.DeleteFlagEnum;
import com.smile.start.mapper.PermissionInfoMapper;
import com.smile.start.repository.PermissionInfoRepository;
import com.smile.start.service.PermissionInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, PermissionInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class PermissionInfoServiceImpl implements PermissionInfoService {

    @Resource
    private PermissionInfoRepository permissionInfoRepository;

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
        final Optional<AuthPermissionInfoDO> optional = permissionInfoRepository.findById(id);
        return permissionInfoMapper.do2dto(optional.orElse(null));
    }

    /**
     * 新增权限信息
     *
     * @param authPermissionInfoDTO
     * @return
     */
    @Override
    public Long insert(AuthPermissionInfoDTO authPermissionInfoDTO) {
        final AuthPermissionInfoDO authPermissionInfoDO = permissionInfoMapper.dto2do(authPermissionInfoDTO);
        Date nowDate = new Date();
        authPermissionInfoDO.setGmtCreate(nowDate);
        authPermissionInfoDO.setGmtModify(nowDate);
        final AuthPermissionInfoDO save = permissionInfoRepository.save(authPermissionInfoDO);
        return save.getId();
    }

    /**
     * 更新权限信息
     *
     * @param authPermissionInfoDTO
     */
    @Override
    public void update(AuthPermissionInfoDTO authPermissionInfoDTO) {
        final AuthPermissionInfoDO authPermissionInfoDO = permissionInfoMapper.dto2do(authPermissionInfoDTO);
        authPermissionInfoDO.setGmtModify(new Date());
        permissionInfoRepository.saveAndFlush(authPermissionInfoDO);
    }

    /**
     * 删除权限信息
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        final AuthPermissionInfoDO authPermissionInfoDO = permissionInfoRepository.getOne(id);
        authPermissionInfoDO.setDeleteFlag(DeleteFlagEnum.DLETED.getValue());
        permissionInfoRepository.saveAndFlush(authPermissionInfoDO);
    }

    /**
     * 查询指定用户权限信息
     * @param userSerialNo
     * @return
     */
    @Override
    public List<AuthPermissionInfoDTO> findByUserSerialNo(String userSerialNo) {
        final List<AuthPermissionInfoDO> permissionList = permissionInfoRepository.findByUserSerialNo(userSerialNo);
        return permissionInfoMapper.doList2dtoList(permissionList);
    }
}
