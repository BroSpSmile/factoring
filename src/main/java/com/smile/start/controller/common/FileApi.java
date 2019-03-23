/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smile.start.model.common.InstallmentFileInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.common.FileInfo;
import com.smile.start.service.common.FileService;

/**
 * 文件上传下载接口
 * @author smile.jing
 * @version $Id: FileApi.java, v 0.1 Jan 26, 2019 11:31:12 PM smile.jing Exp $
 */
@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileApi extends BaseController {
    /** 文件服务 */
    @Resource
    private FileService fileService;

    /**
     * 文件上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<FileInfo> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String contentType = file.getContentType(); //文件类型
        String fileName = file.getOriginalFilename(); //文件名字
        LoggerUtils.info(logger, "上传文件类型={},上传文件名={}", contentType, fileName);
        try {
            FileInfo fileInfo = fileService.upload(file.getInputStream(), fileName);
            SingleResult<FileInfo> result = new SingleResult<FileInfo>(fileInfo);
            return result;
        } catch (IOException e) {
            logger.error("", e);
            return toResult(e, FileInfo.class);
        }
    }

    /**
     * 文件上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadInstallment", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<InstallmentFileInfo> uploadInstallment(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String contentType = file.getContentType(); //文件类型
        String fileName = file.getOriginalFilename(); //文件名字
        LoggerUtils.info(logger, "上传文件类型={},上传文件名={}", contentType, fileName);
        try {
            FileInfo fileInfo = fileService.upload(file.getInputStream(), fileName);
            InstallmentFileInfo  installmentFileInfo =  new InstallmentFileInfo();
            installmentFileInfo.setFileId(fileInfo.getFileId());
            installmentFileInfo.setFileName(fileInfo.getFileName());
            installmentFileInfo.setId(request.getParameter("index"));
            SingleResult<InstallmentFileInfo> result = new SingleResult<InstallmentFileInfo>(installmentFileInfo);
            return result;
        } catch (IOException e) {
            logger.error("", e);
            return toResult(e, InstallmentFileInfo.class);
        }
    }


    /**
     * 删除文件
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/{fileId}", method = RequestMethod.DELETE)
    @ResponseBody
    public BaseResult delete(@PathVariable String fileId) {
        LoggerUtils.info(logger, "删除文件ID={}", fileId);
        boolean success = fileService.delete(fileId);
        BaseResult result = new BaseResult();
        result.setSuccess(success);
        return result;
    }

    /**
     * 下载文件
     * @param request
     * @param response
     */
    @GetMapping
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String fileId = request.getParameter("fileId");
        String fileName = request.getParameter("fileName");
        if (StringUtils.isNotBlank(fileId)) {
            InputStream is = fileService.download(fileId);
            try {
                download(StringUtils.isNotBlank(fileName) ? fileName : fileId, is, response);
            } catch (UnsupportedEncodingException e) {
                LoggerUtils.error(logger, "文件下载异常", e);
            }
        }

    }
}
