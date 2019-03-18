/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.audit.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.smile.start.event.AuditEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.dao.AuditDao;
import com.smile.start.dao.AuditRecordDao;
import com.smile.start.dao.AuditRecordItemDao;
import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.dto.FlowConfigDTO;
import com.smile.start.dto.FlowStatusDTO;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.AuditResult;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.enums.FlowTypeEnum;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.enums.Step;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditFlow;
import com.smile.start.model.project.AuditParam;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.model.project.AuditRecordItem;
import com.smile.start.model.project.Project;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.auth.RoleInfoService;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.common.FlowConfigService;
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
    protected UserInfoService  userInfoService;

    /** 角色服务 */
    @Resource
    private RoleInfoService    roleInfoService;

    /** flowConfigService */
    @Resource
    private FlowConfigService  flowConfigService;

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
                return result;
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
        FlowStatusDTO step = nextStep(audit, toType(project.getProgress()), 1);
        audit.setStep(step.getFlowStatus());
        //获取审核流程
        AuthRoleInfoDTO role = new AuthRoleInfoDTO();
        role.setSerialNo(step.getRoleSerialNo());
        audit.setRole(role);
        return audit;
    }

    /** 
     * @see com.smile.start.service.audit.AuditService#pass(com.smile.start.model.project.AuditRecord, com.smile.start.dto.AuthRoleInfoDTO)
     */
    @Override
    @Transactional
    public BaseResult pass(AuditRecord record) {
        Audit audit = getAudit(record.getAudit().getId());
        FlowStatusDTO step = nextStep(audit, toType(audit.getProject().getProgress()), 0);
        record.setResult(AuditResult.PASS);
        record.setType(step.getFlowStatusDesc());
        record.setStatus(step.getFlowStatusDesc() + "通过");
        FlowStatusDTO nextStep = nextStep(audit, toType(audit.getProject().getProgress()), 1);
        if (null != nextStep) {
            audit.setStep(nextStep.getFlowStatus());
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(nextStep.getRoleSerialNo());
            audit.setRole(role);
        } else {
            audit.setStep(-1);
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(StringUtils.EMPTY);
            audit.setRole(role);
        }
        auditDao.updateRole(audit);
        BaseResult result = addAuditRecord(record);
        if (result.isSuccess()) {
            applicationContext.publishEvent(new AuditEvent(this, audit));
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.audit.AuditService#reject(com.smile.start.model.project.AuditRecord, com.smile.start.dto.AuthRoleInfoDTO)
     */
    @Override
    @Transactional
    public BaseResult reject(AuditRecord record) {
        Audit audit = getAudit(record.getAudit().getId());
        FlowStatusDTO step = nextStep(audit, toType(audit.getProject().getProgress()), 0);
        record.setResult(AuditResult.REJECTED);
        record.setType(step.getFlowStatusDesc());
        FlowStatusDTO nextStep = nextStep(record.getAudit(), toType(audit.getProject().getProgress()), 0);
        if (null != nextStep) {
            record.setStatus("驳回至" + nextStep.getFlowStatusDesc());
            audit.setStep(nextStep.getFlowStatus());
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(nextStep.getRoleSerialNo());
            audit.setRole(role);
        } else {
            audit.setStep(-2);
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(StringUtils.EMPTY);
            audit.setRole(role);
        }
        auditDao.updateRole(audit);
        BaseResult result = addAuditRecord(record);
        if (result.isSuccess()) {
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
        User applicant = userInfoService.getUserById(audit.getApplicant().getId());
        audit.setApplicant(applicant);
        Project project = projectService.getProject(audit.getProject().getId());
        audit.setProject(project);
        List<AuditRecord> records = auditRecordDao.query(audit);
        for (AuditRecord record : records) {
            List<AuditRecordItem> items = auditRecordItemDao.query(record);
            record.setItems(items);
            User user = userInfoService.getUserById(record.getAuditor().getId());
            record.setAuditor(user);
        }
        audit.setRecords(records);
        audit.setFlows(getFlows(audit));
    }

    /**
     * 获取下一审核步骤
     * @param audit
     * @param type
     * @return
     */
    private FlowStatusDTO nextStep(Audit audit, FlowTypeEnum type, Integer offset) {
        FlowConfigDTO config = flowConfigService.getByType(type);
        List<FlowStatusDTO> steps = config.getStatusList();
        for (FlowStatusDTO step : steps) {
            if (step.getFlowStatus() == (audit.getStep() + offset)) {
                return step;
            }
        }
        return null;
    }

    private FlowTypeEnum toType(Progress progress) {
        if (Progress.DRAWUP.equals(progress)) {
            return FlowTypeEnum.DRAWUP;
        }
        return FlowTypeEnum.valueOf(progress.name());
    }

    private FlowTypeEnum toType(AuditType auditType) {
        return FlowTypeEnum.valueOf(auditType.name());
    }

    private List<AuditFlow> getFlows(Audit audit) {
        FlowConfigDTO configDTO = flowConfigService.getByType(toType(audit.getAuditType()));
        List<AuditFlow> flows = Lists.newArrayList();
        for (FlowStatusDTO statu : configDTO.getStatusList()) {
            AuditFlow flow = new AuditFlow();
            flow.setStep(statu.getFlowStatus());
            flow.setDesc(statu.getFlowStatusDesc());
            flow.setRole(roleInfoService.getBySerialNo(statu.getRoleSerialNo()));
            //查询审核记录
            AuditRecord record = auditRecordDao.getLast(audit.getId(), statu.getFlowStatusDesc());
            if (null != record) {
                flow.setResult(record.getResult());
                flow.setUser(userInfoService.getUserById(record.getAuditor().getId()));
            }
            flows.add(flow);
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
        record.setStatus("成功");
        record.setRemark("申请成功");
        record.setType("发起审核");
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
