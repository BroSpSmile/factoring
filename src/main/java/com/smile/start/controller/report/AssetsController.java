/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.report;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smile.start.controller.BaseController;
import com.smile.start.model.project.Assets;
import com.smile.start.service.project.FactoringService;

/**
 * 资产报表
 * @author smile.jing
 * @version $Id: AssetsController.java, v 0.1 Feb 27, 2019 10:26:10 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/assets")
public class AssetsController extends BaseController {

    /** 保理服务 */
    @Resource
    private FactoringService factoringService;

    /**
     * 页面
     * @return
     */
    @GetMapping
    public String index() {
        return "report/assets";
    }

    /**
     * 报表
     * @param month
     * @return
     */
    @GetMapping("/{month}")
    @ResponseBody
    public List<Assets> getAssets(@PathVariable String month) {
        return factoringService.getAssets(month);
    }
}
