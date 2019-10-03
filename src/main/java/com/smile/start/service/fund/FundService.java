/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.fund;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.fund.FundTargetQuery;

/**
 * 直投服务
 * @author smile.jing
 * @version $Id: FundService.java, v 0.1 2019年8月10日 下午8:16:03 smile.jing Exp $
 */
public interface FundService {

    /**
     * 创建直投标的
     * @param target
     * @return
     */
    BaseResult createTarget(FundTarget target);

    /**
     * 更新直投标的
     * @param target
     * @return
     */
    BaseResult modifyTarget(FundTarget target);
    

    /**
     * 分页查询直投标的信息
     * @param query
     * @return
     */
    PageInfo<FundTarget> queryTargets(PageRequest<FundTargetQuery> query);

    /**
     * 根据项目ID查询标的
     * @param target
     * @return
     */
    FundTarget getTarget(String projectId);
}
