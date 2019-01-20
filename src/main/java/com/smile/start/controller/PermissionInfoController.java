package com.smile.start.controller;

import com.smile.start.commons.ResponseResult;
import com.smile.start.dto.AuthPermissionInfoDTO;
import com.smile.start.service.PermissionInfoService;
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
 * @version v1.0 2019/1/6 14:38, PermissionInfoController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/auth/permission")
public class PermissionInfoController {
    private Logger logger = LoggerFactory.getLogger(PermissionInfoController.class);

    @Resource
    private PermissionInfoService permissionInfoService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseResult<AuthPermissionInfoDTO>> get(@PathVariable Long id) {
        ResponseResult<AuthPermissionInfoDTO> result = new ResponseResult<>();
        try {
            AuthPermissionInfoDTO authPermissionInfo = permissionInfoService.get(id);
            result.setSuccess(true);
            result.setData(authPermissionInfo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("查询权限信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<ResponseResult> add(@RequestBody AuthPermissionInfoDTO authPermissionInfoDTO) {
        ResponseResult result = new ResponseResult();
        try {
            permissionInfoService.insert(authPermissionInfoDTO);
            result.setSuccess(true);
            result.setMessage("新增成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("新增权限信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "")
    public ResponseEntity<ResponseResult> update(@RequestBody AuthPermissionInfoDTO authPermissionInfoDTO) {
        ResponseResult result = new ResponseResult();
        try {
            permissionInfoService.update(authPermissionInfoDTO);
            result.setSuccess(true);
            result.setMessage("更新成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("更新权限信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseResult> delete(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        try {
            permissionInfoService.delete(id);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("删除权限信息失败", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
