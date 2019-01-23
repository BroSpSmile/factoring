package com.smile.start.service.impl;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.Asserts;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.UserSearchDTO;
import com.smile.start.entity.AuthUserInfoDO;
import com.smile.start.enums.DeleteFlagEnum;
import com.smile.start.mapper.UserInfoMapper;
import com.smile.start.repository.UserInfoRepository;
import com.smile.start.service.PermissionInfoService;
import com.smile.start.service.RoleInfoService;
import com.smile.start.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:37, UserInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private RoleInfoService roleInfoService;

    @Resource
    private PermissionInfoService permissionInfoService;

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 根据主键查询用户信息
     * @param id
     * @return
     */
    @Override
    public AuthUserInfoDTO get(Long id) {
        final Optional<AuthUserInfoDO> optional = userInfoRepository.findById(id);
        AuthUserInfoDTO authUserInfoDTO = userInfoMapper.do2dto(optional.orElse(null));
        Asserts.notNull(authUserInfoDTO, "用户主键：" + id + " 没有找到记录");

        //组装角色、权限信息
        authUserInfoDTO.setRoleList(roleInfoService.findByUserSerialNo(authUserInfoDTO.getSerialNo()));
        authUserInfoDTO.setPermissionList(permissionInfoService.findByUserSerialNo(authUserInfoDTO.getSerialNo()));
        return authUserInfoDTO;
    }

    /**
     * 查询所有用户信息
     * @return
     */
    @Override
    public PageInfo<AuthUserInfoDTO> findAll(UserSearchDTO userSearchDTO) {
        final PageInfo<AuthUserInfoDTO> result = new PageInfo<>();
        final List<AuthUserInfoDO> userList = userInfoRepository.findAll();
        result.setTotal(userList.size());
        result.setPageSize(10);
        result.setList(userInfoMapper.doList2dtoList(userList));
        return result;
    }

    /**
     * 新增用户信息
     * @param authUserInfoDTO
     * @return
     */
    @Override
    public Long insert(AuthUserInfoDTO authUserInfoDTO) {
        final AuthUserInfoDO authUserInfoDO = userInfoMapper.dto2do(authUserInfoDTO);
        Date nowDate = new Date();
        authUserInfoDO.setGmtCreate(nowDate);
        authUserInfoDO.setGmtModify(nowDate);
        final AuthUserInfoDO save = userInfoRepository.save(authUserInfoDO);
        return save.getId();
    }

    /**
     * 更新用户信息
     * @param authUserInfoDTO
     */
    @Override
    public void update(AuthUserInfoDTO authUserInfoDTO) {
        final AuthUserInfoDO authUserInfoDO = userInfoMapper.dto2do(authUserInfoDTO);
        authUserInfoDO.setGmtModify(new Date());
        userInfoRepository.saveAndFlush(authUserInfoDO);
    }

    /**
     * 删除用户信息
     * @param id
     */
    @Override
    public void delete(Long id) {
        final AuthUserInfoDO authUserInfoDO = userInfoRepository.getOne(id);
        authUserInfoDO.setDeleteFlag(DeleteFlagEnum.DLETED.getValue());
        userInfoRepository.saveAndFlush(authUserInfoDO);
    }
}
