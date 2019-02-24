package com.smile.start.service.contract;

import com.github.pagehelper.PageInfo;
import com.smile.start.dto.ContractBaseInfoDTO;
import com.smile.start.dto.ContractInfoDTO;
import com.smile.start.dto.ContractInfoSearchDTO;
import com.smile.start.model.base.PageRequest;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:49, ContractInfoService.java
 * @since 1.8
 */
public interface ContractInfoService {

    /**
     * 根据主键获取合同信息
     * @param id
     * @return
     */
    ContractInfoDTO get(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    PageInfo<ContractBaseInfoDTO> findAll(PageRequest<ContractInfoSearchDTO> page);

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
     * 删除合同信息
     * @param id
     */
    void delete(Long id);
}
