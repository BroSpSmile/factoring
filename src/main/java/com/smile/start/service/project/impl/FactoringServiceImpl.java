/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smile.start.dao.FactoringDetailDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.enums.ProjectKind;
import com.smile.start.model.project.FactoringDetail;
import com.smile.start.model.project.Project;
import com.smile.start.service.AbstractService;
import com.smile.start.service.project.FactoringService;
import com.smile.start.service.project.ProjectService;

/**
 * 保理项目服务
 * @author smile.jing
 * @version $Id: FactoringServiceImpl.java, v 0.1 Feb 24, 2019 7:28:26 PM smile.jing Exp $
 */
@Service
public class FactoringServiceImpl extends AbstractService implements FactoringService {

    /** 项目服务 */
    @Resource
    private ProjectService     projectService;

    /** factoringDetailDao */
    @Resource
    private FactoringDetailDao factoringDetailDao;

    /** 
     * @see com.smile.start.service.project.FactoringService#create(com.smile.start.model.project.FactoringDetail)
     */
    @Override
    @Transactional
    public BaseResult create(FactoringDetail detail) {
        Project project = detail.getProject();
        project.setKind(ProjectKind.FACTORING);
        project.setProgress(Progress.INIT);
        BaseResult result = projectService.initProject(detail.getProject());
        if (result.isSuccess()) {
            long effect = factoringDetailDao.insert(detail);
            if (effect > 0) {
                result.setSuccess(true);
            } else {
                result.setErrorCode("VP00011001");
                result.setErrorMessage("新增项目失败,请重试!");
            }
        }
        return result;
    }

}
