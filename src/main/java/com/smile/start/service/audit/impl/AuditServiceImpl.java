/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.audit.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditParam;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.model.project.AuditRecordItem;
import com.smile.start.model.project.Project;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditService;
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

    /** flowConfigService */
    @Resource
    private FlowConfigService  flowConfigService;

    /** 项目服务 */
    @Resource
    private ProjectService     projectService;

    /** 
     * @see com.smile.start.service.audit.AuditService#apply(com.smile.start.model.project.Project, com.smile.start.model.auth.User)
     */
    @Override
    @Transactional
    public SingleResult<Audit> apply(Project project, User user) {
        Audit audit = new Audit();
        audit.setApplicant(user);
        audit.setProject(project);
        audit.setCreateTime(new Date());
        audit.setStep(1);
        audit.setAuditType(AuditType.getByCode(project.getProgress().getCode()));
        FlowStatusDTO step = nextStep(audit, toType(project.getProgress()), 1);
        audit.setStep(step.getFlowStatus());
        //获取审核流程
        AuthRoleInfoDTO role = new AuthRoleInfoDTO();
        role.setSerialNo(step.getCheckedRoleList().get(0));
        audit.setRole(role);
        long effect = auditDao.insert(audit);
        SingleResult<Audit> result = new SingleResult<Audit>();
        if (effect > 0) {
            result.setData(audit);
            return result;
        } else {
            result.setSuccess(false);
            result.setErrorCode("VP00011001");
            result.setErrorMessage("新增失败,请重试!");
            return result;
        }
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
        return FlowTypeEnum.valueOf(progress.name());
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
        record.setRemark("审核通过");
        record.setStatus("通过");
        record.setAuditTime(new Date());
        FlowStatusDTO nextStep = nextStep(audit, toType(audit.getProject().getProgress()), 1);
        if (null != nextStep) {
            audit.setStep(nextStep.getFlowStatus());
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(nextStep.getCheckedRoleList().get(0));
            audit.setRole(role);
        } else {
            audit.setStep(-1);
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(StringUtils.EMPTY);
            audit.setRole(role);
            turnover(audit.getProject());
        }
        auditDao.updateRole(audit);
        auditRecordDao.insert(record);
        for (AuditRecordItem item : record.getItems()) {
            item.setRecord(record);
            auditRecordItemDao.insert(item);
        }
        return new BaseResult();
    }

    /**
     * 更新项目状态
     * @param project
     * @return
     */
    private BaseResult turnover(Project project) {
        project.setItems(Collections.emptyList());
        project.setProgress(Progress.getByIndex(project.getProgress().getIndex() + 1));
        return projectService.turnover(project);
    }

    /** 
     * @see com.smile.start.service.audit.AuditService#reject(com.smile.start.model.project.AuditRecord, com.smile.start.dto.AuthRoleInfoDTO)
     */
    @Override
    @Transactional
    public BaseResult reject(AuditRecord record) {
        Audit audit = getAudit(record.getAudit().getId());
        FlowStatusDTO step = nextStep(audit, toType(audit.getProject().getProgress()), 0);
        record.setResult(AuditResult.PASS);
        record.setType(step.getFlowStatusDesc());
        record.setStatus("驳回");
        record.setAuditTime(new Date());
        FlowStatusDTO nextStep = nextStep(audit, toType(audit.getProject().getProgress()), -1);
        if (null != nextStep) {
            audit.setStep(nextStep.getFlowStatus());
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(nextStep.getCheckedRoleList().get(0));
            audit.setRole(role);
        } else {
            audit.setStep(-1);
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(StringUtils.EMPTY);
            audit.setRole(role);
        }
        auditDao.updateRole(audit);
        auditRecordDao.insert(record);
        return new BaseResult();
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
        return audit;
    }

}