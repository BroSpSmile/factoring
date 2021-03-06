/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.combobox;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.smile.start.dao.user.UserDao;
import com.smile.start.model.auth.User;
import com.smile.start.model.dto.BankInfoDTO;
import com.smile.start.model.enums.*;
import com.smile.start.model.enums.audit.AuditResult;
import com.smile.start.model.enums.audit.AuditType;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.enums.project.Progress;
import com.smile.start.model.enums.project.ProjectModel;
import com.smile.start.service.common.BankInfoService;
import com.smile.start.web.controller.BaseController;

/**
 * 
 * @author smile.jing
 * @version $Id: Combobox.java, v 0.1 Jan 8, 2019 9:17:15 PM smile.jing Exp $
 */
@RestController
@RequestMapping("/combo")
public class Combobox extends BaseController {

    /** userDao */
    @Resource
    private UserDao         userDao;

    @Resource
    private BankInfoService bankInfoService;

    /**
     * 
     * @return
     */
    @RequestMapping("/progress")
    public List<Item> getProgress() {
        Progress[] enums = Progress.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
     * 项目归档申请
     * @return
     */
    @RequestMapping("/filingProgress")
    public List<Item> getFilingProgress() {
        FilingProgress[] enums = FilingProgress.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
    * 项目归档申请
    * @return
    */
    @RequestMapping("/filingSubProgress")
    public List<Item> getFilingSubProgress() {
        FilingSubProgress[] enums = FilingSubProgress.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
     * 
     * @return
     */
    @RequestMapping("/meetingStatus")
    public List<Item> getMeetingStatus() {
        MeetingStatus[] enums = MeetingStatus.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
     * 审核类型
     * @return
     */
    @RequestMapping("/auditType")
    public List<Item> getAuditType() {
        AuditType[] enums = AuditType.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
     * 审核结果
     * @return
     */
    @RequestMapping("auditResult")
    public List<Item> getAuditResult() {
        AuditResult[] enums = AuditResult.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
     * 
     * @return
     */
    @RequestMapping("/users")
    public List<Item> getUsers() {
        List<User> users = userDao.findAll();
        List<Item> items = Lists.newArrayListWithCapacity(users.size());
        users.forEach(e -> items.add(new Item(String.valueOf(e.getId()), e.getUsername())));
        return items;
    }

    /**
     * 
     * @return
     */
    @RequestMapping("/meetingKind")
    public List<Item> getMeetingKind() {
        MeetingKind[] enums = MeetingKind.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
     * 
     * @return
     */
    @RequestMapping("/projectModel")
    public List<Item> getProjectModel() {
        ProjectModel[] enums = ProjectModel.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
     * 项目步骤
     * @return
     */
    @RequestMapping("/steps")
    public List<Item> getSteps() {
        Step[] enums = Step.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.name(), e.getName())));
        return items;
    }

    /**
     * 
     * @return
     */
    @RequestMapping("/indexsteps")
    public List<Item> getStepValue() {
        Step[] enums = Step.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(String.valueOf(e.getIndex()), e.getName())));
        return items;
    }

    /**
     *
     * @return
     */
    @RequestMapping("/banks")
    public List<Item> getBanks() {
        List<BankInfoDTO> banks = bankInfoService.findAll();
        return banks.stream().map(bank -> new Item(String.valueOf(bank.getId()), bank.getBankFullName())).collect(Collectors.toList());
    }

    /**
    *
    * @return
    */
    @RequestMapping("/fundStatus")
    public List<Item> getFundStatus() {
        FundStatus[] enums = FundStatus.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }
}
