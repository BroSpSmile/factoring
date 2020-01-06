/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.fund;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.ExportReportExcel;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.integration.tianyan.model.CompanyInfo;
import com.smile.start.mapper.FundMapper;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.enums.project.ProjectKind;
import com.smile.start.model.fund.FundExcelInfo;
import com.smile.start.model.fund.FundInfos;
import com.smile.start.model.fund.FundProject;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.BaseProjectQuery;
import com.smile.start.service.fund.FundService;
import com.smile.start.web.controller.BaseController;

/**
 * 直投Controller
 *
 * @author smile.jing
 * @version $Id: FundController.java, v 0.1 2019年8月10日 下午8:33:21 smile.jing Exp $
 */
@Controller
@RequestMapping("/fund")
public class FundController extends BaseController {
    private String[]    header = { "项目编号", "公司名称", "项目类型", "注册资本", "实收资本", "注册地址", "子基金管理公司", "认缴出资", "实缴出资", "占股", "经营范围", "法定代表人", "成立日期", "投出日期", "每股价格(元)", "股份数(万股)" };

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
     * 暂停项目
     *
     * @param project
     * @return
     */
    @PostMapping("suspend")
    @ResponseBody
    public BaseResult suspend(@RequestBody BaseProject<FundTarget> project) {
        LoggerUtils.info(logger, "更新投资标的={}", project.toString());
        return fundService.suspend(project);
    }

    /**
     * 重启
     *
     * @param project
     * @return
     */
    @PostMapping("restart")
    @ResponseBody
    public BaseResult restart(@RequestBody BaseProject<FundTarget> project) {
        LoggerUtils.info(logger, "更新投资标的={}", project.toString());
        return fundService.restart(project);
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
     * 报表下载
     * @param query
     */
    @GetMapping("download")
    @ResponseBody
    public ResponseEntity<byte[]> download(@RequestParam String query) throws IOException {
        query = decode(query);
        PageRequest<BaseProjectQuery<FundTarget>> target = FastJsonUtils.fromJSONString(query, new TypeReference<PageRequest<BaseProjectQuery<FundTarget>>>() {
        });
        PageInfo<FundProject> projects = fundService.queryTargets(target);
        List<FundExcelInfo> infos = FundMapper.mapper(projects.getList());
        HSSFWorkbook book = ExportReportExcel.creatExcel("直投项目报表", header, infos, "yyyy-MM-dd");
        return export("直投项目报表", book);
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

    /**
     * 查询公司信息
     * @param name
     * @return
     */
    @GetMapping("/company/{name}")
    @ResponseBody
    public CompanyInfo querCompanyInfo(@PathVariable String name) {
        FundTarget target = new FundTarget();
        target.setCompanyFullName(name);
        return fundService.query(target);
    }

}
