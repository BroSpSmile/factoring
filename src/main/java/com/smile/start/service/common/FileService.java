/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.common;

import java.io.File;
import java.io.InputStream;

import com.smile.start.model.common.FileInfo;

/**
 * 文件服务
 * @author smile.jing
 * @version $Id: FileService.java, v 0.1 Jan 26, 2019 11:45:16 PM smile.jing Exp $
 */
public interface FileService {

    /**
     * 文件上传
     * @param is
     * @param fileName
     * @return
     */
    FileInfo upload(InputStream is, String fileName);

    /**
     * 文件上传
     * @param file
     * @param fileName
     * @return
     */
    FileInfo upload(File file, String fileName);

    /**
     * 文件删除
     * @param fileId
     * @return
     */
    boolean delete(String fileId);

}
