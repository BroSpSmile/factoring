package com.smile.start.web.controller.auth;

import com.github.pagehelper.PageInfo;
import com.smile.start.web.controller.BaseController;
import com.smile.start.model.dto.AuthPermissionInfoDTO;
import com.smile.start.model.dto.PermissionSearchDTO;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.common.Tree;
import com.smile.start.service.auth.PermissionInfoService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, PermissionInfoController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/permission")
public class PermissionInfoController extends BaseController {

    @Resource
    private PermissionInfoService permissionInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "auth/permission";
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public SingleResult<AuthPermissionInfoDTO> get(@PathVariable Long id) {
        try {
            SingleResult<AuthPermissionInfoDTO> result = new SingleResult<>();
            AuthPermissionInfoDTO authPermissionInfo = permissionInfoService.get(id);
            result.setSuccess(true);
            result.setData(authPermissionInfo);
            return result;
        } catch (Exception e) {
            logger.error("查询权限信息失败", e);
            return toResult(e, AuthPermissionInfoDTO.class);
        }
    }

    /**
     *
     * @param permissionSearch
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<AuthPermissionInfoDTO> list(@RequestBody PageRequest<PermissionSearchDTO> permissionSearch) {
        PageInfo<AuthPermissionInfoDTO> permissionList = permissionInfoService.findAll(permissionSearch);
        return permissionList;
    }

    /**
     *
     * @param authPermissionInfoDTO
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(@RequestBody AuthPermissionInfoDTO authPermissionInfoDTO) {
        try {
            permissionInfoService.insert(authPermissionInfoDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("新增权限成功");
            return result;
        } catch (Exception e) {
            logger.error("新增权限信息失败", e);
            return toResult(e);
        }
    }

    /**
     *
     * @param authPermissionInfoDTO
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(@RequestBody AuthPermissionInfoDTO authPermissionInfoDTO) {
        try {
            permissionInfoService.update(authPermissionInfoDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("更新权限成功");
            return result;
        } catch (Exception e) {
            logger.error("更新权限信息失败", e);
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
            permissionInfoService.delete(id);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("删除权限成功");
            return result;
        } catch (Exception e) {
            logger.error("删除权限信息失败", e);
            return toResult(e);
        }
    }

    @GetMapping(value = "/tree/{roleSerialNo}")
    @ResponseBody
    public ListResult<Tree> tree(@PathVariable String roleSerialNo) {
        try {
            ListResult<Tree> result = new ListResult<>();
            result.setSuccess(true);
            result.setErrorMessage("获取权限树成功");
            List<Tree> treeList = permissionInfoService.getTree(roleSerialNo);
            result.setValues(treeList);
            return result;
        } catch (Exception e) {
            logger.error("获取权限树失败", e);
            return toListResult(e, Tree.class);
        }
    }

    @GetMapping(value = "/list/{permissionType}")
    @ResponseBody
    public ListResult<AuthPermissionInfoDTO> list(@PathVariable Integer permissionType) {
        try {
            PermissionSearchDTO permissionSearchDTO = new PermissionSearchDTO();
            permissionSearchDTO.setPermissionType(permissionType);
            List<AuthPermissionInfoDTO> permissionList = permissionInfoService.findByParam(permissionSearchDTO);
            ListResult<AuthPermissionInfoDTO> result = new ListResult<>();
            result.setSuccess(true);
            result.setErrorMessage("获取权限列表成功");
            result.setValues(permissionList);
            return result;
        } catch (Exception e) {
            logger.error("获取权限列表失败", e);
            return toListResult(e, AuthPermissionInfoDTO.class);
        }
    }
}
