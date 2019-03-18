package com.smile.start.controller.filing;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.commons.LoginHandler;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.AuditResult;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.enums.FilingSubProgress;
import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.login.LoginUser;
import com.smile.start.model.login.LoginUserRole;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.filing.FilingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ：xioutman
 * @description：归档申请
 * @date ：Created in 2019/2/4 11:10
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/filingAudit")
public class FilingAuditController extends BaseController {

    /**
     * 项目服务
     */
    @Resource
    private FilingService filingService;

    /**
     * auditService
     */
    @Resource
    private AuditService auditService;

    /**
     * 归档申请页面
     *
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "归档审核项目ID={}", id);
        model.addAttribute("id", id);
        if (StringUtils.isNotBlank(request.getParameter("view"))) {
            model.addAttribute("view", request.getParameter("view"));
        }
        model.addAttribute("type", request.getParameter("type"));
        return "filing/auditdetail";
    }

    /**
     * 归档审批查询
     *
     * @param id 为project.id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public SingleResult<FilingApplyInfo> findByProjectId(@PathVariable Long id) {

        LoggerUtils.info(logger, "查询归档审批信息, 请求参数 project.id ={}", id);
        SingleResult<FilingApplyInfo> result = new SingleResult<FilingApplyInfo>();

        Audit audit = auditService.getAuditByProjectFlowAndType(id, AuditType.FILE.getCode());
//        boolean right = false;
//        if (null != audit) {
//            if (audit.getStep() == -1) {
//                right = true;
//            } else {
//                LoginUser user = LoginHandler.getLoginUser();
//                for (LoginUserRole role : user.getRoleList()) {
//                    if (StringUtils.equals(role.getSerialNo(), audit.getRole().getSerialNo())) {
//                        right = true;
//                        break;
//                    }
//                }
//            }
//            if (!right) {
//                throw new RuntimeException("没有审批权限!");
////            result.setErrorMessage("没有审批权限!");
////            result.setErrorCode("VP00011002");
////            result.setSuccess(false);
////            return result;
//            }
//        }

        FilingApplyInfo filingApplyInfo = filingService.findByProjectId(id);
        if (StringUtils.isNotBlank(filingApplyInfo.getFilingListStr())) {
            filingApplyInfo.setFilingList(filingApplyInfo.getFilingListStr().split(","));
        }
        if (null == filingApplyInfo.getRecord()) {
            filingApplyInfo.setRecord(new AuditRecord());
        }
        if (null == filingApplyInfo.getAudit()) {
            filingApplyInfo.setAudit(new Audit());
        }
        filingApplyInfo.setAudit(audit);
        result.setData(filingApplyInfo);
        result.setSuccess(true);
        return result;
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    @GetMapping("/user")
    @ResponseBody
    public BaseResult getUser(HttpServletRequest request) {
        SingleResult<User> result = new SingleResult<User>();
        result.setSuccess(true);
        result.setData(getUserByToken(request));
        return result;
    }

    /**
     * 归档申请法务审核
     *
     * @param filingApplyInfo
     * @return
     */
    @PostMapping("/audit")
    @ResponseBody
    public BaseResult audit(HttpServletRequest request, @RequestBody FilingApplyInfo filingApplyInfo) {
        filingApplyInfo.setProgress(FilingSubProgress.FILE_LEGAL_AUDIT);
        LoggerUtils.info(logger, "归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        User user = getUserByToken(request);
        if (null == filingApplyInfo.getRecord()) {
            filingApplyInfo.setRecord(new AuditRecord());
        }
        filingApplyInfo.getRecord().setAuditor(user);
        filingApplyInfo.getRecord().setResult(AuditResult.PASS);
        filingApplyInfo.getRecord().setRemark("审核通过");
        return filingService.updateFilingApply(filingApplyInfo, false);
    }

    /**
     * 归档申请打回
     *
     * @param filingApplyInfo
     * @return
     */
    @PostMapping("/reject")
    @ResponseBody
    public BaseResult reject(HttpServletRequest request, @RequestBody FilingApplyInfo filingApplyInfo) {
        //filingApplyInfo.setProgress(FilingSubProgress.getByIndex(filingApplyInfo.getRejectStep()));
        filingApplyInfo.setProgress(FilingSubProgress.last(filingApplyInfo.getProgress()));
        User user = getUserByToken(request);
        if (null == filingApplyInfo.getRecord()) {
            filingApplyInfo.setRecord(new AuditRecord());
        }
        filingApplyInfo.getRecord().setAuditor(user);
        filingApplyInfo.getRecord().setResult(AuditResult.REJECTED);
        LoggerUtils.info(logger, "归档申请驳回：filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        return filingService.updateFilingApply(filingApplyInfo, false);
    }

    /**
     * 归档申请完成
     *
     * @param filingApplyInfo
     * @return
     */
    @PostMapping("/complete")
    @ResponseBody
    public BaseResult complete(HttpServletRequest request, @RequestBody FilingApplyInfo filingApplyInfo) {
        User user = getUserByToken(request);
        //filingApplyInfo.setApplyTime(DateUtil.getNewFormatDateString(new Date()));
        filingApplyInfo.setProgress(FilingSubProgress.FILE_OFFICER);
        if (null == filingApplyInfo.getRecord()) {
            filingApplyInfo.setRecord(new AuditRecord());
        }
        filingApplyInfo.getRecord().setAuditor(user);
        filingApplyInfo.getRecord().setResult(AuditResult.COMPLETE);
        LoggerUtils.info(logger, "归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        return filingService.updateFilingApply(filingApplyInfo, false);
    }

}
