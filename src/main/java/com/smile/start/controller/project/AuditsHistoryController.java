/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.project;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditParam;
import com.smile.start.service.audit.AuditService;

/**
 * 
 * @author smile.jing
 * @version $Id: AuditsController.java, v 0.1 Mar 3, 2019 2:05:37 AM smile.jing Exp $
 */
@Controller
@RequestMapping("/auditsHistory")
public class AuditsHistoryController extends BaseController {

    /** auditService */
    @Resource
    private AuditService auditService;

    /**
     * 页面索引
     * @return
     */
    @GetMapping
    public String index() {
        return "audit/auditsHistory";
    }

    /**
     * 分页查询
     * @param query
     * @return
     */
    @PostMapping("/queryHistory")
    @ResponseBody
    public PageInfo<Audit> queryHistory(HttpServletRequest request, @RequestBody PageRequest<AuditParam> query) {
        User user = getUserByToken(request);
        query.getCondition().setAudit(user);
        LoggerUtils.info(logger, "查询参数={}", FastJsonUtils.toJSONString(query));
        PageInfo<Audit> result = auditService.queryHistory(query);
        return result;
    }
}
