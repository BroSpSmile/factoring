package com.smile.start.service.auth.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.commons.Asserts;
import com.smile.start.commons.LoginHandler;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.user.TokenDao;
import com.smile.start.dao.user.UserDao;
import com.smile.start.exception.ValidateException;
import com.smile.start.mapper.RoleInfoMapper;
import com.smile.start.mapper.UserInfoMapper;
import com.smile.start.model.auth.Token;
import com.smile.start.model.auth.User;
import com.smile.start.model.auth.UserOrganizational;
import com.smile.start.model.auth.UserRole;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.dto.*;
import com.smile.start.model.enums.DeleteFlagEnum;
import com.smile.start.model.enums.StatusEnum;
import com.smile.start.model.login.LoginUser;
import com.smile.start.model.login.LoginUserOrganizational;
import com.smile.start.model.login.LoginUserPermission;
import com.smile.start.model.login.LoginUserRole;
import com.smile.start.service.auth.OrganizationalService;
import com.smile.start.service.auth.PermissionInfoService;
import com.smile.start.service.auth.RoleInfoService;
import com.smile.start.service.auth.UserInfoService;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:37, UserInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private Logger                logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Resource
    private UserDao               userDao;

    @Resource
    private TokenDao              tokenDao;

    @Resource
    private RoleInfoService       roleInfoService;

    @Resource
    private PermissionInfoService permissionInfoService;

    @Resource
    private OrganizationalService organizationalService;

    @Resource
    private UserInfoMapper        userInfoMapper;

    /** roleInfoMapper */
    @Resource
    private RoleInfoMapper        roleInfoMapper;

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
    public PageInfo<AuthUserInfoDTO> findAll(PageRequest<UserSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "id desc");
        final List<User> userList = userDao.findByParam(pageRequest.getCondition());
        List<AuthUserInfoDTO> authUserInfoDTOS = userInfoMapper.doList2dtoList(userList);
        authUserInfoDTOS.forEach(e -> {
            loadRoles(e);
            loadOrganizational(e);
        });
        PageInfo<AuthUserInfoDTO> pageInfo = new PageInfo<>(authUserInfoDTOS);
        Page<User> page = (Page<User>) userList;
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageNum(pageRequest.getPageNum());
        pageInfo.setPageSize(pageRequest.getPageSize());
        return pageInfo;
    }

    /**
     * 加载角色
     * @param user
     */
    private void loadRoles(AuthUserInfoDTO user) {
        List<AuthRoleInfoDTO> roles = roleInfoService.findByUserSerialNo(user.getSerialNo());
        user.setRoleList(roles);
        List<String> roleStrings = Lists.newArrayList();
        roles.forEach(e -> roleStrings.add(e.getSerialNo()));
        user.setCheckedRoleList(roleStrings);
    }

    /**
     * 加载组织
     */
    private void loadOrganizational(AuthUserInfoDTO user) {
        List<OrganizationalDTO> organizationals = organizationalService.findByUserSerialNo(user.getSerialNo());
        if (null != organizationals && !organizationals.isEmpty()) {
            user.setFirstOrganizationName(organizationals.get(0).getOrganizationalName());
        }
        List<String> organizationalStrings = Lists.newArrayList();
        organizationals.forEach(e -> organizationalStrings.add(e.getSerialNo()));
        user.setCheckedOrganizationalList(organizationalStrings);
    }

    /**
     * 新增用户信息
     * @param authUserInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insert(AuthUserInfoDTO authUserInfoDTO) {
        final User oldUser = userDao.getByMobile(authUserInfoDTO.getMobile());
        if (oldUser != null) {
            throw new ValidateException("用户手机号已存在");
        }
        String serialNo = SerialNoGenerator.generateSerialNo("U", 7);
        authUserInfoDTO.setSerialNo(serialNo);
        final User user = userInfoMapper.dto2do(authUserInfoDTO);
        Date nowDate = new Date();
        user.setGmtCreate(nowDate);
        user.setGmtModify(nowDate);
        LoginUser loginUser = LoginHandler.getLoginUser();
        user.setCreateUser(loginUser.getSerialNo());
        user.setStatus(StatusEnum.VALID.getValue());
        user.setDeleteFlag(DeleteFlagEnum.UNDELETED.getValue());
        String md5Password = DigestUtils.md5Hex(authUserInfoDTO.getPasswd());
        user.setPasswd(md5Password);

        insertRole(authUserInfoDTO);
        insertOrganizational(authUserInfoDTO);
        return userDao.insert(user);
    }

    /**
     * 更新用户信息
     * @param authUserInfoDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AuthUserInfoDTO authUserInfoDTO) {
        final User user = userInfoMapper.dto2do(authUserInfoDTO);
        user.setGmtModify(new Date());
        LoginUser loginUser = LoginHandler.getLoginUser();
        user.setModifyUser(loginUser.getSerialNo());
        userDao.update(user);

        //处理角色信息
        userDao.deleteRole(authUserInfoDTO.getSerialNo());
        insertRole(authUserInfoDTO);

        //处理组织信息
        userDao.deleteOrganizational(authUserInfoDTO.getSerialNo());
        insertOrganizational(authUserInfoDTO);
    }

    private void insertRole(AuthUserInfoDTO authUserInfoDTO) {
        //处理角色信息
        if (!CollectionUtils.isEmpty(authUserInfoDTO.getCheckedRoleList())) {
            authUserInfoDTO.getCheckedRoleList().forEach(e -> {
                UserRole userRole = new UserRole();
                userRole.setSerialNo(SerialNoGenerator.generateSerialNo("U", 7));
                userRole.setUserSerialNo(authUserInfoDTO.getSerialNo());
                userRole.setRoleSerialNo(e);
                userDao.insertRole(userRole);
            });
        }
    }

    private void insertOrganizational(AuthUserInfoDTO authUserInfoDTO) {
        //处理组织信息
        if (!CollectionUtils.isEmpty(authUserInfoDTO.getCheckedOrganizationalList())) {
            authUserInfoDTO.getCheckedOrganizationalList().forEach(e -> {
                UserOrganizational userOrganizational = new UserOrganizational();
                userOrganizational.setSerialNo(SerialNoGenerator.generateSerialNo("U", 7));
                userOrganizational.setUserSerialNo(authUserInfoDTO.getSerialNo());
                userOrganizational.setOrganizationalSerialNo(e);
                userDao.insertOrganizational(userOrganizational);
            });
        }
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

    /**
     * 验证token是否有效
     * @param token
     * @return
     */
    @Override
    public boolean validateToken(String token) {
        //TODO 每次验证查数据库性能有问题，后面考虑通过把数据缓存在内存中，程序启动的时候从表里加载
        final Token byToken = tokenDao.findByToken(token);
        return byToken.getTokenExpire().getTime() > System.currentTimeMillis();
    }

    /** 
     * @see com.smile.start.service.auth.UserInfoService#getUserByToken(java.lang.String)
     */
    @Override
    public User getUserByToken(String token) {
        Token tokenDo = tokenDao.findByToken(token);
        User user = userDao.getByMobile(tokenDo.getMobile());
        return user;
    }

    /** 
     * @see com.smile.start.service.auth.UserInfoService#getUserByOpenId(java.lang.String)
     */
    @Override
    public User getUserByOpenId(String openId) {
        User user = userDao.getByOpenId(openId);
        return user;
    }

    /** 
     * @see com.smile.start.service.auth.UserInfoService#getUserById(java.lang.Long)
     */
    @Override
    public User getUserById(Long id) {
        return userDao.get(id);
    }

    /**
     * 根据token获取登录用户信息，包括角色、权限信息
     * @param token
     * @return
     */
    @Override
    public LoginUser getLoginUserByToken(String token) {
        logger.info("get getLoginUserByToken start...");
        logger.info("get token start...");
        Token tokenDo = tokenDao.findByToken(token);
        logger.info("get token end...");
        logger.info("get user start...");
        User user = userDao.getByMobile(tokenDo.getMobile());
        logger.info("get user end...");

        //封装用户信息
        LoginUser loginUser = new LoginUser();
        loginUser.setSerialNo(user.getSerialNo());
        loginUser.setUsername(user.getUsername());
        loginUser.setMobile(user.getMobile());
        loginUser.setOpenid(user.getOpenid());
        loginUser.setEmail(user.getEmail());

        //获取用户角色信息
        logger.info("get role start...");
        final List<AuthRoleInfoDTO> roleList = roleInfoService.findByUserSerialNo(user.getSerialNo());
        if (!CollectionUtils.isEmpty(roleList)) {
            List<LoginUserRole> userRoleList = Lists.newArrayList();
            roleList.forEach(e -> {
                LoginUserRole loginUserRole = new LoginUserRole();
                loginUserRole.setSerialNo(e.getSerialNo());
                loginUserRole.setRoleCode(e.getRoleCode());
                loginUserRole.setRoleName(e.getRoleName());
                loginUserRole.setRoleDesc(e.getRoleDesc());
                userRoleList.add(loginUserRole);
            });
            loginUser.setRoleList(userRoleList);
        }
        logger.info("get role end...");

        //获取用户权限信息
        logger.info("get permission start...");
        final List<AuthPermissionInfoDTO> permissionList = permissionInfoService.findTopMenus(user.getSerialNo());
        if (!CollectionUtils.isEmpty(permissionList)) {
            List<LoginUserPermission> userPermissionList = Lists.newArrayList();
            permissionList.forEach(e -> {
                LoginUserPermission loginUserPermission = dto2LoginUserPermisson(e);
                List<AuthPermissionInfoDTO> subList = permissionInfoService.findByParentSerialNo(e.getSerialNo());
                List<LoginUserPermission> childrens = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(subList)) {
                    subList.forEach(p -> childrens.add(dto2LoginUserPermisson(p)));
                }
                loginUserPermission.setChildrens(childrens);
                userPermissionList.add(loginUserPermission);
            });
            loginUser.setPermissionList(userPermissionList);
        }
        logger.info("get permission end...");

        //获取组织信息
        logger.info("get organizational start...");
        final List<OrganizationalDTO> organiztionalList = organizationalService.findByUserSerialNo(user.getSerialNo());
        if (!CollectionUtils.isEmpty(organiztionalList)) {
            List<LoginUserOrganizational> organizationalList = Lists.newArrayList();
            organiztionalList.forEach(e -> {
                LoginUserOrganizational loginUserOrganizational = dto2LoginUserOrganizational(e);
                organizationalList.add(loginUserOrganizational);
            });
            loginUser.setOrganizationalList(organizationalList);
        }
        logger.info("get organizational end...");
        logger.info("get getLoginUserByToken end...");
        return loginUser;
    }

    private LoginUserPermission dto2LoginUserPermisson(AuthPermissionInfoDTO authPermissionInfoDTO) {
        LoginUserPermission loginUserPermission = new LoginUserPermission();
        loginUserPermission.setSerialNo(authPermissionInfoDTO.getSerialNo());
        loginUserPermission.setPermissionCode(authPermissionInfoDTO.getPermissionCode());
        loginUserPermission.setPermissionName(authPermissionInfoDTO.getPermissionName());
        loginUserPermission.setPermissionType(authPermissionInfoDTO.getPermissionType());
        loginUserPermission.setUrl(authPermissionInfoDTO.getUrl());
        return loginUserPermission;
    }

    private LoginUserOrganizational dto2LoginUserOrganizational(OrganizationalDTO organizationalDTO) {
        LoginUserOrganizational loginUserOrganizational = new LoginUserOrganizational();
        loginUserOrganizational.setSerialNo(organizationalDTO.getSerialNo());
        loginUserOrganizational.setOrganizationalName(organizationalDTO.getOrganizationalName());
        return loginUserOrganizational;
    }

    /**
     * 根据用户编号查询用户
     * @param serialNo
     * @return
     */
    @Override
    public AuthUserInfoDTO findBySerialNo(String serialNo) {
        return userInfoMapper.do2dto(userDao.findBySerialNo(serialNo));
    }

    /**
     * 密码更新
     * @param updatePasswordDTO
     */
    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getConfirmPassword())) {
            throw new ValidateException("新密码跟确认密码不一致，请重新输入");
        }
        LoginUser loginUser = LoginHandler.getLoginUser();
        String oldPassword = DigestUtils.md5Hex(updatePasswordDTO.getOldPassword());
        User user = userDao.findBySerialNo(loginUser.getSerialNo());
        if (!oldPassword.equals(user.getPasswd())) {
            throw new ValidateException("原密码不正确，请重新输入");
        }
        user.setPasswd(DigestUtils.md5Hex(updatePasswordDTO.getNewPassword()));
        userDao.updatePassword(user);
    }

    /**
     * 根据角色获取角色用户
     *
     * @param role
     * @return
     */
    @Override
    public List<User> getUserByRoles(AuthRoleInfoDTO role) {
        return userDao.findRuleUsers(roleInfoMapper.dto2do(role));
    }

}
