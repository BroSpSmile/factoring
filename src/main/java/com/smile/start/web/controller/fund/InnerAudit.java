/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.fund;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.BaseProject;
import com.smile.start.service.fund.FundService;
import com.smile.start.web.controller.BaseController;

/**
 * 内核页面
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: InnerAudit
 * @date Date : 2019年10月14日 22:24
 */
@Controller
@RequestMapping("innerAudit")
public class InnerAudit extends BaseController {

    /** 直投服务 */
    @Resource
    private FundService fundService;

    /**
     *
     * @return
     */
    @PostMapping
    @ResponseBody
    public SingleResult<Audit> index(HttpServletRequest request, @RequestBody BaseProject<FundTarget> project) {
        User user = getUserByToken(request);
        project.setOperator(user);
        project.getDetail().setProjectStep(FundStatus.PARTMENT_AUDIT);
        LoggerUtils.info(logger, "项目ID={}", project.toString());
        return fundService.createAudit(project, AuditType.DEEP_TUNING);

    }
}
