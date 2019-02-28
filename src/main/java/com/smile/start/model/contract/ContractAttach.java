package com.smile.start.model.contract;

import java.io.Serializable;

/**
 * @author Joseph
 * @version v1.0 2019/2/24 12:41, ContractAttach.java
 * @since 1.8
 */
public class ContractAttach implements Serializable {
    private static final long serialVersionUID = -4819395779960440788L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 合同流水
     */
    private String contractSerialNo;

    /**
     * 附件名称
     */
    private String attachName;

    /**
     * 文件唯一ID
     */
    private String fileId;

    /**
     * 附件类型：1、标准；2、自定义
     */
    private Integer attachType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getContractSerialNo() {
        return contractSerialNo;
    }

    public void setContractSerialNo(String contractSerialNo) {
        this.contractSerialNo = contractSerialNo;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getAttachType() {
        return attachType;
    }

    public void setAttachType(Integer attachType) {
        this.attachType = attachType;
    }
}
