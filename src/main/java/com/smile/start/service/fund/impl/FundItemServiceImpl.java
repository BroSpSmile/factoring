/**
 *
 */
package com.smile.start.service.fund.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.smile.start.mapper.ProjectMapper;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.enums.project.ProjectItemType;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.AbstractService;
import com.smile.start.service.fund.FundItemService;
import com.smile.start.service.fund.FundService;
import com.smile.start.service.project.ProjectItemSerivce;
import com.smile.start.service.project.ProjectService;

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

    /** 项目服务 */
    @Resource
    private ProjectService     projectService;

    /**
     * 
     * @see com.smile.start.service.fund.FundItemService#save(com.smile.start.model.project.ProjectItem)
     */
    @Override
    public BaseResult save(ProjectItem item) {
        return projectItemService.save(item);
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#save(FundStatus,java.util.List)
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
            FundTarget local = fundService.getTarget(item.getProjectId());
            if (local.getProjectStep().getIndex() > status.getIndex()) {
                target.setProjectStep(local.getProjectStep());
            } else {
                target.setProjectStep(status);
            }
            project.setDetail(target);
            result = fundService.modifyTarget(project);
            if (result.isSuccess()) {
                //projectItemService.delete(project, item.getItemType());
                for (ProjectItem i : items) {
                    result = this.save(i);
                }
            }
        }

        return result;
    }

    /**
     * 保存并提交审核
     *
     * @param status
     * @param items
     * @param type
     * @return
     */
    @Override
    @Transactional
    public SingleResult<Audit> saveAndAudit(FundStatus status, List<ProjectItem> items, AuditType type, User user) {
        SingleResult<Audit> result = new SingleResult();
        if (!CollectionUtils.isEmpty(items)) {
            ProjectItem item = items.get(0);
            Project project = projectService.getProject(item.getProjectId());
            project.setUser(user);
            BaseProject<FundTarget> fundProject = ProjectMapper.mapper(project, FundTarget.class);
            FundTarget target = new FundTarget();
            project.setId(item.getProjectId());
            target.setProjectStep(status);
            fundProject.setDetail(target);
            result = fundService.createAudit(ProjectMapper.mapper(project, FundTarget.class), type);

        }
        if (result.isSuccess()) {
            for (ProjectItem item : items) {
                this.save(item);
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
     * @see com.smile.start.service.fund.FundItemService#delete(com.smile.start.model.project.BaseProject, ProjectItemType)
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
     * @see com.smile.start.service.fund.FundItemService#getItemByType(com.smile.start.model.project.BaseProject, ProjectItemType)
     */
    @Override
    public List<ProjectItem> getItemByType(BaseProject<FundTarget> project, ProjectItemType type) {
        return projectItemService.getItemByType(project, type);
    }

}
