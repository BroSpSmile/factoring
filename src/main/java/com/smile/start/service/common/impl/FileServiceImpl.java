/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.common.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.integration.aliyun.FileClient;
import com.smile.start.model.common.FileInfo;
import com.smile.start.service.AbstractService;
import com.smile.start.service.common.FileService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: FileServiceImpl.java, v 0.1 Jan 26, 2019 11:49:59 PM smile.jing Exp $
 */
@Service
public class FileServiceImpl extends AbstractService implements FileService {

    /** oss文件客户端 */
    @Resource
    private FileClient fileClient;

    /** 
     * @see com.smile.start.service.common.FileService#upload(java.io.InputStream, java.lang.String)
     */
    @Override
    public FileInfo upload(InputStream is, String fileName) {
        FileInfo fileInfo = new FileInfo();
        String fileId = fileClient.upload(is);
        fileInfo.setFileId(fileId);
        fileInfo.setFileName(fileName);
        return fileInfo;
    }

    /** 
     * @see com.smile.start.service.common.FileService#upload(java.io.File, java.lang.String)
     */
    @Override
    public FileInfo upload(File file, String fileName) {
        try {
            FileInfo fileInfo = new FileInfo();
            InputStream is = new FileInputStream(file);
            String fileId = fileClient.upload(is);
            fileInfo.setFileId(fileId);
            fileInfo.setFileName(fileName);
            return fileInfo;
        } catch (FileNotFoundException e) {
            LoggerUtils.error(logger, "上传文件失败,文件名={}", e, fileName);
            throw new RuntimeException(e);
        }
    }

    /** 
     * @see com.smile.start.service.common.FileService#delete(java.lang.String)
     */
    @Override
    public boolean delete(String fileId) {
        return fileClient.delete(fileId);
    }

    /** 
     * @see com.smile.start.service.common.FileService#download(java.lang.String)
     */
    @Override
    public InputStream download(String fileId) {
        return fileClient.download(fileId);
    }

}
