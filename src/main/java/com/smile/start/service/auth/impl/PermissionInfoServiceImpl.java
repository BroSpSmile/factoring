package com.smile.start.service.auth.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.commons.LoginHandler;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.PermissionDao;
import com.smile.start.dao.RoleDao;
import com.smile.start.dto.AuthPermissionInfoDTO;
import com.smile.start.dto.PermissionSearchDTO;
import com.smile.start.mapper.PermissionInfoMapper;
import com.smile.start.model.auth.Permission;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.common.Tree;
import com.smile.start.model.enums.DeleteFlagEnum;
import com.smile.start.model.login.LoginUser;
import com.smile.start.service.auth.PermissionInfoService;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private PermissionDao        permissionDao;

    @Resource
    private RoleDao              roleDao;

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
     * 查询所有权限信息
     * @return
     */
    @Override
    public PageInfo<AuthPermissionInfoDTO> findAll(PageRequest<PermissionSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "id desc");
        final List<Permission> permissionList = permissionDao.findByParam(pageRequest.getCondition());
        PageInfo<AuthPermissionInfoDTO> pageInfo = new PageInfo<>(permissionInfoMapper.doList2dtoList(permissionList));
        Page<Permission> page = (Page<Permission>) permissionList;
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageNum(pageRequest.getPageNum());
        pageInfo.setPageSize(pageRequest.getPageSize());
        return pageInfo;
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
        LoginUser loginUser = LoginHandler.getLoginUser();
        permission.setCreateUser(loginUser.getSerialNo());
        permission.setSerialNo(SerialNoGenerator.generateSerialNo("P", 7));
        permission.setDeleteFlag(DeleteFlagEnum.UNDELETED.getValue());
        if (permission.getParentSerialNo() == null) {
            permission.setParentSerialNo("");
        }
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
        LoginUser loginUser = LoginHandler.getLoginUser();
        permission.setModifyUser(loginUser.getSerialNo());
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

    /**
     * 查询指定用户顶级权限信息
     * @param userSerialNo
     * @return
     */
    @Override
    public List<AuthPermissionInfoDTO> findParentByUserSerialNo(String userSerialNo) {
        final List<Permission> permissionList = permissionDao.findParentByUserSerialNo(userSerialNo);
        return permissionInfoMapper.doList2dtoList(permissionList);
    }

    /** 
     * @see com.smile.start.service.auth.PermissionInfoService#findTopMenus(java.lang.String)
     */
    @Override
    public List<AuthPermissionInfoDTO> findTopMenus(String userSerialNo) {
        List<Permission> permissionList = permissionDao.findTopPermission(userSerialNo);
        return permissionInfoMapper.doList2dtoList(permissionList);
    }

    /**
     * 获取权限树
     * @param roleSerialNo
     * @return
     */
    @Override
    public List<Tree> getTree(String roleSerialNo) {
        final List<String> checkedPermission = roleDao.findPermission(roleSerialNo);
        Tree root = new Tree();
        root.setTitle("权限树");
        root.setSerialNo("");
        List<Tree> treeList = Lists.newArrayList();
        treeList.add(getTree(root, checkedPermission));
        return treeList;
    }

    /**
     * 指定参数查询权限信息
     * @param permissionSearchDTO
     * @return
     */
    @Override
    public List<AuthPermissionInfoDTO> findByParam(PermissionSearchDTO permissionSearchDTO) {
        return permissionInfoMapper.doList2dtoList(permissionDao.findByParam(permissionSearchDTO));
    }

    /**
     * 根据父级权限编号查询权限
     * @param parentSerialNo
     * @return
     */
    @Override
    public List<AuthPermissionInfoDTO> findByParentSerialNo(String parentSerialNo) {
        return permissionInfoMapper.doList2dtoList(permissionDao.findByParentSerialNo(parentSerialNo));
    }

    /**
     * 递归获取组装树信息
     * @param parentTree
     * @return
     */
    private Tree getTree(Tree parentTree, List<String> checkedPermission) {
        final List<Permission> permissionList = permissionDao.findByParentSerialNo(parentTree.getSerialNo());
        if (!CollectionUtils.isEmpty(permissionList)) {
            List<Tree> children = Lists.newArrayList();
            permissionList.forEach(e -> {
                Tree tree = new Tree();
                tree.setTitle(e.getPermissionName());
                tree.setSerialNo(e.getSerialNo());
                if (checkedPermission.contains(e.getSerialNo())) {
                    tree.setChecked(true);
                }
                children.add(getTree(tree, checkedPermission));
            });
            parentTree.setChildren(children);
        }
        return parentTree;
    }
}
