/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service;

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
     * 分页查询
     * @param page
     * @return
     */
    PageInfo<Project> queryPage(PageRequest<Project> page);
}
