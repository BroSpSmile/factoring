package com.smile.start.service.auth.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.Asserts;
import com.smile.start.commons.LoginHandler;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.user.RoleDao;
import com.smile.start.mapper.RoleInfoMapper;
import com.smile.start.model.auth.PermissionSetting;
import com.smile.start.model.auth.Role;
import com.smile.start.model.auth.RolePermission;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.dto.AuthRoleInfoDTO;
import com.smile.start.model.dto.RoleSearchDTO;
import com.smile.start.model.enums.DeleteFlagEnum;
import com.smile.start.model.login.LoginUser;
import com.smile.start.service.auth.RoleInfoService;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, RoleInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    @Resource
    private RoleDao        roleDao;

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
     * @see com.smile.start.service.auth.RoleInfoService#getBySerialNo(java.lang.String)
     */
    @Override
    public AuthRoleInfoDTO getBySerialNo(String serialNo) {
        return roleInfoMapper.do2dto(roleDao.findBySerialNo(serialNo));
    }

    /**
     * 查询所有角色信息
     * @return
     */
    @Override
    public PageInfo<AuthRoleInfoDTO> findAll(PageRequest<RoleSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "id desc");
        final List<Role> roleList = roleDao.findByParam(pageRequest.getCondition());
        PageInfo<AuthRoleInfoDTO> pageInfo = new PageInfo<>(roleInfoMapper.doList2dtoList(roleList));
        Page<Role> page = (Page<Role>) roleList;
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageNum(pageRequest.getPageNum());
        pageInfo.setPageSize(pageRequest.getPageSize());
        return pageInfo;
    }

    /**
     * 查询所有角色信息
     * @return
     */
    @Override
    public List<AuthRoleInfoDTO> findAll() {
        return roleInfoMapper.doList2dtoList(roleDao.findAll());
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
        LoginUser loginUser = LoginHandler.getLoginUser();
        role.setCreateUser(loginUser.getSerialNo());
        role.setSerialNo(SerialNoGenerator.generateSerialNo("R", 7));
        role.setDeleteFlag(DeleteFlagEnum.UNDELETED.getValue());
        return roleDao.insert(role);
    }

    /**
     * 更新角色信息
     *
     * @param authRoleInfoDTO
     */
    @Override
    public void update(AuthRoleInfoDTO authRoleInfoDTO) {
        final Role oldRole = roleDao.findByRoleCode(authRoleInfoDTO.getRoleCode());
        if (oldRole != null) {
            Asserts.notTrue(authRoleInfoDTO.getId().equals(oldRole.getId()), "指定角色编号已经存在");
        }
        final Role role = roleInfoMapper.dto2do(authRoleInfoDTO);
        role.setGmtModify(new Date());
        LoginUser loginUser = LoginHandler.getLoginUser();
        role.setModifyUser(loginUser.getSerialNo());
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

    /**
     * 保存权限信息
     * @param permissionSetting
     */
    @Override
    public void savePermission(PermissionSetting permissionSetting) {
        roleDao.deletePermission(permissionSetting.getRoleSerialNo());
        //处理权限信息
        if (!CollectionUtils.isEmpty(permissionSetting.getCheckedPermissionList())) {
            permissionSetting.getCheckedPermissionList().forEach(e -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setSerialNo(SerialNoGenerator.generateSerialNo("U", 7));
                rolePermission.setRoleSerialNo(permissionSetting.getRoleSerialNo());
                rolePermission.setPermissionSerialNo(e);
                roleDao.insertPermission(rolePermission);
            });
        }
    }
}
