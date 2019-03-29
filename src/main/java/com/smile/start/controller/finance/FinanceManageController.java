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
import com.smile.start.controller.combobox.Item;
import com.smile.start.dao.EntrustAuthDao;
import com.smile.start.dto.*;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.Step;
import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.project.*;
import com.smile.start.service.auth.RoleInfoService;
import com.smile.start.service.entrustauth.EntrustAuthService;
import com.smile.start.service.finance.FinanceService;
import com.smile.start.service.project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private static final String FINANCE_ADMIN_ROLE_CODE = "12";

    private static final String FINANCE_ROLE_CODE = "12-1";

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

    @Resource
    private RoleInfoService roleInfoService;

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
    public PageInfo<ProjectForView> queryByParam(@RequestBody PageRequest<Project> query,HttpServletRequest request) {

        //project.step  LOANEN(9, "放款操作") 可以查询 begin
        //project.step END(12, "完结") 可以查询 begin
        User user = getUserByToken(request);
        if (!isFinanceAdmin(user)) {
            query.getCondition()
                .setProjectIdList(entrustAuthService.getEntrustAuthProjectIds(user.getId(), Step.LOANEN));
        }
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
        boolean isFinanceAdmin = isFinanceAdmin(user);
        result.setData(isFinanceAdmin);
        return result;
    }

    private boolean isFinanceAdmin(User user) {
        List<AuthRoleInfoDTO> roles = roleInfoService.findByUserSerialNo(user.getSerialNo());
        boolean isFinanceAdmin = false;
        for (AuthRoleInfoDTO authRoleInfoDTO : roles) {
            if (authRoleInfoDTO.getRoleCode().equals(FINANCE_ADMIN_ROLE_CODE)) {
                isFinanceAdmin = true;
                break;
            }
        }
        return isFinanceAdmin;
    }

    /**
     * 获取财务人员列表
     *
     * @return
     */
    @GetMapping("/financeUserList")
    @ResponseBody
    public List<Item> getUserAll(HttpServletRequest request) {
        ListResult<AuthUserInfoDTO> result = new ListResult<AuthUserInfoDTO>();
        PageRequest<UserSearchDTO> userSearch = new PageRequest<UserSearchDTO>();
        userSearch.setCondition(new UserSearchDTO());
        PageInfo<AuthUserInfoDTO> userListPageInfo = userInfoService.findAll(userSearch);
        List<AuthUserInfoDTO> list = userListPageInfo.getList();
        list.stream().filter(user -> {
            boolean hasRoleFinanceRole = false;
            for (AuthRoleInfoDTO authRoleInfoDTO : user.getRoleList()) {
                if (authRoleInfoDTO.getRoleCode().equals(FINANCE_ROLE_CODE)) {
                    hasRoleFinanceRole = true;
                    break;
                }
            }
            return hasRoleFinanceRole;
        }).collect(Collectors.toList());

        return list.stream()
            .map(user -> new Item(String.valueOf(user.getId()),
                user.getUsername() + "," + user.getMobile() + "," + user.getFirstOrganizationName()))
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
    public BaseResult entrustAuth(@RequestBody EntrustAuth entrustAuth,HttpServletRequest request) {
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
        projectForView.setUsername(project.getUser().getUsername());
        FactoringDetail detail = project.getDetail();
        projectForView.setLoanAuditPassTime(detail.getLoanAuditPassTime());
        projectForView.setReceivable(detail.getReceivable()/10000);
        projectForView.setDropAmount(detail.getLoanInstallments()
            .stream()
            .map(installment -> installment.getAmount()/10000)
            .collect(Collectors.toList()));
        projectForView.setDropDates(Optional.of(detail.getLoanInstallments())
            .orElse(new ArrayList<Installment>())
            .stream()
            .map(installment -> installment.getInstallmentDate())
            .map(date -> DateUtil.getWebDateString(date))
            .collect(Collectors.toList()));
        projectForView.setReturnAmount(detail.getReturnInstallments()
            .stream()
            .map(installment -> installment.getAmount()/10000)
            .collect(Collectors.toList()));
        projectForView.setReturnDates(Optional.of(detail.getReturnInstallments())
            .orElse(new ArrayList<Installment>())
            .stream()
            .map(installment -> installment.getInstallmentDate())
            .map(date -> DateUtil.getWebDateString(date))
            .collect(Collectors.toList()));
        projectForView.setTotalFactoringFee(detail.getTotalFactoringFee()/10000);
        projectForView.setFactoringInstallmentAmounts(
            Optional.of(detail.getFactoringInstallments())
                .orElse(new ArrayList<Installment>())
                .stream()
                .map(installment -> installment.getAmount()/10000)
                .collect(Collectors.toList()));
        projectForView.setFactoringInstallmentDates(detail.getFactoringInstallments()
            .stream()
            .map(installment -> installment.getInstallmentDate())
            .map(date -> DateUtil.getWebDateString(date))
            .collect(Collectors.toList()));
        projectForView.setFactoringInstallmentInvoiceds(detail.getFactoringInstallments()
            .stream()
            .map(installment -> installment.isInvoiced())
            .collect(Collectors.toList()));

        return projectForView;
    }
}
