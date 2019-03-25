/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.finance;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.project.FactoringDetail;
import com.smile.start.model.project.Installment;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectForView;
import com.smile.start.service.finance.FinanceService;
import com.smile.start.service.project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：xioutman
 * @description：财务管理
 * @date ：Created in 2019/2/4 11:10
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/financeManage")
public class FinanceManageController extends BaseController {

    /**
     * 项目服务
     */
    @Resource
    private ProjectService projectService;

    /**
     * 财务服务
     */
    @Resource
    private FinanceService financeService;

    /**
     * 跳转
     *
     * @return
     */
    @GetMapping
    public String index() {
        return "finance/financemanage";
    }

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public PageInfo<ProjectForView> queryByParam(@RequestBody PageRequest<Project> query) {

        //project.step  LOANEN(9, "放款操作") 可以查询 begin
        //project.step END(12, "完结") 可以查询 begin
        SingleResult<Map<String, Object>> singleResult =
            new SingleResult<Map<String, Object>>();
        LoggerUtils.info(logger, "查询请求参数={}", FastJsonUtils.toJSONString(query));
        List<ProjectForView> projectForViewList = Lists.newArrayList();
        PageInfo<Project> projectPageInfo = financeService.queryPageProject(query);
        if (null != projectPageInfo) {
            List<Project> list = projectPageInfo.getList();
            if (null != list && !list.isEmpty()) {
                for (Project project : list) {
                    projectForViewList.add(getProjectForView(project));
                }
            }
        }
        PageInfo<ProjectForView> result = new PageInfo<>(projectForViewList);
        return result;
    }

    private ProjectForView getProjectForView(Project project) {
        ProjectForView projectForView = new ProjectForView();
        projectForView.setId(project.getId());
        projectForView.setProjectId(project.getProjectId());
        projectForView.setProjectName(project.getProjectName());
        projectForView.setUsername(project.getUser().getUsername());
        FactoringDetail detail = project.getDetail();
        projectForView.setLoanAuditPassTime(detail.getLoanAuditPassTime());
        projectForView.setReceivable(detail.getReceivable());
        projectForView.setDropAmount(detail.getLoanInstallments()
            .stream()
            .map(installment -> installment.getAmount())
            .collect(Collectors.toList()));
        projectForView.setDropDates(Optional.of(detail.getLoanInstallments()).orElse(new ArrayList<Installment>())
            .stream().map(installment -> installment.getInstallmentDate()).map(date -> DateUtil.getWebDateString(date))
            .collect(Collectors.toList()));
        projectForView.setReturnAmount(detail.getReturnInstallments()
            .stream()
            .map(installment -> installment.getAmount())
            .collect(Collectors.toList()));
        projectForView.setReturnDates(Optional.of(detail.getReturnInstallments()).orElse(new ArrayList<Installment>())
            .stream().map(installment -> installment.getInstallmentDate()).map(date -> DateUtil.getWebDateString(date))
            .collect(Collectors.toList()));
        projectForView.setTotalFactoringFee(detail.getTotalFactoringFee());
        projectForView.setFactoringInstallmentAmounts(Optional.of(detail.getFactoringInstallments())
            .orElse(new ArrayList<Installment>())
            .stream()
            .map(installment -> installment.getAmount())
            .collect(Collectors.toList()));
        projectForView.setFactoringInstallmentDates(detail.getFactoringInstallments()
            .stream()
            .map(installment -> installment.getInstallmentDate()).map(date -> DateUtil.getWebDateString(date))
            .collect(Collectors.toList()));
        projectForView.setFactoringInstallmentInvoiceds(detail.getFactoringInstallments()
            .stream()
            .map(installment -> installment.isInvoiced())
            .collect(Collectors.toList()));

        return projectForView;
    }
}
