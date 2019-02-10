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
 * @description：归档申请
 * @author ：xioutman
 * @date ：Created in 2019/2/4 11:10
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/filingApply")
public class FilingApplyController  extends BaseController {

    /** 项目服务 */
    @Resource
    private FilingService filingService;

    /**
     * 归档申请页面
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
     * 归档申请
     * @param filingApplyInfo
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult filingApply(@RequestBody FilingApplyInfo filingApplyInfo) {
        filingApplyInfo.setApplyTime(new Date());
        LoggerUtils.info(logger, "立项归档申请filingApplyInfo={}", FastJsonUtils.toJSONString(filingApplyInfo));
        filingApplyInfo.setProgress(FilingProgress.FILE.getCode());
        return filingService.addFilingApply(filingApplyInfo);
    }


}
