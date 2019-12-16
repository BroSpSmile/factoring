/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.finance;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.InstallmentType;
import com.smile.start.model.project.Project;
import com.smile.start.service.finance.FinanceService;

/**
 * @author ：xioutman
 * @description：财务操作
 * @date ：Created in 2019/2/4 11:10
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/financeOperation")
public class FinanceOperationController extends BaseController {

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
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "项目ID={}", id);
        model.addAttribute("id", id);
        return "finance/operation";
    }

    /**
     * 保存放款信息
     *
     * @param project
     * @return
     */
    @PostMapping("/saveLoanInstallment")
    @ResponseBody
    public BaseResult saveLoanInstallment(@RequestBody Project project) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        if (null != project && null != project.getDetail()) {
            return financeService.saveInstallments(project, InstallmentType.LOAN);
        }
        return result;
    }

    /**
     * 保存回款信息
     *
     * @param project
     * @return
     */
    @PostMapping("/saveReturnInstallment")
    @ResponseBody
    public BaseResult saveReturnInstallment(@RequestBody Project project) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        if (null != project && null != project.getDetail()) {
            return financeService.saveInstallments(project, InstallmentType.RETURN);
        }
        return result;
    }

    /**
     * 保存回款信息
     *
     * @param project
     * @return
     */
    @PostMapping("/saveFactoringInstallment")
    @ResponseBody
    public BaseResult saveFactoringInstallment(@RequestBody Project project) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        if (null != project && null != project.getDetail()) {
            return financeService.saveInstallments(project, InstallmentType.FACTORING);
        }
        return result;
    }
}
