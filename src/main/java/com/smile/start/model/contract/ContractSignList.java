package com.smile.start.model.contract;

import java.io.Serializable;

/**
 * 合同签署清单
 * @author Joseph
 * @version v1.0 2019/2/19 8:42, ContractSignList.java
 * @since 1.8
 */
public class ContractSignList implements Serializable {
    private static final long serialVersionUID = -7554198696100662951L;

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
     * 签署清单名称
     */
    private String signListName;

    /**
     * 状态：0、未完成；1、完成
     */
    private Boolean status;

    /**
     * 是否必须：1、必须；2、非必须
     */
    private Integer isRequired;

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

    public String getSignListName() {
        return signListName;
    }

    public void setSignListName(String signListName) {
        this.signListName = signListName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }
}
