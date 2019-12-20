/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
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
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.fund.FundItemService;

/**
 * 初步尽调
 * @author smile.jing
 * @version $Id: initialTuningController.java, v 0.1 2019年9月1日 下午9:55:40 smile.jing Exp $
 */
@Controller
@RequestMapping("initialTuning")
public class InitialTuningController extends BaseController {

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
        LoggerUtils.info(logger, "项目ID={}", id);
        model.addAttribute("id", id);
        return "fund/initialTuning";
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
