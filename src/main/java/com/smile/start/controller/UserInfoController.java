package com.smile.start.controller;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.ResponseResult;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.UserSearchDTO;
import com.smile.start.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, UserInfoController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/user")
public class UserInfoController {
    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Resource
    private UserInfoService userInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "auth/user";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseResult<AuthUserInfoDTO>> get(@PathVariable Long id) {
        ResponseResult<AuthUserInfoDTO> result = new ResponseResult<>();
        try {
            AuthUserInfoDTO authUserInfo = userInfoService.get(id);
            result.setSuccess(true);
            result.setData(authUserInfo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("查询用户信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseResult<PageInfo<AuthUserInfoDTO>>> list(@RequestBody UserSearchDTO userSearch) {
        ResponseResult<PageInfo<AuthUserInfoDTO>> result = new ResponseResult<>();
        try {
            final PageInfo<AuthUserInfoDTO> userList = userInfoService.findAll(userSearch);
            result.setSuccess(true);
            result.setData(userList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("查询用户信息列表失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<ResponseResult> add(@RequestBody AuthUserInfoDTO authUserInfoDTO) {
        ResponseResult result = new ResponseResult();
        try {
            userInfoService.insert(authUserInfoDTO);
            result.setSuccess(true);
            result.setMessage("新增成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("新增用户信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "")
    public ResponseEntity<ResponseResult> update(@RequestBody AuthUserInfoDTO authUserInfoDTO) {
        ResponseResult result = new ResponseResult();
        try {
            userInfoService.update(authUserInfoDTO);
            result.setSuccess(true);
            result.setMessage("更新成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("更新用户信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseResult> delete(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        try {
            userInfoService.delete(id);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("删除用户信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
