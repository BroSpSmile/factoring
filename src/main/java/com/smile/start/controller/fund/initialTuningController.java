/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.fund;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.fund.FundTargetItem;
import com.smile.start.service.fund.FundItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;

import java.util.List;

/**
 * 初步尽调
 * @author smile.jing
 * @version $Id: initialTuningController.java, v 0.1 2019年9月1日 下午9:55:40 smile.jing Exp $
 */
@Controller
@RequestMapping("initialTuning")
public class initialTuningController extends BaseController {

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
    @PostMapping
    @ResponseBody
    public BaseResult save(@RequestBody List<FundTargetItem> items) {
        LoggerUtils.info(logger, "请求参数:{}", FastJsonUtils.toJSONString(items));
        return fundItemService.save(items);
    }
}
