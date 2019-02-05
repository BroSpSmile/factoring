package com.smile.start.controller.user;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.OrganizationalDTO;
import com.smile.start.dto.OrganizationalSearchDTO;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.service.OrganizationalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/1/29 17:12, OrganizationalController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/organizational")
public class OrganizationalController extends BaseController {

    @Resource
    private OrganizationalService organizationalService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "auth/organizational";
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public SingleResult<OrganizationalDTO> get(@PathVariable Long id) {
        try {
            OrganizationalDTO organizationalDTO = organizationalService.get(id);
            SingleResult<OrganizationalDTO> result = new SingleResult<>();
            result.setSuccess(true);
            result.setData(organizationalDTO);
            return result;
        } catch (Exception e) {
            logger.error("查询组织架构失败", e);
            return toResult(e, OrganizationalDTO.class);
        }
    }

    /**
     *
     * @param organizationalSearch
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<OrganizationalDTO> list(@RequestBody PageRequest<OrganizationalSearchDTO> organizationalSearch) {
        return organizationalService.findAll(organizationalSearch);
    }

    /**
     *
     * @param organizationalDTO
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(@RequestBody OrganizationalDTO organizationalDTO) {
        try {
            organizationalService.insert(organizationalDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("新增组织架构成功");
            return result;
        } catch (Exception e) {
            logger.error("新增组织架构失败", e);
            return toResult(e);
        }
    }

    /**
     *
     * @param organizationalDTO
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(@RequestBody OrganizationalDTO organizationalDTO) {
        try {
            BaseResult result = new BaseResult();
            organizationalService.update(organizationalDTO);
            result.setSuccess(true);
            result.setErrorMessage("更新组织架构成功");
            return result;
        } catch (Exception e) {
            logger.error("更新组织架构失败", e);
            return toResult(e);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public BaseResult delete(@PathVariable Long id) {
        try {
            organizationalService.delete(id);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("删除组织架构成功");
            return result;
        } catch (Exception e) {
            logger.error("删除组织架构失败", e);
            return toResult(e);
        }
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public ListResult<OrganizationalDTO> list() {
        try {
            List<OrganizationalDTO> organizationalList = organizationalService.findByParam(null);
            ListResult<OrganizationalDTO> result = new ListResult<>();
            result.setSuccess(true);
            result.setErrorMessage("获取组织列表成功");
            result.setValues(organizationalList);
            return result;
        } catch (Exception e) {
            logger.error("获取组织列表失败", e);
            return toListResult(e, OrganizationalDTO.class);
        }
    }
}
