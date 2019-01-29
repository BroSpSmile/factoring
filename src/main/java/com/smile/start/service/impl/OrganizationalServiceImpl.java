package com.smile.start.service.impl;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.Asserts;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.OrganizationalDao;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.OrganizationalDTO;
import com.smile.start.dto.OrganizationalSearchDTO;
import com.smile.start.mapper.OrganizationalMapper;
import com.smile.start.model.auth.Organizational;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.DeleteFlagEnum;
import com.smile.start.model.enums.StatusEnum;
import com.smile.start.service.OrganizationalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/29 17:00, OrganizationalServiceImpl.java
 * @since 1.8
 */
@Service
public class OrganizationalServiceImpl implements OrganizationalService {

    @Resource
    private OrganizationalDao organizationalDao;

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
    public PageInfo<OrganizationalDTO> findAll(PageRequest<OrganizationalSearchDTO> page) {
        final PageInfo<OrganizationalDTO> result = new PageInfo<>();
        final List<Organizational> organizationalList = organizationalDao.findByParam(page.getCondition());
        result.setTotal(organizationalList.size());
        result.setPageSize(10);
        List<OrganizationalDTO> organizationalDTOS = organizationalMapper.doList2dtoList(organizationalList);
        result.setList(organizationalDTOS);
        return result;
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
        organizational.setDeleteFlag(DeleteFlagEnum.UNDELETED.getValue());
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
}
