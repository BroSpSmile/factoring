package com.smile.start.service.contract;

import com.smile.start.dto.ContractInfoDTO;
import com.smile.start.dto.ContractSignListDTO;

import java.util.List;

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

    /**
     * 插入签署清单列表
     * @param  contractSerialNo
     * @param signListList
     */
    void insertSignList(String contractSerialNo, List<ContractSignListDTO> signListList);
}
