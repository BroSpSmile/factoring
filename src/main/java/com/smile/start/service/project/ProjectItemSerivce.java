/**
 * VIP.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.service.project;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.ProjectItemType;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.ProjectItem;

import java.util.List;

/**
 * 项目附件服务
 *
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: ProjectItemSerivce
 * @date Date : 2019年10月03日 21:02
 */
public interface ProjectItemSerivce {

    /**
     * 保存附件
     *
     * @param item
     * @return
     */
    BaseResult save(ProjectItem item);

    /**
     * 保存附件
     *
     * @param items
     * @return
     */
    BaseResult save(List<ProjectItem> items);

    /**
     * 刪除附件
     *
     * @param item
     * @return
     */
    BaseResult delete(ProjectItem item);

    /**
     * 根据状态删除附件
     *
     * @param target
     * @param type
     * @return
     */
    BaseResult delete(BaseProject<?> project, ProjectItemType type);

    /**
     * 根据附件ID获取附件信息
     *
     * @param id
     * @return
     */
    ProjectItem getById(Long id);

    /**
     * 获取全部附件
     *
     * @param project
     * @return
     */
    List<ProjectItem> getAll(BaseProject<?> project);

    /**
     * 根据状态获取附件
     *
     * @param project
     * @param type
     * @return
     */
    List<ProjectItem> getItemByType(BaseProject<?> project, ProjectItemType type);
}
