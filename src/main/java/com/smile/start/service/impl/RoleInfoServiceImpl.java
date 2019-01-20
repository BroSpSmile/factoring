package com.smile.start.service.impl;

import com.smile.start.commons.Asserts;
import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.entity.AuthRoleInfoDO;
import com.smile.start.enums.DeleteFlagEnum;
import com.smile.start.mapper.RoleInfoMapper;
import com.smile.start.repository.RoleInfoRepository;
import com.smile.start.service.RoleInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, RoleInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    @Resource
    private RoleInfoRepository roleInfoRepository;

    @Resource
    private RoleInfoMapper roleInfoMapper;

    /**
     * 根据主键查询角色信息
     *
     * @param id
     * @return
     */
    @Override
    public AuthRoleInfoDTO get(Long id) {
        final Optional<AuthRoleInfoDO> optional = roleInfoRepository.findById(id);
        return roleInfoMapper.do2dto(optional.orElse(null));
    }

    /**
     * 新增角色信息
     *
     * @param authRoleInfoDTO
     * @return
     */
    @Override
    public Long insert(AuthRoleInfoDTO authRoleInfoDTO) {
        Asserts.notEmpty(authRoleInfoDTO.getRoleCode(), "角色编号不能为空");
        final AuthRoleInfoDO oldRole = roleInfoRepository.findByRoleCode(authRoleInfoDTO.getRoleCode());
        Asserts.notTrue((oldRole == null), "指定角色编号已经存在");
        final AuthRoleInfoDO authRoleInfoDO = roleInfoMapper.dto2do(authRoleInfoDTO);
        Date nowDate = new Date();
        authRoleInfoDO.setGmtCreate(nowDate);
        authRoleInfoDO.setGmtModify(nowDate);
        final AuthRoleInfoDO save = roleInfoRepository.save(authRoleInfoDO);
        return save.getId();
    }

    /**
     * 更新角色信息
     *
     * @param authRoleInfoDTO
     */
    @Override
    public void update(AuthRoleInfoDTO authRoleInfoDTO) {
        final AuthRoleInfoDO authRoleInfoDO = roleInfoMapper.dto2do(authRoleInfoDTO);
        authRoleInfoDO.setGmtModify(new Date());
        roleInfoRepository.saveAndFlush(authRoleInfoDO);
    }

    /**
     * 删除角色信息
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        final AuthRoleInfoDO authRoleInfoDO = roleInfoRepository.getOne(id);
        authRoleInfoDO.setDeleteFlag(DeleteFlagEnum.DLETED.getValue());
        roleInfoRepository.saveAndFlush(authRoleInfoDO);
    }

    /**
     * 查询指定用户角色信息
     * @param userSerialNo
     * @return
     */
    @Override
    public List<AuthRoleInfoDTO> findByUserSerialNo(String userSerialNo) {
        final List<AuthRoleInfoDO> userRoleList = roleInfoRepository.findByUserSerialNo(userSerialNo);
        return roleInfoMapper.doList2dtoList(userRoleList);
    }
}
