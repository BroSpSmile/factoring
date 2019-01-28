/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.integration.aliyun;

import java.io.InputStream;

/**
 * 阿里云文件服务客户端
 * @author smile.jing
 * @version $Id: FileClient.java, v 0.1 Jan 18, 2019 2:27:14 PM smile.jing Exp $
 */
public interface FileClient {

    /**
     * 上传文件
     * @param inputStream
     * @return 文件ID
     */
    String upload(InputStream inputStream);
    
    /**
     * 删除文件
     * @param fileId
     * @return
     */
    boolean delete(String fileId);

    /**
     * 下载文件
     * @param fileId
     * @return
     */
    InputStream download(String fileId);
}
