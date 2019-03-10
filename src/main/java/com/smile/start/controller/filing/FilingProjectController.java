/**
 *
 */
package com.smile.start.controller.filing;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.enums.ProjectKind;
import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.project.Project;
import com.smile.start.service.filing.FilingService;
import com.smile.start.service.project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @description：归档申请，项目查询
 * @author ：xioutman
 * @date ：Created in 2019/2/4 11:10
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping("/filingProject")
public class FilingProjectController extends BaseController {

    /** 项目服务 */
    @Resource
    private ProjectService projectService;

    /**
     * 项目服务
     */
    @Resource
    private FilingService filingService;

    /**
     *
     * 归档项目查询
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String type = request.getParameter("type");
        model.addAttribute("type", type);
        return "filing/project";
    }

    /**
     * 分页查询
     * @param query
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public PageInfo<Project> queryByParam(@RequestBody PageRequest<Project> query) {
        LoggerUtils.info(logger, "查询请求参数={}", FastJsonUtils.toJSONString(query));
        PageInfo<Project> result = projectService.queryPage(query);
        List<Project> projectList = result.getList();
        projectList.stream().forEach(project -> project.setSubProgress(getSubProgress(project)));
        return result;
    }

    private String getSubProgress(Project project) {
        Optional<FilingApplyInfo> opt = Optional.ofNullable(filingService.findByProjectId(project.getId()));
        return opt.isPresent() ? opt.get().getProgress().getCode() : "";
    }
}
