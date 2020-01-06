/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.fund;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smile.start.model.project.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.fund.FundOpinionDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.fund.FundOpinion;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.service.fund.FundService;
import com.smile.start.web.controller.BaseController;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundOpinionController
 * @date Date : 2020年01月06日 17:03
 */
@Controller
@RequestMapping("fundOpinion")
public class FundOpinionController extends BaseController {

    /** 直投服务 */
    @Resource
    private FundService    fundService;

    /** fundOpinionDao */
    @Resource
    private FundOpinionDao fundOpinionDao;

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
        return "fund/fundOpinion";
    }

    /**
     * 根据项目获取意见
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public FundOpinion getByProject(@PathVariable Long id) {
        FundOpinion opinion = fundOpinionDao.getFundOpinion(id);
        return opinion;
    }

    /**
     * 保存
     * @param opinion
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult save(@RequestBody FundOpinion opinion) {
        LoggerUtils.info(logger, "项目ID={}", opinion.toString());
        return fundService.save(opinion);
    }

    /**
     * 流转
     * @param project
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult pass(@RequestBody Project project) {
        LoggerUtils.info(logger, "项目ID={}", project.toString());
        FundTarget target = fundService.getTarget(project.getProjectId());
        target.setProjectStep(FundStatus.SIGN_CONFIDENTIALITY);
        return fundService.modifyTarget(target);
    }
}
