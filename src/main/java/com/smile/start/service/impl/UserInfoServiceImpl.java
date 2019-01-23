package com.smile.start.service.impl;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.Asserts;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.UserDao;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.UserSearchDTO;
import com.smile.start.mapper.UserInfoMapper;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.DeleteFlagEnum;
import com.smile.start.service.PermissionInfoService;
import com.smile.start.service.RoleInfoService;
import com.smile.start.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:37, UserInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserDao               userDao;

    @Resource
    private RoleInfoService       roleInfoService;

    @Resource
    private PermissionInfoService permissionInfoService;

    @Resource
    private UserInfoMapper        userInfoMapper;

    /**
     * 根据主键查询用户信息
     * @param id
     * @return
     */
    @Override
    public AuthUserInfoDTO get(Long id) {
        final User user = userDao.get(id);
        AuthUserInfoDTO authUserInfoDTO = userInfoMapper.do2dto(user);
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
    public PageInfo<AuthUserInfoDTO> findAll(PageRequest<UserSearchDTO> page) {
        final PageInfo<AuthUserInfoDTO> result = new PageInfo<>();
        final List<User> userList = userDao.findByParam(page.getCondition());
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
        final User user = userInfoMapper.dto2do(authUserInfoDTO);
        Date nowDate = new Date();
        user.setSerialNo(SerialNoGenerator.generateSerialNo("U", 7));
        user.setGmtCreate(nowDate);
        user.setGmtModify(nowDate);
        return userDao.insert(user);
    }

    /**
     * 更新用户信息
     * @param authUserInfoDTO
     */
    @Override
    public void update(AuthUserInfoDTO authUserInfoDTO) {
        final User user = userInfoMapper.dto2do(authUserInfoDTO);
        user.setGmtModify(new Date());
        userDao.update(user);
    }

    /**
     * 删除用户信息
     * @param id
     */
    @Override
    public void delete(Long id) {
        final User user = userDao.get(id);
        user.setDeleteFlag(DeleteFlagEnum.DLETED.getValue());
        userDao.update(user);
    }
}
