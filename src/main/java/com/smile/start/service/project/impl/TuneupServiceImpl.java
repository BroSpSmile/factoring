/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.project.Progress;
import com.smile.start.model.project.Project;
import com.smile.start.service.AbstractService;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.project.ProjectService;
import com.smile.start.service.project.TuneupService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: TuneupServiceImpl.java, v 0.1 Mar 5, 2019 12:46:08 PM smile.jing Exp $
 */
@Service
public class TuneupServiceImpl extends AbstractService implements TuneupService {

    /** projectService */
    @Resource
    private ProjectService projectService;

    /** auditService */
    @Resource
    private AuditService   auditService;

    /** processEngine */
    @Resource
    private ProcessEngine  processEngine;

    /** 
     * @see com.smile.start.service.project.TuneupService#tuneupApply(com.smile.start.model.project.Project)
     */
    @Override
    @Transactional
    public BaseResult tuneupApply(Project project) {
        project.setProgress(Progress.TUNEUP);
        project.setStep(1);
        return processEngine.next(project, true);
    }

}
