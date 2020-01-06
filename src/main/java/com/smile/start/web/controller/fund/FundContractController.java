/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.fund;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.web.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.fund.FundItemService;
import com.smile.start.service.fund.FundService;

/**
 * 合同签署上传
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundContractController
 * @date Date : 2019年11月04日 18:28
 */
@Controller
@RequestMapping("fundContract")
public class FundContractController extends BaseController {

    /** 直投服务 */
    @Resource
    private FundService     fundService;

    /** fundItemService */
    @Resource
    private FundItemService fundItemService;

    /**
     * 页面路由
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return "fund/fundContract";
    }

    /**
     * 保存上传附件
     * @param items
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult save(@RequestBody List<ProjectItem> items) {
        LoggerUtils.info(logger, "请求参数:{}", FastJsonUtils.toJSONString(items));
        return fundItemService.save(FundStatus.CONTRACT_SIGN, items);
    }

    /**
     * 保存上传附件
     * @param items
     * @return
     */
    @PostMapping("signed")
    @ResponseBody
    public BaseResult saveSigned(@RequestBody List<ProjectItem> items) {
        LoggerUtils.info(logger, "请求参数:{}", FastJsonUtils.toJSONString(items));
        return fundItemService.save(FundStatus.PAYMENT, items);
    }

    /**
     * 提交审核
     * @param request 
     * @param project
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult pass(HttpServletRequest request, @RequestBody BaseProject<FundTarget> project) {
        User user = getUserByToken(request);
        project.setOperator(user);
        project.getDetail().setProjectStep(FundStatus.CONTRACT_AUDIT);
        LoggerUtils.info(logger, "项目ID={}", project.toString());
        return fundService.createAudit(project, AuditType.CONTRACT_SIGN);
    }
}
