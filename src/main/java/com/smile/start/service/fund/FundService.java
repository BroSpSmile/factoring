/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.fund;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.smile.start.integration.tianyan.model.CompanyInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.fund.FundInfos;
import com.smile.start.model.fund.FundOpinion;
import com.smile.start.model.fund.FundProject;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.BaseProjectQuery;

/**
 * 直投服务
 *
 * @author smile.jing
 * @version $Id: FundService.java, v 0.1 2019年8月10日 下午8:16:03 smile.jing Exp $
 */
public interface FundService {

    /**
     * 创建直投标的
     *
     * @param project 项目
     * @return
     */
    BaseResult createTarget(BaseProject<FundTarget> project);

    /**
     * 更新直投标的
     *
     * @param project 项目
     * @return
     */
    BaseResult modifyTarget(BaseProject<FundTarget> project);

    /**
     * 暂停项目
     *
     * @param project 项目
     * @return
     */
    BaseResult suspend(BaseProject<FundTarget> project);

    /**
     * 重启项目
     *
     * @param project 项目
     * @return
     */
    BaseResult restart(BaseProject<FundTarget> project);

    /**
     * 更新直投标的
     * @param target
     * @return
     */
    BaseResult modifyTarget(FundTarget target);

    /**
     * 保存项目初步意见表
     * @param opinion
     * @return
     */
    BaseResult save(FundOpinion opinion);

    /**
     * 分页查询直投标的信息
     *
     * @param query 查找参数
     * @return
     */
    PageInfo<FundProject> queryTargets(PageRequest<BaseProjectQuery<FundTarget>> query);

    /**
     * 根据项目ID获取项目
     * @param projectId 项目ID
     * @return
     */
    FundProject getProject(String projectId);

    /**
     * 根据项目ID查询标的
     *
     * @param projectId 项目ID
     * @return
     */
    FundTarget getTarget(String projectId);

    /**
     * 创建审核信息
     * @param proeject 项目
     * @param type 审核类型
     * @return 审核信息
     */
    SingleResult<Audit> createAudit(BaseProject<FundTarget> proeject, AuditType type);

    /**
     * 获取直投信息
     * @return 直投信息
     */
    List<FundInfos> getFundInfos();

    /**
     * 项目检查
     * @return
     */
    void checkCompaniesInfo();

    /**
     * 天眼查查询企业信息
     * @param target
     * @return
     */
    CompanyInfo query(FundTarget target);
}
