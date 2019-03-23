/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.common;

import java.io.Serializable;

/**
 * 文件信息
 *
 * @author smile.jing
 * @version $Id: FileInfo.java, v 0.1 Jan 26, 2019 11:42:56 PM smile.jing Exp $
 */
public class InstallmentFileInfo extends FileInfo {

    private static final long serialVersionUID = 7606575168659710331L;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
