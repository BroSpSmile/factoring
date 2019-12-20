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
import com.smile.start.dao.project.ProjectStepDao;
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
        SingleResult<Audit> auditResult = null;
        if (needAudit) {
            auditResult = auditService.apply(project, project.getUser());
        }
        BaseResult updataResult = projectService.turnover(project);
        if (updataResult.isSuccess()) {
            StepRecord record = new StepRecord();
            record.setProject(project);
            if (project.getStep() == 13) {//完结节点
                record.setStatus(StepStatus.COMPLETED);
            } else {
                record.setStatus(StepStatus.BEGIN);
            }
            record.setStep(Step.getStep(project.getStep()));
            record.setCreateTime(new Date());
            if (null != auditResult && auditResult.isSuccess()) {
                record.setAudit(auditResult.getData());
            }
            BaseResult addResult = addOrUpate(record);
            SingleResult<StepRecord> result = new SingleResult<StepRecord>();
            result.setSuccess(addResult.isSuccess());
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
        stepDao.delete(record);
        project.setStep(project.getStep() - 1);
        changeStatus(project, StepStatus.PROCESSING);
        return projectService.updateProject(project);

    }

    /** 
     * @see com.smile.start.service.engine.ProcessEngine#skip(com.smile.start.model.project.Project)
     */
    @Override
    @Transactional
    public BaseResult skip(Project project) {
        StepRecord record = new StepRecord();
        record.setProject(project);
        record.setStatus(StepStatus.LATER);
        record.setStep(Step.getStep(project.getStep()));
        record.setCreateTime(new Date());
        BaseResult result = addOrUpate(record);
        if (Step.TUNEUP.equals(Step.getStep(project.getStep()))) {
            project.setStep(project.getStep() + 1);
            StepRecord recordAudit = new StepRecord();
            recordAudit.setProject(project);
            recordAudit.setStatus(StepStatus.LATER);
            recordAudit.setStep(Step.getStep(project.getStep()));
            recordAudit.setCreateTime(new Date());
            result = addOrUpate(recordAudit);
        }
        if (result.isSuccess()) {
            BaseResult updataResult = projectService.turnover(project);
            if (updataResult.isSuccess()) {
                StepRecord newRecord = new StepRecord();
                newRecord.setProject(project);
                newRecord.setStatus(StepStatus.BEGIN);
                newRecord.setStep(Step.getStep(project.getStep()));
                newRecord.setCreateTime(new Date());

                BaseResult addResult = addOrUpate(newRecord);
                result.setSuccess(addResult.isSuccess());
                return result;
            } else {
                throw new RuntimeException("更新项目进度失败");
            }
        } else {
            throw new RuntimeException("更新项目进度失败");
        }

    }

    /**
     * 更新或者新增步骤
     * @param record
     * @return
     */
    public BaseResult addOrUpate(StepRecord record) {
        StepRecord exist = stepDao.getStep(record.getProject().getId(), record.getStep());
        if (null != exist) {
            if (!Step.MEETING.equals(record.getStep())) {
                record.setId(exist.getId());
                record.setModifyTime(new Date());
                stepDao.update(record);
            }
        } else {
            stepDao.insert(record);
        }
        return new BaseResult();
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

}
