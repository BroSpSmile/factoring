/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.service.audit;

import java.util.Optional;

import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.BaseProject;

/**
 * 审核创建服务
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AuditCreateService
 * @date Date : 2019年11月03日 15:08
 */
public interface AuditCreateService {

    /**
     * 创建审核流程
     * @param project 项目
     * @param type 审核类型
     * @return 审核结果
     */
    <T> SingleResult<Audit> apply(BaseProject<T> project, AuditType type);

    /**
     * 获取审核信息
     * @param project 项目
     * @param type 审核类型
     * @return 审核内容
     */
    <T> Optional<Audit> findAudit(BaseProject<T> project, AuditType type);
}
