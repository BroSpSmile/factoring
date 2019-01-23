package com.smile.start.service.impl;

import com.smile.start.commons.Asserts;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.RoleDao;
import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.enums.DeleteFlagEnum;
import com.smile.start.mapper.RoleInfoMapper;
import com.smile.start.model.auth.Role;
import com.smile.start.service.RoleInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, RoleInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    @Resource
    private RoleDao roleDao;

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
        return roleInfoMapper.do2dto(roleDao.get(id));
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
        final Role oldRole = roleDao.findByRoleCode(authRoleInfoDTO.getRoleCode());
        Asserts.notTrue((oldRole == null), "指定角色编号已经存在");
        final Role role = roleInfoMapper.dto2do(authRoleInfoDTO);
        Date nowDate = new Date();
        role.setGmtCreate(nowDate);
        role.setGmtModify(nowDate);
        role.setSerialNo(SerialNoGenerator.generateSerialNo("R", 7));
        return roleDao.insert(role);
    }

    /**
     * 更新角色信息
     *
     * @param authRoleInfoDTO
     */
    @Override
    public void update(AuthRoleInfoDTO authRoleInfoDTO) {
        final Role role = roleInfoMapper.dto2do(authRoleInfoDTO);
        role.setGmtModify(new Date());
        roleDao.update(role);
    }

    /**
     * 删除角色信息
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        final Role role = roleDao.get(id);
        role.setDeleteFlag(DeleteFlagEnum.DLETED.getValue());
        roleDao.update(role);
    }

    /**
     * 查询指定用户角色信息
     * @param userSerialNo
     * @return
     */
    @Override
    public List<AuthRoleInfoDTO> findByUserSerialNo(String userSerialNo) {
        final List<Role> userRoleList = roleDao.findByUserSerialNo(userSerialNo);
        return roleInfoMapper.doList2dtoList(userRoleList);
    }
}
