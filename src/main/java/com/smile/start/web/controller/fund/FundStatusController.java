/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.fund;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.BaseProject;
import com.smile.start.service.fund.FundService;
import com.smile.start.web.controller.BaseController;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundStatusController
 * @date Date : 2020年01月06日 19:41
 */
@Controller
@RequestMapping("/fundstatus")
public class FundStatusController extends BaseController {

    /**
     * 直投服务
     */
    @Resource
    private FundService fundService;

    /**
     * 提交审核
     * @param project
     * @return
     */
    @PostMapping("/{status}")
    @ResponseBody
    public BaseResult pass(@PathVariable FundStatus status, @RequestBody BaseProject<FundTarget> project) {
        FundTarget target = fundService.getTarget(project.getProjectId());
        target.setProjectStep(status);
        project.setDetail(target);
        LoggerUtils.info(logger, "项目ID={}", project.toString());
        return fundService.modifyTarget(target);
    }
}
