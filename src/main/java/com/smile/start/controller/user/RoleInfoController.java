package com.smile.start.controller.user;

import com.smile.start.controller.BaseController;
import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.SingleResult;
import com.smile.start.service.RoleInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
}
