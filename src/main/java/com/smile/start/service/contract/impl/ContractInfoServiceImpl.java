package com.smile.start.service.contract.impl;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.*;
import com.smile.start.dto.*;
import com.smile.start.mapper.ContractInfoMapper;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.contract.*;
import com.smile.start.model.enums.ContractStatusEnum;
import com.smile.start.service.contract.ContractInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    private ContractExtendInfoDao contractExtendInfoDao;

    @Resource
    private ContractReceivableAgreementDao contractReceivableAgreementDao;

    @Resource
    private ContractReceivableConfirmationDao contractReceivableConfirmationDao;

    @Resource
    private ContractSignListDao contractSignListDao;

    @Resource
    private ContractInfoMapper contractInfoMapper;

    @Override
    public PageInfo<ContractBaseInfoDTO> findAll(PageRequest<ContractInfoSearchDTO> page) {
        final PageInfo<ContractBaseInfoDTO> result = new PageInfo<>();
        final List<ContractInfo> doList = contractInfoDao.findByParam(page.getCondition());
        result.setTotal(doList.size());
        result.setPageSize(10);
        result.setList(contractInfoMapper.doList2dtoListBase(doList));
        return result;
    }

    /**
     * 插入合同基本信息
     * @param contractInfoDTO
     * @return
     */
    @Override
    public Long insert(ContractInfoDTO contractInfoDTO) {
        final ContractInfo contractInfo = contractInfoMapper.dto2do(contractInfoDTO.getBaseInfo());
        String contractSerialNo = SerialNoGenerator.generateSerialNo("C",7);
        contractInfo.setSerialNo(contractSerialNo);
        Date nowDate = new Date();
        contractInfo.setGmtCreate(nowDate);
        contractInfo.setGmtModify(nowDate);
        contractInfo.setStatus(ContractStatusEnum.APPLY.getValue());

        //保存签署清单
        if(!CollectionUtils.isEmpty(contractInfoDTO.getSignList())) {
            contractInfoDTO.getSignList().forEach(e -> {
                ContractSignList contractSignList = new ContractSignList();
                contractSignList.setSerialNo(SerialNoGenerator.generateSerialNo("CSL",5));
                contractSignList.setContractSerialNo(contractSerialNo);
                contractSignList.setSignListName(e.getSignListName());
                contractSignListDao.insert(contractSignList);
            });
        }

        //保存合同信息
        final ContractExtendInfo contractExtendInfo = contractInfoMapper.dto2do(contractInfoDTO.getContractExtendInfo());
        contractExtendInfo.setSerialNo(SerialNoGenerator.generateSerialNo("CEI",5));
        contractExtendInfo.setContractSerialNo(contractSerialNo);
        contractExtendInfoDao.insert(contractExtendInfo);

        //保存应收账款转让确认函
        final ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractInfoDTO.getContractReceivableConfirmation());
        contractReceivableConfirmation.setSerialNo(SerialNoGenerator.generateSerialNo("CRC",5));
        contractReceivableConfirmation.setContractSerialNo(contractSerialNo);
        contractReceivableConfirmationDao.insert(contractReceivableConfirmation);

        //保存应收账款转让登记协议
        final ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractInfoDTO.getContractReceivableAgreement());
        contractReceivableAgreement.setSerialNo(SerialNoGenerator.generateSerialNo("CRA",5));
        contractReceivableAgreement.setContractSerialNo(contractSerialNo);
        contractReceivableAgreementDao.insert(contractReceivableAgreement);
        return contractInfoDao.insert(contractInfo);
    }

    /**
     * 修改合同基本信息
     * @param contractInfoDTO
     * @return
     */
    @Override
    public void update(ContractInfoDTO contractInfoDTO) {
        final ContractInfo contractInfo = contractInfoMapper.dto2do(contractInfoDTO.getBaseInfo());
        contractInfo.setGmtModify(new Date());
        contractInfoDao.update(contractInfo);
    }

    /**
     * 删除合同信息
     * @param id
     */
    @Override
    public void delete(Long id) {
        ContractInfo contractInfo = contractInfoDao.get(id);
        contractSignListDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractReceivableConfirmationDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractReceivableAgreementDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractExtendInfoDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDao.delete(id);
    }
}
