/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.fund.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.BaseProjectDao;
import com.smile.start.dao.InstallmentDao;
import com.smile.start.dao.fund.FundOpinionDao;
import com.smile.start.dao.fund.FundTargetDao;
import com.smile.start.dao.project.ProjectStepDao;
import com.smile.start.event.CompanyEvent;
import com.smile.start.integration.tianyan.CompanyClient;
import com.smile.start.integration.tianyan.model.CompanyInfo;
import com.smile.start.integration.tianyan.model.TianyanResult;
import com.smile.start.mapper.ProjectMapper;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.Step;
import com.smile.start.model.enums.StepStatus;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.enums.project.ProjectItemType;
import com.smile.start.model.enums.project.ProjectKind;
import com.smile.start.model.fund.FundInfos;
import com.smile.start.model.fund.FundOpinion;
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

    /** 项目步骤DAO */
    @Resource
    private ProjectStepDao     projectStepDao;

    /** fundOpinionDao */
    @Resource
    private FundOpinionDao     fundOpinionDao;

    private DecimalFormat      df = new DecimalFormat("#.00");

    /** 审核创建服务 */
    @Resource
    private AuditCreateService auditCreateService;

    /** 天眼查客户端 */
    @Resource
    private CompanyClient      companyClient;

    /** */
    @Resource
    private ApplicationContext applicationContext;

    /**
     * @see com.smile.start.service.fund.FundService#createTarget(BaseProject)
     */
    @Override
    @Transactional
    public BaseResult createTarget(BaseProject<FundTarget> project) {
        project.setProjectId(idGenService.genId(ProjectKind.INVESTMENT));
        long projectEffct = baseProjectDao.insert(project);
        LoggerUtils.info(logger, "新增项目影响行:{}", projectEffct);
        FundTarget target = project.getDetail();
        target.setProjectId(project.getProjectId());
        target.setProjectName(project.getProjectName());
        target.setMemberBStr(toStr(target.getMemberBs()));
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
        target.setMemberBStr(toStr(target.getMemberBs()));
        int effect = fundTargetDao.updateTarget(target);
        LoggerUtils.info(logger, "修改直投标的:{}结果={}", target.getProjectId(), target.toString());
        if (effect > 0 && !CollectionUtils.isEmpty(project.getItems())) {
            for (ProjectItem item : project.getItems()) {
                item.setProjectId(project.getId());
                item.setItemType(ProjectItemType.PROJECT);
                if (FundStatus.POST_INVESTMENT.equals(target.getProjectStep())) {
                    item.setItemType(ProjectItemType.POST_INVESTMENT);
                }
            }

            itemService.save(project.getItems());
        }
        return toResult(effect);
    }

    /**
     * 暂停项目
     *
     * @param project 项目
     * @return
     */
    @Override
    public BaseResult suspend(BaseProject<FundTarget> project) {
        FundTarget target = project.getDetail();
        //处理暂停 重启项目
        if (null != target) {
            StepRecord record = new StepRecord();
            record.setProject(ProjectMapper.mapper(project));
            Audit audit = new Audit();
            audit.setId(Long.valueOf(target.getProjectStep().getIndex()));
            record.setAudit(audit);
            record.setStep(Step.APPROVAL);
            record.setStatus(StepStatus.BEGIN);
            record.setRemark(project.getRemark());
            projectStepDao.insert(record);
        }
        target.setProjectStep(FundStatus.SUSPEND);
        return this.modifyTarget(project);
    }

    /**
     * 重启项目
     *
     * @param project 项目
     * @return
     */
    @Override
    public BaseResult restart(BaseProject<FundTarget> project) {
        StepRecord record = projectStepDao.getLastStep(project.getId());
        if (record == null) {
            return new BaseResult();
        }
        FundStatus status = FundStatus.getByIndex(record.getAudit().getId().intValue());
        FundTarget target = project.getDetail();
        target.setProjectStep(status);
        return this.modifyTarget(target);
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
     * 保存项目初步意见表
     *
     * @param opinion
     * @return
     */
    @Override
    public BaseResult save(FundOpinion opinion) {
        if (opinion.getId() != null) {
            int effect = fundOpinionDao.update(opinion);
            return toResult(effect);
        } else {
            long effect = fundOpinionDao.insert(opinion);
            return toResult(effect);
        }
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
            target.setMemberBs(toUser(target.getMemberBStr()));
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
        fundTarget.setMemberBs(toUser(fundTarget.getMemberBStr()));
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
     * 检查公司信息
     *
     * @return
     */
    @Override
    public void checkCompaniesInfo() {
        List<FundTarget> targets = fundTargetDao.getNotEndFund();
        for (FundTarget target : targets) {
            CompanyInfo info = this.query(target);
            if (null != info) {
                Map<String, String> def = Maps.newHashMap();
                if (!StringUtils.equalsIgnoreCase(info.getName(), target.getCompanyFullName())) {
                    def.put("companyFullName", "公司全称");
                }
                if (!StringUtils.equalsIgnoreCase(info.getLegalPersonName(), target.getControllerOwner())) {
                    def.put("controllerOwner", "实际控制人");
                }
                ;
                if (!StringUtils.equalsIgnoreCase(info.getRegCapital(), df.format(target.getRegisteredCapital()))) {
                    def.put("registeredCapital", "注册资本");
                }
                if (!StringUtils.equalsIgnoreCase(info.getIndustry(), target.getIndustry())) {
                    def.put("industry", "所属行业");
                }
                if (!StringUtils.equalsIgnoreCase(info.getEstiblishTime(), target.getRegisterTime())) {
                    def.put("registerTime", "注册时间");
                }
                if (!def.isEmpty()) {
                    //存在字段变化通知负责人
                    applicationContext.publishEvent(new CompanyEvent(this, target, def));
                }
            }
        }
    }

    /**
     * 天眼查查询企业信息
     *
     * @param target
     * @return
     */
    @Override
    public CompanyInfo query(FundTarget target) {
        TianyanResult<CompanyInfo> result = companyClient.query(null, target.getCompanyFullName());
        if (result.getErrorCode() == 0) {
            return result.getResult();
        }
        return null;
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
        if (null != target.getMemberBs() && !target.getMemberBs().isEmpty()) {
            List<User> users = Lists.newArrayListWithCapacity(target.getMemberBs().size());
            for (int i = 0; i < target.getMemberBs().size(); i++) {
                users.add(userInfo.getUserById(target.getMemberBs().get(i).getId()));
            }
            target.setMemberBs(users);
        }

    }

}
