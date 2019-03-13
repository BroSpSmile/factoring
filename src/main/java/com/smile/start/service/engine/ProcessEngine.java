/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.engine;

import java.util.List;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.StepStatus;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.StepRecord;

/**
 * 流程引擎
 * @author smile.jing
 * @version $Id: ProcessEngine.java, v 0.1 Mar 10, 2019 11:53:40 PM smile.jing Exp $
 */
public interface ProcessEngine {

    /**
     * 获取项目流程记录
     * @param project
     * @return
     */
    List<StepRecord> getRecords(Project project);

    /**
     * 流转下一业务节点
     * @param project
     * @return 最新业务节点
     */
    SingleResult<StepRecord> next(Project project);

    /**
     * 回滚到上一业务节点，同时删除当前节点状态
     * @param project
     * @return
     */
    BaseResult prev(Project project);

    /**
     * 后补业务节点
     * @param project
     * @return 最新业务节点
     */
    SingleResult<StepRecord> skip(Project project);

    /**
     * 变更业务节点状态
     * @param project
     * @param status
     * @return
     */
    SingleResult<StepRecord> changeStatus(Project project, StepStatus status);

    /**
     * 变更业务节点状态
     * @param project
     * @param status
     * @return
     */
    SingleResult<StepRecord> changeStatus(Project project, StepStatus status, Audit audit);
}
