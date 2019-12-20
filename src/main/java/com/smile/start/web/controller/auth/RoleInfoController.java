package com.smile.start.web.controller.auth;

import com.github.pagehelper.PageInfo;
import com.smile.start.web.controller.BaseController;
import com.smile.start.model.dto.AuthRoleInfoDTO;
import com.smile.start.model.dto.RoleSearchDTO;
import com.smile.start.model.auth.PermissionSetting;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.service.auth.RoleInfoService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:37, RoleInfoController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/role")
public class RoleInfoController extends BaseController {

    @Resource
    private RoleInfoService roleInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "auth/role";
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public SingleResult<AuthRoleInfoDTO> get(@PathVariable Long id) {
        try {
            SingleResult<AuthRoleInfoDTO> result = new SingleResult<>();
            AuthRoleInfoDTO authRoleInfo = roleInfoService.get(id);
            result.setSuccess(true);
            result.setData(authRoleInfo);
            return result;
        } catch (Exception e) {
            logger.error("查询角色信息失败", e);
            return toResult(e, AuthRoleInfoDTO.class);
        }
    }

    /**
     *
     * @param roleSearch
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<AuthRoleInfoDTO> list(@RequestBody PageRequest<RoleSearchDTO> roleSearch) {
        PageInfo<AuthRoleInfoDTO> roleList = roleInfoService.findAll(roleSearch);
        return roleList;
    }

    /**
     *
     * @param authRoleInfoDTO
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(@RequestBody AuthRoleInfoDTO authRoleInfoDTO) {
        try {
            roleInfoService.insert(authRoleInfoDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("新增角色成功");
            return result;
        } catch (Exception e) {
            logger.error("新增角色信息失败", e);
            return toResult(e);
        }
    }

    /**
     *
     * @param authRoleInfoDTO
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(@RequestBody AuthRoleInfoDTO authRoleInfoDTO) {
        try {
            roleInfoService.update(authRoleInfoDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("更新角色成功");
            return result;
        } catch (Exception e) {
            logger.error("更新角色信息失败", e);
            return toResult(e);
        }
    }

    /**
     * delete
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public BaseResult delete(@PathVariable Long id) {
        try {
            roleInfoService.delete(id);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("删除角色成功");
            return result;
        } catch (Exception e) {
            logger.error("删除角色信息失败", e);
            return toResult(e);
        }
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ListResult<AuthRoleInfoDTO> all() {
        try {
            final List<AuthRoleInfoDTO> all = roleInfoService.findAll();
            ListResult<AuthRoleInfoDTO> result = new ListResult<>();
            result.setSuccess(true);
            result.setErrorMessage("获取所有角色成功");
            result.setValues(all);
            return result;
        } catch (Exception e) {
            logger.error("获取所有角色失败", e);
            return toListResult(e, AuthRoleInfoDTO.class);
        }
    }

    @PostMapping(value = "/permission")
    @ResponseBody
    public BaseResult savePersmission(@RequestBody PermissionSetting permissionSetting) {
        try {
            roleInfoService.savePermission(permissionSetting);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("保存角色权限成功");
            return result;
        } catch (Exception e) {
            logger.error("存角色权限信息失败", e);
            return toResult(e);
        }
    }
}
