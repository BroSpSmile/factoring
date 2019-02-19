package com.smile.start.service.contract;

import com.smile.start.dto.ContractInfoDTO;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:49, ContractInfoService.java
 * @since 1.8
 */
public interface ContractInfoService {

    /**
     * 插入合同基本信息
     * @param contractInfoDTO
     * @return
     */
    Long insert(ContractInfoDTO contractInfoDTO);

    /**
     * 修改合同基本信息
     * @param contractInfoDTO
     * @return
     */
    void update(ContractInfoDTO contractInfoDTO);
}
