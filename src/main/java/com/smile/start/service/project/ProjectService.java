/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.project.Project;

/**
 * 项目服务
 * 
 * @author smile.jing
 * @version $Id: ProjectService.java, v 0.1 Jan 13, 2019 9:16:01 PM smile.jing
 *          Exp $
 */
public interface ProjectService {

    /**
     * 新增项目
     * 
     * @param project
     * @return
     */
    BaseResult initProject(Project project);

    /**
     * 更新项目
     * @param project
     * @return
     */
    BaseResult updateProject(Project project);

    /**
     * 业务状态流转
     * @param project
     * @return
     */
    BaseResult turnover(Project project);

    /**
     * 删除项目
     * @param id
     * @return
     */
    BaseResult delete(Long id);

    /**
     * 获取项目
     * @param id
     * @return
     */
    Project getProject(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    PageInfo<Project> queryPage(PageRequest<Project> page);

    /**
     * 查询所有未归档项目
     * @return
     */
    List<Project> queryUnarchivedProjects(Project project);
}
