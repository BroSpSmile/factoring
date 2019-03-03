/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.audit;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditParam;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.model.project.Project;

/**
 * 审核服务
 * @author smile.jing
 * @version $Id: AuditService.java, v 0.1 Mar 2, 2019 9:28:46 PM smile.jing Exp $
 */
public interface AuditService {

    /**
     * 审核申请
     * @param project 项目
     * @param user 申请人
     * @return
     */
    SingleResult<Audit> apply(Project project, User user);

    /**
     * 审核通过
     * @param record
     * @return
     */
    BaseResult pass(AuditRecord record);

    /**
     * 审核驳回
     * @param record
     * @return
     */
    BaseResult reject(AuditRecord record);

    /**
     * 查询当前用户待审核信息
     * @param param
     * @return
     */
    PageInfo<Audit> query(PageRequest<AuditParam> param);

    /**
     * 获取审核明细
     * @param id
     * @return
     */
    Audit getAudit(Long id);
}
