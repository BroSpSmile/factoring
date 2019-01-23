package com.smile.start.controller;

import com.smile.start.commons.ResponseResult;
import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.service.RoleInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:37, RoleInfoController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/auth/role")
public class RoleInfoController {
    private Logger logger = LoggerFactory.getLogger(RoleInfoController.class);

    @Resource
    private RoleInfoService roleInfoService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseResult<AuthRoleInfoDTO>> get(@PathVariable Long id) {
        ResponseResult<AuthRoleInfoDTO> result = new ResponseResult<>();
        try {
            AuthRoleInfoDTO authRoleInfo = roleInfoService.get(id);
            result.setSuccess(true);
            result.setData(authRoleInfo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("查询角色信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<ResponseResult> add(@RequestBody AuthRoleInfoDTO authRoleInfoDTO) {
        ResponseResult result = new ResponseResult();
        try {
            roleInfoService.insert(authRoleInfoDTO);
            result.setSuccess(true);
            result.setMessage("新增成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("新增角色信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "")
    public ResponseEntity<ResponseResult> update(@RequestBody AuthRoleInfoDTO authRoleInfoDTO) {
        ResponseResult result = new ResponseResult();
        try {
            roleInfoService.update(authRoleInfoDTO);
            result.setSuccess(true);
            result.setMessage("更新成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("更新角色信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseResult> delete(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        try {
            roleInfoService.delete(id);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("删除角色信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
