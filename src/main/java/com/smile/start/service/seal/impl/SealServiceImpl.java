package com.smile.start.service.seal.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.dao.SealDao;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.SealSearchDTO;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.seal.ProjectSeal;
import com.smile.start.service.seal.SealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用印管理
 * @author Joseph
 * @version v1.0 2019/3/17 19:45, SealServiceImpl.java
 * @since 1.8
 */
@Service
public class SealServiceImpl implements SealService {

    @Resource
    private SealDao sealDao;

    /**
     * 查询所有待签署项目信息
     * @return
     */
    @Override
    public PageInfo<ProjectSeal> findAll(PageRequest<SealSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "fp.id desc");
        final List<ProjectSeal> projectList = sealDao.findByParam(pageRequest.getCondition());
        return new PageInfo<>(projectList);
    }
}
