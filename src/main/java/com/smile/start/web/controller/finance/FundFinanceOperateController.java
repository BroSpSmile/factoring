/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.finance;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.service.finance.FinanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.smile.start.web.controller.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundFinanceOperateController
 * @date Date : 2019年11月10日 21:37
 */
@Controller
@RequestMapping("fundFinanceOperate")
public class FundFinanceOperateController extends BaseController {

    /**财务服务 */
    @Resource
    private FinanceService financeService;

    /**
     * 放款页面
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "项目ID={}", id);
        model.addAttribute("id", id);
        return "finance/fundOperate";
    }

    /**
     * 保存放款信息
     *
     * @param target
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public BaseResult save(@RequestBody FundTarget target) {
        LoggerUtils.info(logger, "请求参数:{}", FastJsonUtils.toJSONString(target));
        return financeService.saveFundInstallments(target);

    }
}
