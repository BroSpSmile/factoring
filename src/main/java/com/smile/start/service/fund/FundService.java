/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.fund;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.fund.FundProject;
import com.smile.start.model.fund.FundTarget;
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
     * @param project
     * @return
     */
    BaseResult createTarget(BaseProject<FundTarget> project);

    /**
     * 更新直投标的
     *
     * @param project
     * @return
     */
    BaseResult modifyTarget(BaseProject<FundTarget> project);


    /**
     * 分页查询直投标的信息
     *
     * @param query
     * @return
     */
    PageInfo<FundProject> queryTargets(PageRequest<BaseProjectQuery<FundTarget>> query);

    /**
     * 根据项目ID获取项目
     * @param projectId
     * @return
     */
    FundProject getProject(String projectId);

    /**
     * 根据项目ID查询标的
     *
     * @param projectId
     * @return
     */
    FundTarget getTarget(String projectId);
}
