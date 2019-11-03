/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.service.audit.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.smile.start.dao.AuditDao;
import com.smile.start.dao.AuditRecordDao;
import com.smile.start.dao.AuditRecordItemDao;
import com.smile.start.dao.FlowConfigDao;
import com.smile.start.mapper.ProjectMapper;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.AuditResult;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.project.*;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditCreateService;
import com.smile.start.service.audit.AuditService;

/**
 * 实现
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AuditCreateServiceImpl
 * @date Date : 2019年11月03日 15:17
 */
@Service
public class AuditCreateServiceImpl extends AbstractService implements AuditCreateService {

    /** flowConfigDao */
    @Resource
    private FlowConfigDao      flowConfigDao;

    /** auditDao */
    @Resource
    private AuditDao           auditDao;

    /** auditRecordDao */
    @Resource
    private AuditRecordDao     auditRecordDao;

    /** auditRecordItemDao */
    @Resource
    private AuditRecordItemDao auditRecordItemDao;

    /** 审核服务 */
    @Resource
    private AuditService       auditService;

    /**
     * 创建审核流程
     *
     * @param project 项目
     * @param type    审核类型
     * @return 审核结果
     */
    @Override
    @Transactional
    public <T> SingleResult<Audit> apply(BaseProject<T> project, AuditType type) {
        Audit audit = initAudit(project, type);
        Optional<Audit> exist = this.findAudit(project, type);
        if (exist.isPresent()) {
            BaseResult result = auditService.pass(createRecord(audit));
            return toResult(result.isSuccess(), exist.get());
        } else {
            long effect = auditDao.insert(audit);
            if (effect > 0) {
                this.addRecorde(audit);
            }
            return toResult(effect > 0, audit);
        }
    }

    /**
     * 获取审核信息
     *
     * @param project 项目
     * @param type    审核类型
     * @return 审核内容
     */
    @Override
    public <T> Optional<Audit> findAudit(BaseProject<T> project, AuditType type) {
        Audit audit = auditDao.getByProjectAndType(project.getId(), type.getCode());
        return Optional.ofNullable(audit);
    }

    /**
     * 初始化审核对象
     * @param project 项目
     * @param type 审核类型
     * @return
     */
    private <T> Audit initAudit(BaseProject<T> project, AuditType type) {
        Audit audit = new Audit();
        audit.setApplicant(project.getOperator());
        audit.setProject(ProjectMapper.mapper(project));
        audit.setCreateTime(new Date());
        audit.setStep(0);
        audit.setAuditType(type);
        this.getNextFlow(type).ifPresent(flow -> {
            audit.setStep(flow.getStep());
            audit.setRole(flow.getRole());
            audit.setNextAudit(flow);
        });
        return audit;
    }

    /**
     * 获取下一审核步骤
     * @param type 审核类型
     * @return 下一审核流程
     */
    private Optional<AuditFlow> getNextFlow(AuditType type) {
        List<AuditFlow> flows = flowConfigDao.findFlows(type.getValue());
        for (AuditFlow flow : flows) {
            if (flow.getStep() == 1) {
                return Optional.of(flow);
            }
        }
        return Optional.empty();
    }

    /**
     * 添加审核记录
     * @param audit 审核内容
     * @return BaseResult
     */
    private BaseResult addRecorde(Audit audit) {
        AuditRecord record = new AuditRecord();
        record.setStatus("成功");
        record.setRemark("申请成功");
        record.setType("提出申请");
        record.setAudit(audit);
        record.setAuditor(audit.getApplicant());
        record.setResult(AuditResult.APPLY);
        record.setStatus(record.getResult().getDesc());
        record.setAuditTime(new Date());
        long effect = auditRecordDao.insert(record);
        if (effect > 0) {
            if (!CollectionUtils.isEmpty(record.getItems())) {
                for (AuditRecordItem item : record.getItems()) {
                    item.setRecord(record);
                    auditRecordItemDao.insert(item);
                }
            }
        }
        return toResult(effect > 0);
    }

    /**
     * 创建审核记录
     * @param audit 审核单
     * @return AuditRecord
     */
    private AuditRecord createRecord(Audit audit) {
        AuditRecord record = new AuditRecord();
        record.setAudit(audit);
        record.setAuditor(audit.getApplicant());
        return record;
    }
}
