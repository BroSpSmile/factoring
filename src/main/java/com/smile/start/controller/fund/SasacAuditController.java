/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.controller.fund;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.FundStatus;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.fund.FundItemService;

/**
 * 国资委/区政府审核
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: SasacAuditController
 * @date Date : 2019年11月04日 18:11
 */
@Controller
@RequestMapping("/sasacAudit")
public class SasacAuditController extends BaseController {

    /** fundItemService */
    @Resource
    private FundItemService fundItemService;

    /**
     * 页面路由
     * @return
     */
    @GetMapping("/{org}")
    public String index(HttpServletRequest request, Model model, @PathVariable String org) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "项目ID={},当前审核节点:{}", id, org);
        model.addAttribute("id", id);
        model.addAttribute("org", org);
        return "fund/sasacAudit";
    }

    /**
     * 保存
     * @param items
     * @return
     */
    @PostMapping("/{status}")
    @ResponseBody
    public BaseResult save(@RequestBody List<ProjectItem> items, @PathVariable FundStatus status) {
        LoggerUtils.info(logger, "请求参数:{}", FastJsonUtils.toJSONString(items));
        return fundItemService.save(status, items);
    }

}
