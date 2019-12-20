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
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.fund.FundItemService;

/**
 * 支付打款
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: SasacAuditController
 * @date Date : 2019年11月04日 18:11
 */
@Controller
@RequestMapping("/payment")
public class PaymentController extends BaseController {

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
        return "fund/payment";
    }

    /**
     * 保存
     * @param items
     * @return
     */
    @PostMapping("/{status}")
    @ResponseBody
    public BaseResult save(HttpServletRequest request, @RequestBody List<ProjectItem> items, @PathVariable FundStatus status) {
        LoggerUtils.info(logger, "请求参数:{}", FastJsonUtils.toJSONString(items));
        User user = getUserByToken(request);
        return fundItemService.saveAndAudit(status, items, AuditType.PAYMENT, user);
    }

}
