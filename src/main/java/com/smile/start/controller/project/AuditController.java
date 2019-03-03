/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.project;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.service.audit.AuditService;

/**
 * 立项审核
 * @author smile.jing
 * @version $Id: AuditController.java, v 0.1 Mar 2, 2019 2:28:59 PM smile.jing Exp $
 */
@Controller
@RequestMapping("audit")
public class AuditController extends BaseController {

    /** auditService */
    @Resource
    private AuditService auditService;

    /**
     * 页面路由
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "项目ID={}", id);
        model.addAttribute("id", id);
        return "project/audit";
    }

    /**
     * 获取审核
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Audit getAudit(@PathVariable Long id) {
        LoggerUtils.info(logger, "审核编号={}", id);
        Audit audit = auditService.getAudit(id);
        return audit;
    }

    /**
     * 审核通过
     * @param record
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult pass(HttpServletRequest request,@RequestBody AuditRecord record) {
        User user = getUserByToken(request);
        record.setAuditor(user);
        LoggerUtils.info(logger, "项目审核数据={}", FastJsonUtils.toJSONString(record));
        return auditService.pass(record);
    }
}
