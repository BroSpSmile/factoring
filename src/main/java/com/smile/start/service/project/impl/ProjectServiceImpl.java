/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.ProjectDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.project.Project;
import com.smile.start.service.AbstractService;
import com.smile.start.service.project.IdGenService;
import com.smile.start.service.project.ProjectService;

/**
 * 实现
 * 
 * @author smile.jing
 * @version $Id: ProjectServiceImpl.java, v 0.1 Jan 13, 2019 9:17:47 PM smile.jing Exp $
 * 
 */
@Service
public class ProjectServiceImpl extends AbstractService implements ProjectService {

    /** 项目DAO */
    @Resource
    private ProjectDao   projectDao;

    /** Id生成服务 */
    @Resource
    private IdGenService idGenService;

    /**
     * @see com.smile.start.service.project.ProjectService#initProject(com.smile.start.model.project.Project)
     */
    @Override
    @Transactional
    public BaseResult initProject(Project project) {
        project.setProjectId(idGenService.genId(project.getKind()));
        long effect = projectDao.insert(project);
        LoggerUtils.info(logger, "新增项目影响行effect={}", effect);
        BaseResult result = new BaseResult();
        if (effect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("");
            result.setErrorMessage("新增项目失败,请重试!");
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#findAll()
     */
    public List<Project> findAll() {
        PageHelper.offsetPage(0, 4, "id desc");
        List<Project> projects = projectDao.findAll();
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<Project> page = new PageInfo<>(projects);
        return page.getList();
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#queryPage(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<Project> queryPage(PageRequest<Project> page) {
        PageHelper.startPage(page.getPageNum() , page.getPageSize(), "id desc");
        List<Project> projects = projectDao.findByParam(page.getCondition());
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<Project> result = new PageInfo<>(projects);
        return result;
    }

}
