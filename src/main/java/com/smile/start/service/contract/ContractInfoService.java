package com.smile.start.service.contract;

import com.github.pagehelper.PageInfo;
import com.smile.start.dto.*;
import com.smile.start.model.base.PageRequest;
import java.util.List;

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

    /**
     * 提交审核
     * @param id
     */
    void submitAudit(Long id);

    /**
     * 此方法只供合同审核列表用，根据当前登录用户查询待审核的合同列表
     * @param page
     * @return
     */
    PageInfo<ContractBaseInfoDTO> findAuditList(PageRequest<ContractAuditSearchDTO> page);

    /**
     * 合同审核
     * @param contractAuditDTO
     */
    void audit(ContractAuditDTO contractAuditDTO);

    /**
     * 查询合同审核历史
     * @param contractSerialNo
     * @return
     */
    List<ContractAuditRecordDTO> findAuditRecord(String contractSerialNo);

    /**
     * 获取合同签署清单列表
     * @param contractSerialNo
     * @return
     */
    List<ContractSignListDTO> findSignListByContractSerialNo(String contractSerialNo);

    /**
     * 保存签署信息
     * @param contractSignDTO
     */
    void saveSign(ContractSignDTO contractSignDTO);
}
