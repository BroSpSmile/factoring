package com.smile.start.service.common;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.dto.BankInfoDTO;
import com.smile.start.model.dto.BankInfoSearchDTO;
import com.smile.start.model.base.PageRequest;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/3/9 14:28, BankInfoService.java
 * @since 1.8
 */
public interface BankInfoService {

    /**
     * 根据主键查银行信息
     * @param id
     * @return
     */
    BankInfoDTO get(Long id);

    /**
     * 分页查询银行信息
     * @return
     */
    PageInfo<BankInfoDTO> findAll(PageRequest<BankInfoSearchDTO> page);

    /**
     * 查询所有银行信息
     * @return
     */
    List<BankInfoDTO> findAll();

    /**
     * 新增银行信息
     * @param bankInfoDTO
     * @return
     */
    Long insert(BankInfoDTO bankInfoDTO);

    /**
     * 更新银行信息
     * @param bankInfoDTO
     */
    void update(BankInfoDTO bankInfoDTO);

    /**
     * 删除银行信息
     * @param id
     */
    void delete(Long id);

}
