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
    private Long              id;

    /**
     * 业务流水
     */
    private String            serialNo;

    /**
     * 合同流水
     */
    private String            contractSerialNo;

    /**
     * 签署清单名称
     */
    private String            signListName;

    /**
     * 状态：0、未完成；1、完成
     */
    private Boolean           status;

    /**
     * 是否必须：1、必须；2、非必须
     */
    private Integer           isRequired;

    /** 文件归档状态 */
    private Integer           filingStatus;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (id != null ? "id\":\"" + id + "\", \"" : "") + (serialNo != null ? "serialNo\":\"" + serialNo + "\", \"" : "")
               + (contractSerialNo != null ? "contractSerialNo\":\"" + contractSerialNo + "\", \"" : "")
               + (signListName != null ? "signListName\":\"" + signListName + "\", \"" : "") + (status != null ? "status\":\"" + status + "\", \"" : "")
               + (isRequired != null ? "isRequired\":\"" + isRequired + "\", \"" : "") + (filingStatus != null ? "filingStatus\":\"" + filingStatus : "") + "\"}  ";
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>serialNo</tt>.
     * 
     * @return property value of serialNo
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * Setter method for property <tt>serialNo</tt>.
     * 
     * @param serialNo value to be assigned to property serialNo
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * Getter method for property <tt>contractSerialNo</tt>.
     * 
     * @return property value of contractSerialNo
     */
    public String getContractSerialNo() {
        return contractSerialNo;
    }

    /**
     * Setter method for property <tt>contractSerialNo</tt>.
     * 
     * @param contractSerialNo value to be assigned to property contractSerialNo
     */
    public void setContractSerialNo(String contractSerialNo) {
        this.contractSerialNo = contractSerialNo;
    }

    /**
     * Getter method for property <tt>signListName</tt>.
     * 
     * @return property value of signListName
     */
    public String getSignListName() {
        return signListName;
    }

    /**
     * Setter method for property <tt>signListName</tt>.
     * 
     * @param signListName value to be assigned to property signListName
     */
    public void setSignListName(String signListName) {
        this.signListName = signListName;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>isRequired</tt>.
     * 
     * @return property value of isRequired
     */
    public Integer getIsRequired() {
        return isRequired;
    }

    /**
     * Setter method for property <tt>isRequired</tt>.
     * 
     * @param isRequired value to be assigned to property isRequired
     */
    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Getter method for property <tt>filingStatus</tt>.
     * 
     * @return property value of filingStatus
     */
    public Integer getFilingStatus() {
        return filingStatus;
    }

    /**
     * Setter method for property <tt>filingStatus</tt>.
     * 
     * @param filingStatus value to be assigned to property filingStatus
     */
    public void setFilingStatus(Integer filingStatus) {
        this.filingStatus = filingStatus;
    }

}
