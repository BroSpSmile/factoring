/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.commons.MoneyToChineseUtil;
import com.smile.start.web.controller.BaseController;

/**
 * 通用工具API
 * @author smile.jing
 * @version $Id: CommonUtilsController.java, v 0.1 Feb 25, 2019 6:42:59 PM smile.jing Exp $
 */
@RestController
@RequestMapping("/common")
public class CommonUtilsController extends BaseController {

    /**
     * 转换大写金额
     * @param money
     * @return
     */
    @GetMapping("/chinese/{money}")
    public String toChinese(@PathVariable String money) {
        if (StringUtils.isBlank(money)) {
            return StringUtils.EMPTY;
        }
        LoggerUtils.info(logger, "请求转换金额={}", money);
        return MoneyToChineseUtil.convert(MoneyToChineseUtil.moneyFormat(money));
    }
}
