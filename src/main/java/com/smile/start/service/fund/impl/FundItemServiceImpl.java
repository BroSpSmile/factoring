/**
 *
 */
package com.smile.start.service.fund.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.smile.start.dao.FundTargetDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.FundStatus;
import com.smile.start.model.enums.ProjectItemType;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.AbstractService;
import com.smile.start.service.fund.FundItemService;
import com.smile.start.service.fund.FundService;
import com.smile.start.service.project.ProjectItemSerivce;

/**
 * 实现
 * @author Administrator
 */
@Service
public class FundItemServiceImpl extends AbstractService implements FundItemService {

    /** 项目附件服务 */
    @Resource
    private ProjectItemSerivce projectItemService;

    /** fundService */
    @Resource
    private FundService        fundService;

    /** fundTargetDao */
    @Resource
    private FundTargetDao      fundTargetDao;

    /**
     * 
     * @see com.smile.start.service.fund.FundItemService#save(com.smile.start.model.project.ProjectItem)
     */
    @Override
    public BaseResult save(ProjectItem item) {
        return projectItemService.save(item);
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#save(java.util.List)
     */
    @Override
    @Transactional
    public BaseResult save(FundStatus status, List<ProjectItem> items) {
        BaseResult result = new BaseResult();
        if (!CollectionUtils.isEmpty(items)) {
            ProjectItem item = items.get(0);
            BaseProject<FundTarget> project = new BaseProject<FundTarget>();
            FundTarget target = new FundTarget();
            project.setId(item.getProjectId());
            target.setProjectStep(status);
            project.setDetail(target);
            result = fundService.modifyTarget(project);
        }
        if (result.isSuccess()) {
            for (ProjectItem item : items) {
                result = this.save(item);
            }
        }
        return result;
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#delete(com.smile.start.model.project.ProjectItem)
     */
    @Override
    public BaseResult delete(ProjectItem item) {
        return projectItemService.delete(item);
    }

    /**
     * 
     * @see com.smile.start.service.fund.FundItemService#delete(com.smile.start.model.project.BaseProject, com.smile.start.model.enums.ProjectItemType)
     */
    @Override
    public BaseResult delete(BaseProject<FundTarget> project, ProjectItemType type) {
        return projectItemService.delete(project, type);
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#getById(java.lang.Long)
     */
    @Override
    public ProjectItem getById(Long id) {
        return projectItemService.getById(id);
    }

    /** 
     * @see com.smile.start.service.fund.FundItemService#getAll(com.smile.start.model.project.BaseProject)
     */
    @Override
    public List<ProjectItem> getAll(BaseProject<FundTarget> project) {
        return projectItemService.getAll(project);
    }

    /** 
     * @see com.smile.start.service.fund.FundItemService#getItemByType(com.smile.start.model.project.BaseProject, com.smile.start.model.enums.ProjectItemType)
     */
    @Override
    public List<ProjectItem> getItemByType(BaseProject<FundTarget> project, ProjectItemType type) {
        return projectItemService.getItemByType(project, type);
    }

}
