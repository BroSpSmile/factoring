/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.web.controller.BaseController;
import com.smile.start.web.controller.combobox.Item;
import com.smile.start.model.dto.AuthRoleInfoDTO;
import com.smile.start.model.dto.AuthUserInfoDTO;
import com.smile.start.model.dto.UserSearchDTO;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.project.EntrustAuth;
import com.smile.start.model.project.FactoringDetail;
import com.smile.start.model.project.Installment;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectForView;
import com.smile.start.service.auth.RoleInfoService;
import com.smile.start.service.entrustauth.EntrustAuthService;
import com.smile.start.service.finance.FinanceService;
import com.smile.start.service.project.ProjectService;

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

    //private static final String FINANCE_ADMIN_ROLE_CODE = "12";

    // private static final String FINANCE_ROLE_CODE = "12-1";

    @Value("${finance.admin.role.code}")
    private String             financeAdminRoleCode;

    @Value("${finance.role.code}")
    private String             financeRoleCode;

    /**
     * 项目服务
     */
    @Resource
    private ProjectService     projectService;

    /**
     * 财务服务
     */
    @Resource
    private FinanceService     financeService;

    @Resource
    private RoleInfoService    roleInfoService;

    @Resource
    private EntrustAuthService entrustAuthService;

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
    public PageInfo<ProjectForView> queryByParam(@RequestBody PageRequest<Project> query, HttpServletRequest request) {
        User user = getUserByToken(request);
        List<AuthRoleInfoDTO> roles = roleInfoService.findByUserSerialNo(user.getSerialNo());
        if (!isFinanceAdmin(roles) && !isFinanceOper(roles)) {
            PageInfo<ProjectForView> result = new PageInfo<ProjectForView>(new ArrayList<ProjectForView>());
            return result;
        }
        //        if (!isFinanceAdmin(roles)) {
        //            List<Long> projectIdList = entrustAuthService.getEntrustAuthProjectIds(user.getId(), Step.LOANEN);
        //            if (null == projectIdList || projectIdList.isEmpty()) {
        //                PageInfo<ProjectForView> result = new PageInfo<ProjectForView>(new ArrayList<ProjectForView>());
        //                return result;
        //            }
        //            query.getCondition().setProjectIdList(projectIdList);
        //        }
        LoggerUtils.info(logger, "查询请求参数={}", FastJsonUtils.toJSONString(query));
        List<ProjectForView> projectForViewList = Lists.newArrayList();
        PageInfo<Project> projectPageInfo = financeService.queryPageProject(query);
        Page<Project> page = null;
        if (null != projectPageInfo) {
            List<Project> list = projectPageInfo.getList();
            if (null != list && !list.isEmpty()) {
                for (Project project : list) {
                    projectForViewList.add(getProjectForView(project));
                }
                page = (Page<Project>) list;
            }
        }
        PageInfo<ProjectForView> result = new PageInfo<>(projectForViewList);
        if (null != page) {
            result.setPageNum(page.getPageNum());
            result.setTotal(page.getTotal());
            result.setPageSize(page.getPageSize());
        }
        return result;
    }

    /**
     * 当前账号是否是财务管理员
     *
     * @return
     */
    @GetMapping("/isFinanceAdmin")
    @ResponseBody
    public BaseResult getUser(HttpServletRequest request) {
        SingleResult<Boolean> result = new SingleResult<Boolean>();
        User user = getUserByToken(request);
        List<AuthRoleInfoDTO> roles = roleInfoService.findByUserSerialNo(user.getSerialNo());
        boolean isFinanceAdmin = isFinanceAdmin(roles);
        result.setData(isFinanceAdmin);
        return result;
    }

    private boolean isFinanceAdmin(List<AuthRoleInfoDTO> roles) {
        boolean isFinanceAdmin = false;
        for (AuthRoleInfoDTO authRoleInfoDTO : roles) {
            if (authRoleInfoDTO.getRoleCode().equals(StringUtils.isEmpty(financeAdminRoleCode) ? "12" : financeAdminRoleCode.trim())) {
                isFinanceAdmin = true;
                break;
            }
        }
        return isFinanceAdmin;
    }

    private boolean isFinanceOper(List<AuthRoleInfoDTO> roles) {
        boolean isFinanceOper = false;
        for (AuthRoleInfoDTO authRoleInfoDTO : roles) {
            if (authRoleInfoDTO.getRoleCode().equals(StringUtils.isEmpty(financeRoleCode) ? "12-1" : financeRoleCode.trim())) {
                isFinanceOper = true;
                break;
            }
        }
        return isFinanceOper;
    }

    /**
     * 获取财务人员列表
     *
     * @return
     */
    @GetMapping("/financeUserList")
    @ResponseBody
    public List<Item> getUserAll(HttpServletRequest request) {
        //ListResult<AuthUserInfoDTO> result = new ListResult<AuthUserInfoDTO>();
        PageRequest<UserSearchDTO> userSearch = new PageRequest<UserSearchDTO>();
        userSearch.setCondition(new UserSearchDTO());
        PageInfo<AuthUserInfoDTO> userListPageInfo = userInfoService.findAll(userSearch);
        List<AuthUserInfoDTO> list = userListPageInfo.getList();
        list.stream().filter(user -> {
            boolean hasRoleFinanceRole = false;
            for (AuthRoleInfoDTO authRoleInfoDTO : user.getRoleList()) {
                if (authRoleInfoDTO.getRoleCode().equals(StringUtils.isEmpty(financeRoleCode) ? "12-1" : financeRoleCode.trim())) {
                    hasRoleFinanceRole = true;
                    break;
                }
            }
            return hasRoleFinanceRole;
        }).collect(Collectors.toList());

        return list.stream().map(user -> new Item(String.valueOf(user.getId()), user.getUsername() + "," + user.getMobile() + "," + user.getFirstOrganizationName()))
            .collect(Collectors.toList());
    }

    /**
     * 归档申请保存
     *
     * @param entrustAuth
     * @return
     */
    @PostMapping("/entrustAuth")
    @ResponseBody
    public BaseResult entrustAuth(@RequestBody EntrustAuth entrustAuth, HttpServletRequest request) {
        User user = getUserByToken(request);
        entrustAuth.setFromUserId(user.getId());
        if (null == entrustAuthService.query(entrustAuth.getProjectId())) {
            entrustAuthService.insert(entrustAuth);
        } else {
            entrustAuthService.update(entrustAuth);
        }
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        result.setErrorMessage("流程流转成功");
        return result;
    }

    /**
     * 当前账号是否是财务管理员
     *
     * @return
     */
    @GetMapping("/entrustAuth/{id}")
    @ResponseBody
    public BaseResult queryEntrustAuth(@PathVariable Long id) {
        SingleResult<EntrustAuth> result = new SingleResult<EntrustAuth>();
        result.setData(entrustAuthService.query(id));
        return result;
    }

    private ProjectForView getProjectForView(Project project) {
        ProjectForView projectForView = new ProjectForView();
        projectForView.setId(project.getId());
        projectForView.setProjectId(project.getProjectId());
        projectForView.setProjectName(project.getProjectName());
        projectForView.setModel(project.getModel().getDesc());
        projectForView.setUsername(project.getUser().getUsername());
        projectForView.setStep(project.getStep());
        FactoringDetail detail = project.getDetail();
        projectForView.setCreditor(detail.getCreditor());
        projectForView.setDebtor(detail.getDebtor());
        projectForView.setLoanAuditPassTime(DateUtil.getNewFormatDateString(detail.getLoanAuditPassTime()));
        projectForView.setReceivable(detail.getReceivable());
        projectForView.setDropAmount(detail.getLoanInstallments().stream().map(installment -> installment.getAmount()).collect(Collectors.toList()));
        projectForView.setDropDates(Optional.of(detail.getLoanInstallments()).orElse(new ArrayList<Installment>()).stream().map(installment -> installment.getInstallmentDate())
            .map(date -> DateUtil.getWebDateString(date)).collect(Collectors.toList()));
        projectForView.setReturnAmount(detail.getReturnInstallments().stream().map(installment -> installment.getAmount()).collect(Collectors.toList()));
        projectForView.setReturnDates(Optional.of(detail.getReturnInstallments()).orElse(new ArrayList<Installment>()).stream().map(installment -> installment.getInstallmentDate())
            .map(date -> DateUtil.getWebDateString(date)).collect(Collectors.toList()));
        projectForView.setTotalFactoringFee(detail.getTotalFactoringFee());
        projectForView.setFactoringInstallmentAmounts(
            Optional.of(detail.getFactoringInstallments()).orElse(new ArrayList<Installment>()).stream().map(installment -> installment.getAmount()).collect(Collectors.toList()));
        projectForView.setFactoringInstallmentDates(detail.getFactoringInstallments().stream().map(installment -> installment.getInstallmentDate())
            .map(date -> DateUtil.getWebDateString(date)).collect(Collectors.toList()));
        projectForView.setFactoringInstallmentInvoiceds(detail.getFactoringInstallments().stream().map(installment -> installment.isInvoiced()).collect(Collectors.toList()));

        return projectForView;
    }
}
