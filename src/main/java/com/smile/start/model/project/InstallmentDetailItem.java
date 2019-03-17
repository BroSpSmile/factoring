/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;

/**
 * 分期附件
 *
 * @author smile.jing
 * @version $Id: ProjectItem.java, v 0.1 Jan 28, 2019 3:47:50 PM smile.jing Exp $
 */
public class InstallmentDetailItem implements Serializable {

    /**
     * UID
     */
    private static final long serialVersionUID = 1206639280144526741L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 分期编号
     */
    private Long installmentDetailId;

    /**
     * 附件名称
     */
    private String itemName;

    /**
     * 附件文件id
     */
    private String itemValue;

    /**
     * 此字段只有itemType是合同时才生效
     * 附件类型：1、标准；2、自定义
     */
    private Integer attachType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstallmentDetailId() {
        return installmentDetailId;
    }

    public void setInstallmentDetailId(Long installmentDetailId) {
        this.installmentDetailId = installmentDetailId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public Integer getAttachType() {
        return attachType;
    }

    public void setAttachType(Integer attachType) {
        this.attachType = attachType;
    }
}
