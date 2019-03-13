/**
 * 
 */
package com.smile.start.controller.project;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.ProjectItemType;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.project.ProjectService;

/**
 * 项目
 * @author smile.jing
 * @version $Id: ApprovalController.java, v 0.1 Jan 13, 2019 9:32:21 PM smile.jing Exp $
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

    /** 项目服务 */
    @Resource
    private ProjectService projectService;

    /**
     * 项目立项页
     * 
     * @return
     */
    @GetMapping
    public String index() {
        return "project/project";
    }

    /**
     * 获取项目
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Project get(@PathVariable Long id) {
        LoggerUtils.info(logger, "根据ID获取项目信息,id={}", id);
        return projectService.getProject(id);
    }

    /**
     * 新增项目
     * 
     * @param project
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(HttpServletRequest request, @RequestBody Project project) {
        User user = getUserByToken(request);
        project.setUser(user);
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
    public BaseResult edit(HttpServletRequest request, @RequestBody Project project) {
        LoggerUtils.info(logger, "修改项目请求参数={}", FastJsonUtils.toJSONString(project));
        try {
            User user = getUserByToken(request);
            project.setUser(user);
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

    /**
     * 查询项目附件
     * @param id
     * @param type
     * @return
     */
    @PostMapping("/items/{id}/{type}")
    @ResponseBody
    public List<ProjectItem> queryItems(@PathVariable Long id, @PathVariable String type) {
        return projectService.queryItems(id, ProjectItemType.getByCode(type));
    }

    /**
     * 删除附件
     * @param item
     * @return
     */
    @DeleteMapping("items")
    @ResponseBody
    public BaseResult deleteItem(@RequestBody ProjectItem item) {
        LoggerUtils.info(logger, "删除附件:{}", FastJsonUtils.toJSONString(item));
        return projectService.deleteItem(item);
    }

}
