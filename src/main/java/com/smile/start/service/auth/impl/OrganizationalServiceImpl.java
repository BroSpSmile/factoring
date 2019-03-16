package com.smile.start.service.auth.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.Asserts;
import com.smile.start.commons.LoginHandler;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.OrganizationalDao;
import com.smile.start.dto.OrganizationalDTO;
import com.smile.start.dto.OrganizationalSearchDTO;
import com.smile.start.mapper.OrganizationalMapper;
import com.smile.start.model.auth.Organizational;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.DeleteFlagEnum;
import com.smile.start.model.login.LoginUser;
import com.smile.start.service.auth.OrganizationalService;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/1/29 17:00, OrganizationalServiceImpl.java
 * @since 1.8
 */
@Service
public class OrganizationalServiceImpl implements OrganizationalService {

    @Resource
    private OrganizationalDao    organizationalDao;

    @Resource
    private OrganizationalMapper organizationalMapper;

    /**
     * 根据主键查询组织架构信息
     * @param id
     * @return
     */
    @Override
    public OrganizationalDTO get(Long id) {
        final Organizational organizational = organizationalDao.get(id);
        OrganizationalDTO organizationalDTO = organizationalMapper.do2dto(organizational);
        Asserts.notNull(organizationalDTO, "组织架构主键：" + id + " 没有找到记录");
        return organizationalDTO;
    }

    /**
     * 查询所有组织架构
     * @return
     */
    @Override
    public PageInfo<OrganizationalDTO> findAll(PageRequest<OrganizationalSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "id desc");
        final List<Organizational> organizationalList = organizationalDao.findByParam(pageRequest.getCondition());
        PageInfo<OrganizationalDTO> pageInfo = new PageInfo<>(organizationalMapper.doList2dtoList(organizationalList));
        Page<Organizational> page = (Page<Organizational>) organizationalList;
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageNum(pageRequest.getPageNum());
        pageInfo.setPageSize(pageRequest.getPageSize());
        return pageInfo;
    }

    /**
     * 新增组织架构信息
     * @param organizationalDTO
     * @return
     */
    @Override
    public Long insert(OrganizationalDTO organizationalDTO) {
        String serialNo = SerialNoGenerator.generateSerialNo("U", 7);
        organizationalDTO.setSerialNo(serialNo);
        final Organizational organizational = organizationalMapper.dto2do(organizationalDTO);
        Date nowDate = new Date();
        organizational.setGmtCreate(nowDate);
        organizational.setGmtModify(nowDate);
        LoginUser loginUser = LoginHandler.getLoginUser();
        organizational.setCreateUser(loginUser.getSerialNo());
        organizational.setDeleteFlag(DeleteFlagEnum.UNDELETED.getValue());
        if (organizational.getParentSerialNo() == null) {
            organizational.setParentSerialNo("");
        }
        return organizationalDao.insert(organizational);
    }

    /**
     * 更新组织架构信息
     * @param organizationalDTO
     */
    @Override
    public void update(OrganizationalDTO organizationalDTO) {
        final Organizational organizational = organizationalMapper.dto2do(organizationalDTO);
        organizational.setGmtModify(new Date());
        LoginUser loginUser = LoginHandler.getLoginUser();
        organizational.setModifyUser(loginUser.getSerialNo());
        organizationalDao.update(organizational);
    }

    /**
     * 删除组织架构信息
     * @param id
     */
    @Override
    public void delete(Long id) {
        final Organizational organizational = organizationalDao.get(id);
        organizational.setDeleteFlag(DeleteFlagEnum.DLETED.getValue());
        organizationalDao.update(organizational);
    }

    /**
     * 按条件查询
     * @param organizationalSearchDTO
     * @return
     */
    @Override
    public List<OrganizationalDTO> findByParam(OrganizationalSearchDTO organizationalSearchDTO) {
        return organizationalMapper.doList2dtoList(organizationalDao.findByParam(organizationalSearchDTO));
    }

    /**
     * 根据用户编号查询组织
     * @param userSerialNo
     * @return
     */
    @Override
    public List<OrganizationalDTO> findByUserSerialNo(String userSerialNo) {
        return organizationalMapper.doList2dtoList(organizationalDao.findByUserSerialNo(userSerialNo));
    }
}
