package com.smile.start.controller.user;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.LoginRequestDTO;
import com.smile.start.dto.UserSearchDTO;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.service.UserInfoService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, UserInfoController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/user")
public class UserInfoController extends BaseController {

    @Resource
    private UserInfoService userInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "auth/user";
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public SingleResult<AuthUserInfoDTO> get(@PathVariable Long id) {
        try {
            AuthUserInfoDTO authUserInfo = userInfoService.get(id);
            SingleResult<AuthUserInfoDTO> result = new SingleResult<>();
            result.setSuccess(true);
            result.setData(authUserInfo);
            return result;
        } catch (Exception e) {
            logger.error("查询用户信息失败", e);
            return toResult(e, AuthUserInfoDTO.class);
        }
    }

    /**
     *
     * @param userSearch
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<AuthUserInfoDTO> list(@RequestBody PageRequest<UserSearchDTO> userSearch) {
        PageInfo<AuthUserInfoDTO> userList = userInfoService.findAll(userSearch);
        return userList;
    }

    /**
     *
     * @param authUserInfoDTO
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(@RequestBody AuthUserInfoDTO authUserInfoDTO) {
        try {
            userInfoService.insert(authUserInfoDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("新增用户成功");
            return result;
        } catch (Exception e) {
            logger.error("新增用户信息失败", e);
            return toResult(e);
        }
    }

    /**
     *
     * @param authUserInfoDTO
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(@RequestBody AuthUserInfoDTO authUserInfoDTO) {
        try {
            BaseResult result = new BaseResult();
            userInfoService.update(authUserInfoDTO);
            result.setSuccess(true);
            result.setErrorMessage("更新用户成功");
            return result;
        } catch (Exception e) {
            logger.error("更新用户信息失败", e);
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
            userInfoService.delete(id);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("删除用户成功");
            return result;
        } catch (Exception e) {
            logger.error("删除用户信息失败", e);
            return toResult(e);
        }
    }

}
