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
import com.smile.start.dao.FundTargetDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.ProjectKind;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.fund.FundTargetItem;
import com.smile.start.model.fund.FundTargetQuery;
import com.smile.start.service.AbstractService;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.fund.FundItemService;
import com.smile.start.service.fund.FundService;
import com.smile.start.service.project.IdGenService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: FundServiceImpl.java, v 0.1 2019年8月10日 下午8:20:22 smile.jing
 *          Exp$
 */
@Service
public class FundServiceImpl extends AbstractService implements FundService {

    /** DAO */
    @Resource
    private FundTargetDao   fundTargetDao;

    /** 附件服务 */
    @Resource
    private FundItemService fundItemService;

    /** ID生成器 */
    @Resource
    private IdGenService    idGenService;

    /** 用户信息服务 */
    @Resource
    private UserInfoService userInfo;

    /**
     * @see com.smile.start.service.fund.FundService#createTarget(com.smile.start.model.fund.FundTarget)
     */
    @Override
    @Transactional
    public BaseResult createTarget(FundTarget target) {
        target.setProjectId(idGenService.genId(ProjectKind.FUND));
        long effect = fundTargetDao.insert(target);
        LoggerUtils.info(logger, "新增直投标的:{}结果={}", target.getProjectId(), effect);
        BaseResult result = toResult(effect);
        if (result.isSuccess() && !CollectionUtils.isEmpty(target.getItems())) {
            for (FundTargetItem item : target.getItems()) {
                item.setTarget(target);
                item.setItemType(target.getProjectStep());
            }
            result = fundItemService.save(target.getItems());
        }
        return toResult(effect);
    }

    /**
     * @see com.smile.start.service.fund.FundService#modifyTarget(com.smile.start.model.fund.FundTarget)
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
    public PageInfo<FundTarget> queryTargets(PageRequest<FundTargetQuery> query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), "id desc");
        List<FundTarget> targets = fundTargetDao.queryFundTarget(query.getCondition());
        for (FundTarget target : targets) {
            if (null != target.getMemberA() && null != target.getMemberA().getId()) {
                target.setMemberA(userInfo.getUserById(target.getMemberA().getId()));
            }
            if (null != target.getMemberB() && null != target.getMemberB().getId()) {
                target.setMemberB(userInfo.getUserById(target.getMemberB().getId()));
            }
        }
        PageInfo<FundTarget> result = new PageInfo<>(targets);
        return result;
    }

    /**
     * @see com.smile.start.service.fund.FundService#getTarget(java.lang.String)
     */
    @Override
    public FundTarget getTarget(String projectId) {
        FundTarget target = new FundTarget();
        target.setProjectId(projectId);
        FundTarget fundTarget = fundTargetDao.getByProjectId(target);
        if (null != fundTarget.getMemberA() && null != fundTarget.getMemberA().getId()) {
            fundTarget.setMemberA(userInfo.getUserById(fundTarget.getMemberA().getId()));
        }
        if (null != fundTarget.getMemberB() && null != fundTarget.getMemberB().getId()) {
            fundTarget.setMemberB(userInfo.getUserById(fundTarget.getMemberB().getId()));
        }
        return fundTarget;
    }

}
