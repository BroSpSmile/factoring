/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.fund;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.FundStatus;
import com.smile.start.model.enums.ProjectKind;
import com.smile.start.model.fund.FundInfos;
import com.smile.start.model.fund.FundProject;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.BaseProjectQuery;
import com.smile.start.service.fund.FundService;

/**
 * 直投Controller
 *
 * @author smile.jing
 * @version $Id: FundController.java, v 0.1 2019年8月10日 下午8:33:21 smile.jing Exp $
 */
@Controller
@RequestMapping("/fund")
public class FundController extends BaseController {

    /**
     * 直投服务
     */
    @Resource
    private FundService fundService;

    /**
     * index
     *
     * @return
     */
    @GetMapping
    public String index() {
        return "fund/main";
    }

    /**
     * 新增投资标的
     *
     * @param request
     * @param project
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult createTarget(HttpServletRequest request, @RequestBody BaseProject<FundTarget> project) {
        LoggerUtils.info(logger, "新增投资标的={}", project.toString());
        project.setKind(ProjectKind.INVESTMENT);
        project.getDetail().setProjectStep(FundStatus.INITIAL_CONTACT);
        project.setCreateTime(new Date());
        User user = getUserByToken(request);
        project.setOperator(user);
        return fundService.createTarget(project);
    }

    /**
     * 更新投资标的
     *
     * @param project
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult modifyTarget(@RequestBody BaseProject<FundTarget> project) {
        LoggerUtils.info(logger, "更新投资标的={}", project.toString());
        return fundService.modifyTarget(project);
    }

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public PageInfo<FundProject> query(@RequestBody PageRequest<BaseProjectQuery<FundTarget>> query) {
        PageInfo<FundProject> projects = fundService.queryTargets(query);
        return projects;
    }

    /**
     * 根据项目ID查询项目编号
     *
     * @param projectId
     * @return
     */
    @GetMapping("/{projectId}")
    @ResponseBody
    public FundTarget get(@PathVariable String projectId) {
        return fundService.getTarget(projectId);
    }

    /**
     * 直投信息
     * @return
     */
    @GetMapping("/infos")
    @ResponseBody
    public List<FundInfos> getAllFundInfos() {
        return fundService.getFundInfos();
    }
}
