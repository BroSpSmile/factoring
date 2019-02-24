package com.smile.start.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:52, ContractInfoDTO.java
 * @since 1.8
 */
public class ContractInfoDTO implements Serializable {
    private static final long serialVersionUID = 8761006705859319164L;

    /**
     * 基础信息
     */
    private ContractBaseInfoDTO baseInfo;

    /**
     * 合同扩展信息
     */
    private ContractExtendInfoDTO contractExtendInfo;

    /**
     * 应收账款转让登记协议
     */
    private ContractReceivableAgreementDTO contractReceivableAgreement;

    /**
     * 应收账款转让确认函
     */
    private ContractReceivableConfirmationDTO contractReceivableConfirmation;

    /**
     * 签署清单
     */
    private List<ContractSignListDTO> signList;

    /**
     * 附件列表
     */
    private List<ContractAttachDTO> attachList;

    public ContractBaseInfoDTO getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(ContractBaseInfoDTO baseInfo) {
        this.baseInfo = baseInfo;
    }

    public ContractExtendInfoDTO getContractExtendInfo() {
        return contractExtendInfo;
    }

    public void setContractExtendInfo(ContractExtendInfoDTO contractExtendInfo) {
        this.contractExtendInfo = contractExtendInfo;
    }

    public ContractReceivableAgreementDTO getContractReceivableAgreement() {
        return contractReceivableAgreement;
    }

    public void setContractReceivableAgreement(ContractReceivableAgreementDTO contractReceivableAgreement) {
        this.contractReceivableAgreement = contractReceivableAgreement;
    }

    public ContractReceivableConfirmationDTO getContractReceivableConfirmation() {
        return contractReceivableConfirmation;
    }

    public void setContractReceivableConfirmation(ContractReceivableConfirmationDTO contractReceivableConfirmation) {
        this.contractReceivableConfirmation = contractReceivableConfirmation;
    }

    public List<ContractSignListDTO> getSignList() {
        return signList;
    }

    public void setSignList(List<ContractSignListDTO> signList) {
        this.signList = signList;
    }

    public List<ContractAttachDTO> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<ContractAttachDTO> attachList) {
        this.attachList = attachList;
    }
}
