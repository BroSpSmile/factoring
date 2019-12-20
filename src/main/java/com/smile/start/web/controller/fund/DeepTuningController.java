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
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.fund.FundItemService;

/**
 * 深入尽调
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: deepTuningController
 * @date Date : 2019年10月14日 21:29
 */
@Controller
@RequestMapping("deepTuning")
public class DeepTuningController extends BaseController {

    /** fundItemService */
    @Resource
    private FundItemService fundItemService;

    /**
     * 页面路由
     *
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "项目ID={}", id);
        model.addAttribute("id", id);
        return "fund/deepTuning";
    }

    /**
     * 保存
     * @param items
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult save(@RequestBody List<ProjectItem> items) {
        LoggerUtils.info(logger, "请求参数:{}", FastJsonUtils.toJSONString(items));
        return fundItemService.save(FundStatus.PARTMENT_AUDIT, items);
    }
}
