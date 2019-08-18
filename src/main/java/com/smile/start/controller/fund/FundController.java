/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.fund;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.FundStatus;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.fund.FundTargetQuery;
import com.smile.start.service.fund.FundService;

/**
 * 直投Controller
 * @author smile.jing
 * @version $Id: FundController.java, v 0.1 2019年8月10日 下午8:33:21 smile.jing Exp $
 */
@Controller
@RequestMapping("/fund")
public class FundController extends BaseController {

    /** 直投服务 */
    @Resource
    private FundService fundService;

    /**
     * index
     * @return
     */
    @GetMapping
    public String index() {
        return "fund/main";
    }

    /**
     * 新增投资标的
     * @param target
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult createTarget(HttpServletRequest request, @RequestBody FundTarget target) {
        LoggerUtils.info(logger, "新增投资标的={}", target.toString());
        target.setProjectStep(FundStatus.INITIAL_CONTACT);
        target.setCreateTime(new Date());
        User user = getUserByToken(request);
        target.setOperator(user);
        return fundService.createTarget(target);
    }

    /**
     * 更新投资标的
     * @param target
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult modifyTarget(@RequestBody FundTarget target) {
        LoggerUtils.info(logger, "更新投资标的={}", target.toString());
        return fundService.modifyTarget(target);
    }

    /**
     * 分页查询
     * @param query
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public PageInfo<FundTarget> query(@RequestBody PageRequest<FundTargetQuery> query) {
        PageInfo<FundTarget> tragets=  fundService.queryTargets(query);
        return tragets;
    }

    /**
     * 根据项目ID查询项目编号
     * @param projectId
     * @return
     */
    @GetMapping("/{projectId}")
    @ResponseBody
    public FundTarget get(@PathVariable String projectId) {
        return fundService.getTarget(projectId);
    }
}
