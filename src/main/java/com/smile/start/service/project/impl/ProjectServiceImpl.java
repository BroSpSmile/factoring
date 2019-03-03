/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.ProjectDao;
import com.smile.start.dao.ProjectItemDao;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.auth.UserInfoService;
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
    private ProjectDao      projectDao;

    /** 用户服务 */
    @Resource
    private UserInfoService userInfoService;

    /** 项目附件DAO */
    @Resource
    private ProjectItemDao  projectItemDao;

    /** Id生成服务 */
    @Resource
    private IdGenService    idGenService;

    /** 审核服务 */
    @Resource
    private AuditService    auditService;

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
            result.setErrorCode("VP00011001");
            result.setErrorMessage("新增项目失败,请重试!");
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#updateProject(com.smile.start.model.project.Project)
     */
    @Override
    public BaseResult updateProject(Project project) {
        List<Project> projects = projectDao.findByProjectId(project.getProjectId());
        if (!CollectionUtils.isEmpty(projects) && projects.size() > 1) {
            throw new RuntimeException("当前项目ID重复,无法更新");
        }
        if (!CollectionUtils.isEmpty(projects)) {
            for (Project old : projects) {
                if (old.getId() != project.getId()) {
                    throw new RuntimeException("当前项目ID重复,无法更新");
                }
            }
        }
        int effect = projectDao.update(project);
        LoggerUtils.info(logger, "修改项目影响行effect={}", effect);
        BaseResult result = new BaseResult();
        if (effect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011002");
            result.setErrorMessage("新增项目失败,请重试!");
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#apply(com.smile.start.model.project.Project)
     */
    @Override
    @Transactional
    public BaseResult apply(Project project) {
        int effect = projectDao.update(project);
        LoggerUtils.info(logger, "修改项目影响行effect={}", effect);
        for (ProjectItem item : project.getItems()) {
            projectItemDao.insert(item);
        }
        BaseResult result = auditService.apply(project, project.getUser());
        return result;
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#delete(java.lang.Long)
     */
    @Override
    @Transactional
    public BaseResult delete(Long id) {
        Project project = projectDao.get(id);
        if (project == null) {
            throw new RuntimeException("删除项目失败,当前项目不存在");
        }
        if (!Progress.INIT.equals(project.getProgress())) {
            throw new RuntimeException("删除项目失败,当前项目状态非法");
        }
        int effect = projectDao.delete(id);
        LoggerUtils.info(logger, "删除项目影响行effect={}", effect);
        BaseResult result = new BaseResult();
        if (effect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011003");
            result.setErrorMessage("删除项目失败,请重试!");
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
     * @see com.smile.start.service.project.ProjectService#getProject(java.lang.Long)
     */
    @Override
    public Project getProject(Long id) {
        Project project = projectDao.get(id);
        List<ProjectItem> items = projectItemDao.getItems(project);
        project.setItems(items);
        return project;
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#queryPage(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<Project> queryPage(PageRequest<Project> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), "id desc");
        List<Project> projects = projectDao.findByParam(page.getCondition());
        projects.stream().forEach(project -> project.setUser(getUser(project.getUser().getId())));
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<Project> result = new PageInfo<>(projects);
        return result;
    }

    /**
     * 获取用户
     * @param id
     * @return
     */
    private User getUser(Long id) {
        return userInfoService.getUserById(id);
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#queryUnarchivedProjects()
     */
    @Override
    public List<Project> queryUnarchivedProjects(Project project) {
        return projectDao.findUnarchivedProjects(project);
    }

}
