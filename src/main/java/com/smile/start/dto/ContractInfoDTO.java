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
     * 签署清单
     */
    private List<SignListTemplateDTO> signList;

    public ContractBaseInfoDTO getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(ContractBaseInfoDTO baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<SignListTemplateDTO> getSignList() {
        return signList;
    }

    public void setSignList(List<SignListTemplateDTO> signList) {
        this.signList = signList;
    }
}
