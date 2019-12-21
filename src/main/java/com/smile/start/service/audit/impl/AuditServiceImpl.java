/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.audit.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.dao.FlowConfigDao;
import com.smile.start.dao.audit.AuditDao;
import com.smile.start.dao.audit.AuditRecordDao;
import com.smile.start.dao.audit.AuditRecordItemDao;
import com.smile.start.event.AuditEvent;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.dto.AuthRoleInfoDTO;
import com.smile.start.model.enums.Step;
import com.smile.start.model.enums.audit.AuditResult;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.enums.project.ProjectKind;
import com.smile.start.model.project.*;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.project.ProjectService;

/**
 * 
 * @author smile.jing
 * @version $Id: AuditServiceImpl.java, v 0.1 Mar 2, 2019 9:51:45 PM smile.jing Exp $
 */
@Service
public class AuditServiceImpl extends AbstractService implements AuditService {

    /** auditDao */
    @Resource
    private AuditDao           auditDao;

    /** auditRecordDao */
    @Resource
    private AuditRecordDao     auditRecordDao;

    /** auditRecordItemDao */
    @Resource
    private AuditRecordItemDao auditRecordItemDao;

    /** 用户服务 */
    @Resource
    private UserInfoService    userInfoService;

    /** flowConfigDao */
    @Resource
    private FlowConfigDao      flowConfigDao;

    /** 项目服务 */
    @Resource
    private ProjectService     projectService;

    @Autowired
    private ApplicationContext applicationContext;

    /** 
     * @see com.smile.start.service.audit.AuditService#apply(com.smile.start.model.project.Project, com.smile.start.model.auth.User)
     */
    @Override
    @Transactional
    public SingleResult<Audit> apply(Project project, User user) {
        Audit audit = initAudit(project, user);
        Audit extendAudit = auditDao.getByProjectAndType(project.getId(), audit.getAuditType().name());
        SingleResult<Audit> result = new SingleResult<Audit>();
        if (extendAudit != null) {
            AuditRecord record = new AuditRecord();
            record.setAudit(extendAudit);
            record.setAuditor(audit.getApplicant());
            this.pass(record);
            result.setSuccess(true);
            result.setData(extendAudit);
        } else {
            long effect = auditDao.insert(audit);
            this.addApplyRecord(audit);
            if (effect > 0) {
                result.setData(audit);
                applicationContext.publishEvent(new AuditEvent(this, audit));
            } else {
                result.setSuccess(false);
                result.setErrorCode("VP00011001");
                result.setErrorMessage("新增审核记录失败");
            }
        }
        return result;
    }

    /**
     * 初始化审核流程
     * @param project
     * @param user
     * @return
     */
    private Audit initAudit(Project project, User user) {
        Audit audit = new Audit();
        audit.setApplicant(user);
        audit.setProject(project);
        audit.setCreateTime(new Date());
        audit.setStep(0);
        audit.setAuditType(AuditType.getByCode(Step.getStep(project.getStep()).name()));
        if (ProjectKind.INVESTMENT.equals(project.getKind()) && AuditType.FILE.equals(audit.getAuditType())) {
            audit.setAuditType(AuditType.FUND_FILE);
        }
        AuditFlow flow = getFlow(audit, 1);
        audit.setStep(flow.getStep());
        audit.setRole(flow.getRole());
        audit.setNextAudit(flow);
        return audit;
    }

