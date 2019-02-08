package com.smile.start.controller.filing;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @description：归档申请
 * @author ：xioutman
 * @date ：Created in 2019/2/4 11:10
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/filingAudit")
public class FilingAuditController extends BaseController {

    /**
     * 归档申请页面
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        LoggerUtils.info(logger, "归档审核项目ID={}", id);
        model.addAttribute("id", id);
        return "filing/auditdetail";
    }

}
