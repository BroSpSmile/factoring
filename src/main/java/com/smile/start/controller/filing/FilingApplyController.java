package com.smile.start.controller.filing;

import com.smile.start.commons.DateUtil;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.AuditResult;
import com.smile.start.model.enums.FilingSubProgress;
import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.filing.FilingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author ：xioutman
 * @description：归档申请
 * @date ：Created in 2019/2/4 11:10
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/filingApply")
public class FilingApplyController extends BaseController {

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
        LoggerUtils.info(logger, "归档申请项目ID={}", id);
        model.addAttribute("id", id);
        model.addAttribute("type", request.getParameter("type"));

        return "filing/apply";
    }

    /**
     * 归档申请保存
     *
     * @param filingApplyInfo
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public BaseResult save(HttpServletRequest request, @RequestBody FilingApplyInfo filingApplyInfo) {
        User user = getUserByToken(request);
        genFilingApplyInfo(filingApplyInfo, user);
        filingApplyInfo.setProgress(FilingSubProgress.FILE_TOBE_APPLY);
        LoggerUtils.info(logger, "归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        checkAndSetAuditField(filingApplyInfo, user);
        return filingService.addFilingApply(filingApplyInfo);
    }

    /**
     * 归档申请打回保存
     *
     * @param filingApplyInfo
     * @return
     */
    @PostMapping("/rejectAndSave")
    @ResponseBody
    public BaseResult rejectAndSave(HttpServletRequest request, @RequestBody FilingApplyInfo filingApplyInfo) {
        return save(request, filingApplyInfo);
    }

    /**
     * 归档申请提交
     *
     * @param filingApplyInfo
     * @return
     */
    @PostMapping("/commit")
    @ResponseBody
    public BaseResult commit(HttpServletRequest request, @RequestBody FilingApplyInfo filingApplyInfo) {
        User user = getUserByToken(request);
        genFilingApplyInfo(filingApplyInfo, user);
        filingApplyInfo.setProgress(FilingSubProgress.FILE_APPLY);
        LoggerUtils.info(logger, "归档申请: filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        checkAndSetAuditField(filingApplyInfo, user);
        return filingService.addFilingApply(filingApplyInfo);
    }

    private void checkAndSetAuditField(FilingApplyInfo filingApplyInfo, User user) {
        if (null == filingApplyInfo.getRecord()) {
            filingApplyInfo.setRecord(new AuditRecord());
        }
        filingApplyInfo.getRecord().setAuditor(user);
        filingApplyInfo.getRecord().setAuditTime(new Date());
        filingApplyInfo.getRecord().setResult(AuditResult.APPLY);
        filingApplyInfo.getRecord().setRemark("申请成功");
        if (null == filingApplyInfo.getAudit()) {
            filingApplyInfo.setAudit(new Audit());
        }
    }

    private void genFilingApplyInfo(FilingApplyInfo filingApplyInfo, User user) {
        if (null != user) {
            filingApplyInfo.setApplicant(String.valueOf(user.getId()));
        }
        filingApplyInfo.setApplyTime(DateUtil.getNewFormatDateString(new Date()));
    }
}
