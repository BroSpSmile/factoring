/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.integration.aliyun.impl;

import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.integration.aliyun.FileClient;
import com.smile.start.service.AbstractService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: FileClientImpl.java, v 0.1 Jan 23, 2019 8:56:37 PM smile.jing Exp $
 */
@Service
public class FileClientImpl extends AbstractService implements FileClient {

    /** OSS客户端 */
    @Resource
    private OSSClient ossClient;

    /**  */
    @Value("${aliyun.oss.bucket}")
    private String    bucket;

    /** 
     * @see com.smile.start.integration.aliyun.FileClient#upload(java.io.InputStream)
     */
    @Override
    public String upload(InputStream inputStream) {
        String key = UUID.randomUUID().toString();
        ossClient.putObject(bucket, key, inputStream);
        LoggerUtils.info(logger, "文件上传成功,fileId={}", key);
        return key;
    }

    /** 
     * @see com.smile.start.integration.aliyun.FileClient#download(java.lang.String)
     */
    @Override
    public InputStream download(String fileId) {
        OSSObject ossObject = ossClient.getObject(bucket, fileId);
        LoggerUtils.info(logger, "文件下载成功,fileId={}", fileId);
        return ossObject.getObjectContent();
    }

}
