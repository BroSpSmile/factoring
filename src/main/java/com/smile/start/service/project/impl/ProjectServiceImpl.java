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
import com.smile.start.dao.project.ProjectDao;
import com.smile.start.dao.project.ProjectItemDao;
import com.smile.start.dao.project.ProjectStepDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.Step;
import com.smile.start.model.enums.project.Progress;
import com.smile.start.model.enums.project.ProjectItemType;
import com.smile.start.model.enums.project.ProjectKind;
import com.smile.start.model.project.FactoringDetail;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.model.project.StepRecord;
import com.smile.start.service.AbstractService;
import com.smile.start.service.common.FileService;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.project.FactoringService;
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
    private ProjectDao       projectDao;

    /** projectRecordDao */
    @Resource
    private ProjectStepDao   projectRecordDao;

    /** factoringService */
    @Resource
    private FactoringService factoringService;

    /** 项目附件DAO */
    @Resource
    private ProjectItemDao   projectItemDao;

    /** Id生成服务 */
    @Resource
    private IdGenService     idGenService;

    /** 流程引擎 */
    @Resource
    private ProcessEngine    processEngine;

    /** 文件服务 */
    @Resource
    private FileService      fileService;

    /**
     * @see com.smile.start.service.project.ProjectService#initProject(com.smile.start.model.project.Project)
     */
    @Override
    @Transactional
    public BaseResult initProject(Project project) {
        // project.setProjectId(idGenService.genId(project.getKind()));
        project.setStep(0);
        project.setKind(ProjectKind.FACTORING);
        project.setProgress(Progress.INIT);
        long effect = projectDao.insert(project);
        LoggerUtils.info(logger, "新增项目影响行effect={}", effect);
        BaseResult result = toResult(effect);
        if (!CollectionUtils.isEmpty(project.getItems())) {
            for (ProjectItem item : project.getItems()) {
                item.setProjectId(project.getId());
                projectItemDao.insert(item);
            }
        }
        //processEngine.next(project, false);
        return result;
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#updateProject(com.smile.start.model.project.Project)
     */
    @Override
    @Transactional
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
        if (!CollectionUtils.isEmpty(project.getItems())) {
            for (ProjectItem item : project.getItems()) {
                item.setProjectId(project.getId());
                projectItemDao.insert(item);
            }
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#turnover(com.smile.start.model.project.Project)
     */
    @Override
    @Transactional
    public BaseResult turnover(Project project) {
        Project local = projectDao.get(project.getId());
        if (project.getStep() >= local.getStep()) {//判断是否后补进度
            project.setStep(project.getStep());
            int effect = projectDao.update(project);
            LoggerUtils.info(logger, "修改项目影响行effect={}", effect);
        } else {
            project.setStep(project.getStep());
            int effect = projectDao.update(project);
            LoggerUtils.info(logger, "修改项目影响行effect={}", effect);
        }
        //        if (!CollectionUtils.isEmpty(project.getItems())) {
        //            for (ProjectItem item : project.getItems()) {
        //                projectItemDao.insert(item);
        //            }
        //        }
        return new BaseResult();
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
     * @see com.smile.start.service.project.ProjectService#getProjectDetail(java.lang.Long)
     */
    @Override
    public Project getProjectDetail(Long id) {
        Project project = this.getProject(id);
        FactoringDetail detail = factoringService.get(id);
        detail.setProject(project);
        project.setDetail(detail);
        return project;
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#queryPage(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<Project> queryPage(PageRequest<Project> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), "id desc");
        List<Project> projects = projectDao.queryFactoringProject(page.getCondition());
        PageInfo<Project> result = new PageInfo<>(projects);
        return result;
    }

    /**
     * 根据项目状态获取项目
     *
     * @param step
     * @return
     */
    @Override
    public List<Project> queryByStatus(Step step) {
        Project project = new Project();
        project.setStep(step.getIndex());
        List<Project> projects = projectDao.queryFactoringProject(project);
        return projects;
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#queryUnarchivedProjects
     */
    @Override
    public List<Project> queryUnarchivedProjects(Project project) {
        return projectDao.findUnarchivedProjects(project);
    }

    /**
     * 查询项目附件
     *
     * @param projectId 项目ID
     * @return
     */
    @Override
    public List<ProjectItem> queryItems(Long projectId) {
        Project project = new Project();
        project.setId(projectId);
        return projectItemDao.getItems(project);
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#queryItems(java.lang.Long, ProjectItemType)
     */
    @Override
    public List<ProjectItem> queryItems(Long projectId, ProjectItemType type) {
        return projectItemDao.getTypeItems(projectId, type);
    }

    /** 
     * @see com.smile.start.service.project.ProjectService#deleteItem(com.smile.start.model.project.ProjectItem)
     */
    @Override
    public BaseResult deleteItem(ProjectItem item) {
        int effect = projectItemDao.delete(item);
        boolean success = fileService.delete(item.getItemValue());
        LoggerUtils.info(logger, "删除文件结果:{}", success);
        return toResult(effect);
    }

    /** 
     * @see com.smile.start.service.project.ProjectService
     */
    public List<Project> findAll() {
        PageHelper.offsetPage(0, 4, "id desc");
        List<Project> projects = projectDao.findAll();
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<Project> page = new PageInfo<>(projects);
        return page.getList();
    }

    /**
     * 
     * @param project
     */
    @Override
    public void setDetail(Project project) {
        FactoringDetail detail = factoringService.get(project.getId());
        List<StepRecord> records = processEngine.getRecords(project);
        project.setRecords(records);
        project.setDetail(detail);
    }

}
