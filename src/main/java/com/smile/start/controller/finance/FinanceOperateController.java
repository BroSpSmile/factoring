/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.finance;

import javax.annotation.Resource;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.project.Project;
import com.smile.start.service.project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.smile.start.controller.BaseController;
import com.smile.start.service.finance.FinanceService;

/**
 * 财务操作
 * @author Jacky
 * @version $Id: FinanceOperateController.java, v 0.1 2019年3月6日 下午3:23:21 Jacky Exp $
 */
@Controller
@RequestMapping("/finance")
public class FinanceOperateController extends BaseController {

    /** 项目服务 */
    @Resource
    private ProjectService projectService;

    /**  财务服务 */
    @Resource
    private FinanceService financeService;

    /**
     * 跳转
     * @return
     */
    @GetMapping
    public String index() {
        return "finance/finance";
    }

    /**
     * 分页查询
     * @param query
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public PageInfo<Project> queryByParam(@RequestBody PageRequest<Project> query) {
        LoggerUtils.info(logger, "查询请求参数={}", FastJsonUtils.toJSONString(query));
        return projectService.queryPage(query);
    }

}
