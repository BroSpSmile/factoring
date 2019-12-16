/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.fund.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.BaseProjectDao;
import com.smile.start.dao.FundTargetDao;
import com.smile.start.dao.InstallmentDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.enums.ProjectItemType;
import com.smile.start.model.enums.ProjectKind;
import com.smile.start.model.fund.FundInfos;
import com.smile.start.model.fund.FundProject;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.*;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditCreateService;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.fund.FundService;
import com.smile.start.service.project.IdGenService;
import com.smile.start.service.project.ProjectItemSerivce;

/**
 * 实现
 *
 * @author smile.jing
 * @version $Id: FundServiceImpl.java, v 0.1 2019年8月10日 下午8:20:22 smile.jing
 * Exp$
 */
@Service
public class FundServiceImpl extends AbstractService implements FundService {

    /** projectDao */
    @Resource
    private BaseProjectDao     baseProjectDao;

    /** DAO */
    @Resource
    private FundTargetDao      fundTargetDao;

    /**  */
    @Resource
    private InstallmentDao     installmentDao;

    /** 附件服务 */
    @Resource
    private ProjectItemSerivce itemService;

    /** ID生成器 */
    @Resource
    private IdGenService       idGenService;

    /** 用户信息服务 */
    @Resource
    private UserInfoService    userInfo;

    /** 审核创建服务 */
    @Resource
    private AuditCreateService auditCreateService;

    /**
     * @see com.smile.start.service.fund.FundService#createTarget(BaseProject)
     */
    @Override
    @Transactional
    public BaseResult createTarget(BaseProject<FundTarget> project) {
        project.setProjectId(idGenService.genId(ProjectKind.FUND));
        long projectEffct = baseProjectDao.insert(project);
        LoggerUtils.info(logger, "新增项目影响行:{}", projectEffct);
        FundTarget target = project.getDetail();
        target.setProjectId(project.getProjectId());
        target.setProjectName(project.getProjectName());
        long effect = fundTargetDao.insert(project.getDetail());
        LoggerUtils.info(logger, "新增直投标的:{}结果={}", project.getProjectId(), effect);
        BaseResult result = toResult(effect);
        if (result.isSuccess() && !CollectionUtils.isEmpty(project.getItems())) {
            project.getItems().forEach(item -> {
                item.setProjectId(project.getId());
                item.setItemType(ProjectItemType.PROJECT);
            });
            itemService.save(project.getItems());
        }
        return toResult(effect);
    }

    /**
     * @see com.smile.start.service.fund.FundService#modifyTarget(BaseProject)
     */
    @Override
    public BaseResult modifyTarget(BaseProject<FundTarget> project) {
        FundTarget target = project.getDetail();
        if (null == target) {
            target = new FundTarget();
        }
        if (project.getId() > 0 && null == target.getId()) {
            target.setId(fundTargetDao.getFundId(project.getId()));
        }
        int effect = fundTargetDao.updateTarget(target);
        LoggerUtils.info(logger, "修改直投标的:{}结果={}", target.getProjectId(), target.toString());
        if (effect > 0 && !CollectionUtils.isEmpty(project.getItems())) {
            project.getItems().forEach(item -> {
                item.setProjectId(project.getId());
                item.setItemType(ProjectItemType.PROJECT);
            });
            itemService.save(project.getItems());
        }
        return toResult(effect);
    }

    /**
     * 更新直投标的
     *
     * @param target
     * @return
     */
    @Override
    public BaseResult modifyTarget(FundTarget target) {
        int effect = fundTargetDao.updateTarget(target);
        LoggerUtils.info(logger, "修改直投标的:{}结果={}", target.getProjectId(), target.toString());
        return toResult(effect);
    }

    /**
     * @see com.smile.start.service.fund.FundService#queryTargets(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<FundProject> queryTargets(PageRequest<BaseProjectQuery<FundTarget>> query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), "id desc");
        List<FundProject> projects = fundTargetDao.queryFundTarget(query.getCondition());
        for (FundProject project : projects) {
            FundTarget target = project.getDetail();
            setUser(target);
        }
        PageInfo<FundProject> result = new PageInfo<>(projects);
        return result;
    }

    /**
     * 根据项目ID获取项目
     *
     * @param projectId
     * @return
     */
    @Override
    public FundProject getProject(String projectId) {
        return null;
    }

    /**
     * @see com.smile.start.service.fund.FundService#getTarget(java.lang.String)
     */
    @Override
    public FundTarget getTarget(String projectId) {
        FundTarget target = new FundTarget();
        target.setProjectId(projectId);
        FundTarget fundTarget = fundTargetDao.getByProjectId(target);
        List<Installment> installments = installmentDao.queryByDetail(fundTarget.getId(), ProjectKind.INVESTMENT);
        for (Installment installment : installments) {
            InstallmentItem item = installmentDao.getInstallmentItem(installment);
            installment.setItem(item);
        }
        fundTarget.setLoanInstallments(installments);
        setUser(fundTarget);
        return fundTarget;
    }

    /**
     * 创建审核信息
     *
     * @param proeject 项目
     * @param type     审核类型
     * @return 审核信息
     */
    @Override
    @Transactional
    public SingleResult<Audit> createAudit(BaseProject<FundTarget> proeject, AuditType type) {
        this.modifyTarget(proeject);
        return auditCreateService.apply(proeject, type);
    }

    /**
     * 获取直投信息
     *
     * @return 直投信息
     */
    @Override
    public List<FundInfos> getFundInfos() {
        return fundTargetDao.queryFundInfos();
    }

    /**
     * 设置用户
     *
     * @param target
     */
    private void setUser(FundTarget target) {
        if (null != target.getMemberA() && null != target.getMemberA().getId()) {
            target.setMemberA(userInfo.getUserById(target.getMemberA().getId()));
        }
        if (null != target.getMemberB() && null != target.getMemberB().getId()) {
            target.setMemberB(userInfo.getUserById(target.getMemberB().getId()));
        }
    }

}
