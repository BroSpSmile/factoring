package com.smile.start.service.contract.impl;

import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.ContractInfoDao;
import com.smile.start.dto.ContractInfoDTO;
import com.smile.start.mapper.ContractInfoMapper;
import com.smile.start.model.contract.ContractInfo;
import com.smile.start.service.contract.ContractInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:49, ContractInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {

    @Resource
    private ContractInfoDao contractInfoDao;

    @Resource
    private ContractInfoMapper contractInfoMapper;

    /**
     * 插入合同基本信息
     * @param contractInfoDTO
     * @return
     */
    @Override
    public Long insert(ContractInfoDTO contractInfoDTO) {
        final ContractInfo contractInfo = contractInfoMapper.dto2do(contractInfoDTO);
        contractInfo.setSerialNo(SerialNoGenerator.generateSerialNo("C",7));
        Date nowDate = new Date();
        contractInfo.setGmtCreate(nowDate);
        contractInfo.setGmtModify(nowDate);
        long id = contractInfoDao.insert(contractInfo);
        return id;
    }

    /**
     * 修改合同基本信息
     * @param contractInfoDTO
     * @return
     */
    @Override
    public void update(ContractInfoDTO contractInfoDTO) {
        final ContractInfo contractInfo = contractInfoMapper.dto2do(contractInfoDTO);
        contractInfo.setGmtModify(new Date());
        contractInfoDao.update(contractInfo);
    }
}
