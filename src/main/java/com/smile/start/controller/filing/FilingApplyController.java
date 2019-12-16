package com.smile.start.controller.filing;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.ContractInfoDTO;
import com.smile.start.dto.ContractSignListDTO;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.Step;
import com.smile.start.model.project.Project;
import com.smile.start.service.contract.ContractInfoService;
import com.smile.start.service.engine.ProcessEngine;

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

    /** 合同服务 */
    @Resource
    private ContractInfoService contractInfoService;

    /** 流程引擎 */
    @Resource
    private ProcessEngine       processEngine;

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
     * @param contract
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public BaseResult save(@RequestBody ContractInfoDTO contract) {
        List<ContractSignListDTO> signDtos = contract.getSignList();
        return contractInfoService.updateFilingStatus(signDtos);
    }

    /**
     * 归档申请提交
     *
     * @return
     */
    @PostMapping("/commit")
    @ResponseBody
    public BaseResult commit(HttpServletRequest request, @RequestBody ContractInfoDTO contract) {
        try {
            User user = getUserByToken(request);
            Project project = contract.getProject();
            project.setStep(Step.FILE.getIndex());
            project.setUser(user);
            contractInfoService.uploadTransferList(project.getId());
            return processEngine.next(project, true);
        } catch (Exception e) {
            logger.error("归档申请提交失败", e);
            return toResult(e);
        }
    }
}
