package com.smile.start.controller.filing;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.FilingProgress;
import com.smile.start.model.filing.FilingApplyInfo;
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
     * 归档申请页面
     *
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "归档申请项目ID={}", id);
        model.addAttribute("id", id);
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
    public BaseResult save(@RequestBody FilingApplyInfo filingApplyInfo) {
        filingApplyInfo.setApplyTime(new Date());
        filingApplyInfo.setProgress(FilingProgress.TOBEFILED.getCode());
        LoggerUtils.info(logger, "归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        return filingService.addFilingApply(filingApplyInfo);
    }

    /**
     * 归档申请保存
     *
     * @param filingApplyInfo
     * @return
     */
    @PostMapping("/commit")
    @ResponseBody
    public BaseResult commit(@RequestBody FilingApplyInfo filingApplyInfo) {
        filingApplyInfo.setApplyTime(new Date());
        filingApplyInfo.setProgress(FilingProgress.FILE.getCode());
        LoggerUtils.info(logger, "归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        return filingService.addFilingApply(filingApplyInfo);
    }

    /**
     * 归档申请审核
     *
     * @param filingApplyInfo
     * @return
     */
    @PutMapping("/audit")
    @ResponseBody
    public BaseResult audit(@RequestBody FilingApplyInfo filingApplyInfo) {
        //filingApplyInfo.setApplyTime(new Date());
        filingApplyInfo.setProgress(FilingProgress.FILEAUDIT.getCode());
        LoggerUtils.info(logger, "归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        return filingService.updateFilingApply(filingApplyInfo);
    }

    /**
     * 归档申请打回
     *
     * @param filingApplyInfo
     * @return
     */
    @PutMapping("/reject")
    @ResponseBody
    public BaseResult reject(@RequestBody FilingApplyInfo filingApplyInfo) {
        //filingApplyInfo.setApplyTime(new Date());
        filingApplyInfo.setProgress(FilingProgress.FILE.getCode());
        LoggerUtils.info(logger, "归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        return filingService.updateFilingApply(filingApplyInfo);
    }

    /**
     * 归档申请打回保存
     *
     * @param filingApplyInfo
     * @return
     */
    @PutMapping("/rejectAndSave")
    @ResponseBody
    public BaseResult rejectAndSave(@RequestBody FilingApplyInfo filingApplyInfo) {
        filingApplyInfo.setApplyTime(new Date());
        filingApplyInfo.setProgress(FilingProgress.TOBEFILED.getCode());
        LoggerUtils.info(logger, "归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        return filingService.updateFilingApply(filingApplyInfo);
    }


    /**
     * 归档申请完成
     *
     * @param filingApplyInfo
     * @return
     */
    @PutMapping("/complete")
    @ResponseBody
    public BaseResult complete(@RequestBody FilingApplyInfo filingApplyInfo) {
        //filingApplyInfo.setApplyTime(new Date());
        filingApplyInfo.setProgress(FilingProgress.FILECOMPLETE.getCode());
        LoggerUtils.info(logger, "归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        return filingService.updateFilingApply(filingApplyInfo);
    }
}
