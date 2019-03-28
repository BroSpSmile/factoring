/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.loan;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.loan.Loan;
import com.smile.start.model.project.Project;
import com.smile.start.service.loan.LoanService;

/**
 * 放款申请
 * @author smile.jing
 * @version $Id: LoanController.java, v 0.1 Feb 25, 2019 3:24:58 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/loanApply")
public class LoanApplyController extends BaseController {

    /**  */
    @Resource
    private LoanService loanService;

    /**
     * 跳转
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "立项申请项目ID={}", id);
        model.addAttribute("id", id);
        return "loan/apply";
    }

    /**
     * 根据项目ID获取项目放款信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Loan getLoan(@PathVariable Long id) {
        Project project = new Project();
        project.setId(id);
        return loanService.getLoan(project);
    }

    /**
     * 保存
     * @param loan
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult save(@RequestBody Loan loan) {
        LoggerUtils.info(logger, "放款审核请求={}", FastJsonUtils.toJSONString(loan));
        return loanService.save(loan);
    }

    /**
     * 下载
     * @param id
     */
    @GetMapping("/download")
    public void download(@PathVariable Long id) {

    }

    /**
     * 提交审核
     * @param loan
     * @return
     */
    @PostMapping("/commit")
    @ResponseBody
    public BaseResult commit(HttpServletRequest request, @RequestBody Loan loan) {
        User user = getUserByToken(request);
        loan.getProject().setUser(user);
        LoggerUtils.info(logger, "尽调申请project={}", FastJsonUtils.toJSONString(loan));
        return loanService.commit(loan);
    }
}
