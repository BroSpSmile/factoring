/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.common.FileService;
import com.smile.start.service.project.ProjectItemSerivce;
import com.smile.start.web.controller.BaseController;

/**
 * 附件操作
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: AttachController
 * @date Date : 2020年01月01日 21:25
 */
@Controller
@RequestMapping("attch")
public class AttachController extends BaseController {

    /** 文件服务 */
    @Resource
    private ProjectItemSerivce projectItemSerivce;

    /** 文件服务 */
    @Resource
    private FileService        fileService;

    /**
     * 删除文件
     * @param item
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public BaseResult delete(@RequestBody ProjectItem item) {
        LoggerUtils.info(logger, "删除文件ID={}", FastJsonUtils.toJSONString(item));
        projectItemSerivce.delete(item);
        boolean success = fileService.delete(item.getItemValue());
        BaseResult result = new BaseResult();
        result.setSuccess(success);
        return result;
    }
}
