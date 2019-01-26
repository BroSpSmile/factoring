package com.smile.start.service.impl;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.Asserts;
import com.smile.start.commons.Constants;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.TokenDao;
import com.smile.start.dao.UserDao;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.LoginRequestDTO;
import com.smile.start.dto.UserSearchDTO;
import com.smile.start.mapper.UserInfoMapper;
import com.smile.start.model.auth.Token;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.DeleteFlagEnum;
import com.smile.start.model.enums.StatusEnum;
import com.smile.start.service.PermissionInfoService;
import com.smile.start.service.RoleInfoService;
import com.smile.start.service.UserInfoService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
    private TokenDao              tokenDao;

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
        user.setStatus(StatusEnum.VALID.getValue());
        user.setDeleteFlag(DeleteFlagEnum.UNDELETED.getValue());
        //TODO
        //String md5Passwd = MD5Encoder.encode(authUserInfoDTO.getPasswd().getBytes());
        //user.setPasswd(md5Passwd);
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
}
