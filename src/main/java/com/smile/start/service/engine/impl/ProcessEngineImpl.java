/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.engine.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.smile.start.dao.ProjectStepDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.Step;
import com.smile.start.model.enums.StepStatus;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.StepRecord;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.project.ProjectService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: ProcessEngineImpl.java, v 0.1 Mar 11, 2019 12:08:31 AM smile.jing Exp $
 */
@Service
public class ProcessEngineImpl extends AbstractService implements ProcessEngine {

    /** stepDao */
    @Resource
    private ProjectStepDao stepDao;

    /** 审核服务 */
    @Resource
    private AuditService   auditService;

    /** projectService */
    @Resource
    private ProjectService projectService;

    /** 
     * @see com.smile.start.service.engine.ProcessEngine#getRecords(com.smile.start.model.project.Project)
     */
    @Override
    public List<StepRecord> getRecords(Project project) {
        //获取已处理业务流程记录
        List<StepRecord> done = stepDao.query(project);
        return getAllSteps(done);
    }

    /** 
     * @see com.smile.start.service.engine.ProcessEngine#next(com.smile.start.model.project.Project)
     */
    @Override
    @Transactional
    public SingleResult<StepRecord> next(Project project, Boolean needAudit) {
        this.changeStatus(project, StepStatus.COMPLETED);
        BaseResult updataResult = updateProject(project);
        if (updataResult.isSuccess()) {
            StepRecord record = new StepRecord();
            record.setProject(project);
            record.setStatus(StepStatus.BEGIN);
            record.setStep(Step.getStep(project.getStep()));
            record.setCreateTime(new Date());
            if (needAudit) {
                SingleResult<Audit> auditResult = auditService.apply(project, project.getUser());
                if (auditResult.isSuccess()) {
                    record.setAudit(auditResult.getData());
                }
            }
            long effect = stepDao.insert(record);
            SingleResult<StepRecord> result = new SingleResult<StepRecord>();
            result.setSuccess(effect > 0);
            result.setData(record);
            return result;
        } else {
            throw new RuntimeException("更新项目状态失败");
        }

    }

    /** 
     * @see com.smile.start.service.engine.ProcessEngine#prev(com.smile.start.model.project.Project)
     */
    @Override
    public BaseResult prev(Project project) {
        StepRecord record = new StepRecord();
        record.setProject(project);
        record.setStep(Step.getStep(project.getStep()));
        int effect = stepDao.delete(record);
        BaseResult result = toResult(effect);
        if (result.isSuccess()) {
            project.setStep(project.getStep() - 1);
            result = projectService.turnover(project);
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.engine.ProcessEngine#skip(com.smile.start.model.project.Project)
     */
    @Override
    public SingleResult<StepRecord> skip(Project project) {
        StepRecord record = new StepRecord();
        record.setProject(project);
        record.setStatus(StepStatus.LATER);
        record.setStep(Step.getStep(project.getStep()));
        record.setCreateTime(new Date());
        long effect = stepDao.insert(record);
        if (effect > 0) {
            project.setStep(project.getStep() + 1);
            return this.next(project, false);
        } else {
            throw new RuntimeException("更新项目进度失败");
        }

    }

    /** 
     * @see com.smile.start.service.engine.ProcessEngine#changeStatus(com.smile.start.model.project.Project, com.smile.start.model.enums.StepStatus)
     */
    @Override
    public SingleResult<StepRecord> changeStatus(Project project, StepStatus status) {
        return this.changeStatus(project, status, null);
    }

    /** 
     * @see com.smile.start.service.engine.ProcessEngine#changeStatus(com.smile.start.model.project.Project, com.smile.start.model.enums.StepStatus, com.smile.start.model.project.Audit)
     */
    @Override
    @Transactional
    public SingleResult<StepRecord> changeStatus(Project project, StepStatus status, Audit audit) {
        StepRecord record = stepDao.getStep(project.getId(), Step.getStep(project.getStep()));
        if (record != null) {
            if (null != audit) {
                record.setAudit(audit);
            }
            record.setStatus(status);
            record.setModifyTime(new Date());
            int effect = stepDao.update(record);
            SingleResult<StepRecord> result = new SingleResult<StepRecord>();
            result.setSuccess(effect > 0);
            result.setData(record);
            return result;
        }
        return new SingleResult<StepRecord>();
    }

    /**
     * 获取业务流程节点
     * @return
     */
    private List<StepRecord> getAllSteps(List<StepRecord> dones) {
        List<StepRecord> records = Lists.newArrayListWithCapacity(Step.values().length);
        for (Step step : Step.values()) {
            StepRecord record = null;
            for (StepRecord done : dones) {
                if (done.getStep() == step) {
                    record = done;
                }
            }
            if (null == record) {
                record = getRecord(step);
            }
            records.add(record);
        }
        return records;
    }

    /**
     * 获取未处理记录
     * @param step
     * @return
     */
    private StepRecord getRecord(Step step) {
        StepRecord record = new StepRecord();
        record.setStatus(StepStatus.BEGIN);
        record.setStep(step);
        return record;
    }

    /**
     * 更新项目
     * 
     * @param project
     * @return
     */
    private BaseResult updateProject(Project project) {
        project.setStep(project.getStep() + 1);
        return projectService.turnover(project);
    }
}
