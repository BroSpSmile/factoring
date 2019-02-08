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
import com.smile.start.model.project.Project;
import com.smile.start.service.project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
     *
     * 归档项目查询
     * @return
     */
    @GetMapping
    public String index() {
        return "filing/project";
    }

    /**
     * 新增项目
     * 
     * @param project
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(@RequestBody Project project) {
        project.setKind(ProjectKind.FACTORING);
        project.setProgress(Progress.INIT);
        LoggerUtils.info(logger, "新增项目请求参数={}", FastJsonUtils.toJSONString(project));
        return projectService.initProject(project);
    }

    /**
     * 更新项目
     * @param project
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult edit(@RequestBody Project project) {
        LoggerUtils.info(logger, "修改项目请求参数={}", FastJsonUtils.toJSONString(project));
        try {
            return projectService.updateProject(project);
        } catch (RuntimeException e) {
            return toResult(e);
        }
    }

    /**
     * 删除项目
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public BaseResult delete(@PathVariable Long id) {
        try {
            LoggerUtils.info(logger, "删除项目id={}", id);
            return projectService.delete(id);
        } catch (RuntimeException e) {
            return toResult(e);
        }
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
        return result;
    }
}
