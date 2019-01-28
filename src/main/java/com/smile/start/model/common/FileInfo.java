/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.common;

import java.io.Serializable;

/**
 * 文件信息
 * @author smile.jing
 * @version $Id: FileInfo.java, v 0.1 Jan 26, 2019 11:42:56 PM smile.jing Exp $
 */
public class FileInfo implements Serializable {

    /** UID */
    private static final long serialVersionUID = -5751628942055756992L;

    /** 文件名 */
    private String            fileName;

    /** 上传ID */
    private String            fileId;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"fileName\":\"" + fileName + "\", \"fileId\":\"" + fileId + "\"}  ";
    }

    /**
     * Getter method for property <tt>fileName</tt>.
     * 
     * @return property value of fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter method for property <tt>fileName</tt>.
     * 
     * @param fileName value to be assigned to property fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter method for property <tt>fileId</tt>.
     * 
     * @return property value of fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * Setter method for property <tt>fileId</tt>.
     * 
     * @param fileId value to be assigned to property fileId
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}