    /** 
     * @see com.smile.start.service.audit.AuditService#pass(com.smile.start.model.project.AuditRecord)
     */
    @Override
    @Transactional
    public BaseResult pass(AuditRecord record) {
        Audit audit = auditDao.get(record.getAudit().getId());
        AuditFlow step = getFlow(audit, 0);
        record.setResult(AuditResult.PASS);
        record.setType(step.getDesc());
        record.setStatus(step.getDesc() + "通过");
        AuditFlow nextStep = getFlow(audit, 1);
        if (null != nextStep) {
            audit.setStep(nextStep.getStep());
            audit.setRole(nextStep.getRole());
            audit.setNextAudit(nextStep);
        } else {
            audit.setStep(-1);
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(StringUtils.EMPTY);
            audit.setRole(role);
        }
        auditDao.updateRole(audit);
        BaseResult result = addAuditRecord(record);
        if (result.isSuccess()) {
            audit.setRecords(Lists.newArrayList(record));
            applicationContext.publishEvent(new AuditEvent(this, audit));
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.audit.AuditService#reject(com.smile.start.model.project.AuditRecord)
     */
    @Override
    @Transactional
    public BaseResult reject(AuditRecord record) {
        Audit audit = auditDao.get(record.getAudit().getId());
        AuditFlow flow = getFlow(audit, 0);
        record.setResult(AuditResult.REJECTED);
        record.setType(flow.getDesc());
        AuditFlow nextFlow = getFlow(record.getAudit(), 0);
        if (null != nextFlow) {
            record.setStatus("驳回至" + nextFlow.getDesc());
            audit.setStep(nextFlow.getStep());
            audit.setRole(nextFlow.getRole());
            audit.setNextAudit(nextFlow);
        } else {
            audit.setStep(-2);
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(StringUtils.EMPTY);
            audit.setRole(role);
        }
        auditDao.updateRole(audit);
        BaseResult result = addAuditRecord(record);
        if (result.isSuccess()) {
            audit.setRecords(Lists.newArrayList(record));
            //发布审核事件
            applicationContext.publishEvent(new AuditEvent(this, audit));
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.audit.AuditService#query(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<Audit> query(PageRequest<AuditParam> param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize(), "id desc");
        List<Audit> audits = auditDao.query(param.getCondition());
        for (Audit audit : audits) {
            Project project = projectService.getProject(audit.getProject().getId());
            audit.setProject(project);
            User user = userInfoService.getUserById(audit.getApplicant().getId());
            audit.setApplicant(user);
        }
        PageInfo<Audit> result = new PageInfo<>(audits);
        return result;
    }

    /** 
     * @see com.smile.start.service.audit.AuditService#queryHistory(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<Audit> queryHistory(PageRequest<AuditParam> param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize(), "id desc");
        List<Audit> audits = auditDao.queryHistory(param.getCondition());
        for (Audit audit : audits) {
            Project project = projectService.getProject(audit.getProject().getId());
            audit.setProject(project);
            User user = userInfoService.getUserById(audit.getApplicant().getId());
            audit.setApplicant(user);
        }
        PageInfo<Audit> result = new PageInfo<>(audits);
        return result;
    }

    /** 
     * @see com.smile.start.service.audit.AuditService#getAudit(java.lang.Long)
     */
    @Override
    public Audit getAudit(Long id) {
        Audit audit = auditDao.get(id);
        genAudit(audit);
        return audit;
    }

    /**
     * @see com.smile.start.service.audit.AuditService#getAudit(java.lang.Long)
     */
    @Override
    public Audit getAuditByProjectFlowAndType(Long id, String type) {
        Audit audit = auditDao.getByProjectAndType(id, type);
        if (audit != null) {
            genAudit(audit);
        }
        return audit;
    }

    private void genAudit(Audit audit) {
        Project project = projectService.getProject(audit.getProject().getId());
        audit.setProject(project);
        List<AuditRecord> records = auditRecordDao.query(audit);
        for (AuditRecord record : records) {
            if (AuditType.TUNEUP.equals(audit.getAuditType())) {
                List<AuditRecordItem> items = auditRecordItemDao.query(record);
                record.setItems(items);
            }
        }
        audit.setRecords(records);
        audit.setFlows(getFlows(audit));
    }

    /**
     * 获取下一审核步骤
     * @param audit
     * @param offset
     * @return
     */
    private AuditFlow getFlow(Audit audit, Integer offset) {
        List<AuditFlow> flows = flowConfigDao.findFlows(audit.getAuditType().getValue());
        for (AuditFlow flow : flows) {
            if (flow.getStep() == (audit.getStep() + offset)) {
                return flow;
            }
        }
        return null;
    }

    private List<AuditFlow> getFlows(Audit audit) {
        List<AuditFlow> flows = flowConfigDao.findFlows(audit.getAuditType().getValue());
        for (AuditFlow flow : flows) {
            //查询审核记录
            AuditRecord record = auditRecordDao.getLast(audit.getId(), flow.getDesc());
            if (null != record) {
                flow.setResult(record.getResult());
                flow.setUser(record.getAuditor());
            }
        }
        return flows;
    }

    /**
     * 申请添加审核记录
     * @param audit
     * @return
     */
    private BaseResult addApplyRecord(Audit audit) {
        AuditRecord record = new AuditRecord();
        AuditFlow flow = getFlow(audit, -1);
        record.setStatus("成功");
        record.setRemark("申请成功");
        record.setType(flow.getDesc());
        record.setAudit(audit);
        record.setAuditor(audit.getApplicant());
        record.setResult(AuditResult.APPLY);
        record.setStatus(record.getResult().getDesc());
        return addAuditRecord(record);
    }

    /**
     * 插入审批记录
     * @param record
     * @return
     */
    private BaseResult addAuditRecord(AuditRecord record) {
        record.setAuditTime(new Date());
        long effect = auditRecordDao.insert(record);
        if (effect > 0) {
            if (!CollectionUtils.isEmpty(record.getItems())) {
                for (AuditRecordItem item : record.getItems()) {
                    item.setRecord(record);
                    auditRecordItemDao.insert(item);
                }
            }
            return new BaseResult();
        } else {
            throw new RuntimeException("新增审核记录失败");
        }

    }

}
