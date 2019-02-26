/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.loan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.loan.Loan;

/**
 * 放款申请
 * @author smile.jing
 * @version $Id: LoanController.java, v 0.1 Feb 25, 2019 3:24:58 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/loanApply")
public class LoanApplyController extends BaseController {
    /**
     * 跳转
     * @return
     */
    @GetMapping
    public String index() {
        return "loan/apply";
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
        return new BaseResult();
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
    public BaseResult commit(@RequestBody Loan loan) {
        return new BaseResult();
    }
}
