/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.factoring;

import java.util.Collections;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.FactoringDetail;
import com.smile.start.model.project.Project;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.project.FactoringService;
import com.smile.start.service.project.ProjectService;
import com.smile.start.web.controller.BaseController;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FactoringStatusController
 * @date Date : 2020年01月05日 20:17
 */
@Controller
@RequestMapping("factoringStatus")
public class FactoringStatusController extends BaseController {

    /** 项目服务 */
    @Resource
    private ProjectService   projectService;

    /** 保理明细服务 */
    @Resource
    private FactoringService factoringService;

    /** 审核服务 */
    @Resource
    private AuditService     auditService;

    /**
     * 提出申请
     * @param project
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult apply(HttpServletRequest request, @RequestBody Project project) {
        User user = getUserByToken(request);
        project.setUser(user);
        project.setItems(Collections.emptyList());
        LoggerUtils.info(logger, "project={}", FastJsonUtils.toJSONString(project));
        return projectService.updateProject(project);
    }

    /**
     * 提出申请
     * @param project
     * @return
     */
    @PostMapping("/back")
    @ResponseBody
    public BaseResult back(HttpServletRequest request, @RequestBody Project project) {
        User user = getUserByToken(request);
        project.setUser(user);
        FactoringDetail detail = factoringService.get(project.getId());
        //归档退回
        if (project.getStep() == 4) {
            if (null != detail.getRealRemittanceDay()) {
                project.setStep(3);
            } else {
                project.setStep(2);
            }
        } else if (project.getStep() == 2) {
            project.setStep(0);
        } else if (project.getStep() == 3) {
            project.setStep(1);
        } else if (project.getStep() == 1) {
            project.setStep(0);
        }
        project.setItems(Collections.emptyList());
        LoggerUtils.info(logger, "project={}", FastJsonUtils.toJSONString(project));
        return projectService.updateProject(project);
    }

    @PostMapping("/check")
    @ResponseBody
    public BaseResult canGetBack(HttpServletRequest request, @RequestBody Project project) {
        User user = getUserByToken(request);
        BaseResult result = new BaseResult();
        project.setUser(user);
        Audit audit = auditService.getAuditByProjectFlowAndType(project.getId(), AuditType.TUNEUP.name());
        if (audit != null && (audit.getStep() > 0 || audit.getStep() == -1)) {
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
        }
        return result;
    }
}
