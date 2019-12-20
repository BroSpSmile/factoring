/**
 * VIP.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.project.ProjectItemDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.project.ProjectItemType;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.AbstractService;
import com.smile.start.service.project.ProjectItemSerivce;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: ProjectItemSerivceImpl
 * @date Date : 2019年10月03日 21:04
 */
@Service
public class ProjectItemSerivceImpl extends AbstractService implements ProjectItemSerivce {
    /**
     * 项目附件DAO
     */
    @Resource
    private ProjectItemDao itemDao;

    /**
     * 保存附件
     *
     * @param item
     * @return
     */
    @Override
    public BaseResult save(ProjectItem item) {
        long effect = itemDao.insert(item);
        LoggerUtils.info(logger, "新增附件effect={}", effect);
        return toResult(effect);
    }

    /**
     * 保存附件
     *
     * @param items
     * @return
     */
    @Override
    public BaseResult save(List<ProjectItem> items) {
        BaseResult result = new BaseResult();
        for (ProjectItem item : items) {
            result = this.save(item);
        }
        return result;
    }

    /**
     * 刪除附件
     *
     * @param item
     * @return
     */
    @Override
    public BaseResult delete(ProjectItem item) {
        int effect = itemDao.delete(item);
        return toResult(effect);
    }

    /**
     * 根据状态删除附件
     *
     * @param project
     * @param type
     * @return
     */
    @Override
    public BaseResult delete(BaseProject<?> project, ProjectItemType type) {
        int effect = itemDao.deleteItems(project.getId(), type);
        return toResult(effect);
    }

    /**
     * 根据附件ID获取附件信息
     *
     * @param id
     * @return
     */
    @Override
    public ProjectItem getById(Long id) {
        return itemDao.get(id);
    }

    /**
     * 获取全部附件
     *
     * @param project
     * @return
     */
    @Override
    public List<ProjectItem> getAll(BaseProject<?> project) {
        return itemDao.getProjectItems(project);
    }

    /**
     * 根据状态获取附件
     *
     * @param project
     * @param type
     * @return
     */
    @Override
    public List<ProjectItem> getItemByType(BaseProject<?> project, ProjectItemType type) {
        return itemDao.getTypeItems(project.getId(), type);
    }
}
