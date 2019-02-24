package com.smile.start.service.contract.impl;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoginHandler;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.*;
import com.smile.start.dto.*;
import com.smile.start.mapper.ContractInfoMapper;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.contract.*;
import com.smile.start.model.enums.ContractStatusEnum;
import com.smile.start.model.login.LoginUser;
import com.smile.start.service.contract.ContractInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private ContractAttachDao contractAttachDao;

    @Resource
    private ContractInfoMapper contractInfoMapper;

    /**
     * 根据主键获取合同信息
     * @param id
     * @return
     */
    @Override
    public ContractInfoDTO get(Long id) {
        ContractInfoDTO contractInfoDTO = new ContractInfoDTO();
        final ContractInfo contractInfo = contractInfoDao.get(id);
        final ContractBaseInfoDTO contractBaseInfoDTO = contractInfoMapper.do2dto(contractInfo);
        contractInfoDTO.setBaseInfo(contractBaseInfoDTO);

        final ContractExtendInfo contractExtendInfo =
                contractExtendInfoDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractExtendInfo(contractInfoMapper.do2dto(contractExtendInfo));
        final ContractReceivableAgreement contractReceivableAgreement =
                contractReceivableAgreementDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractReceivableAgreement(contractInfoMapper.do2dto(contractReceivableAgreement));
        final ContractReceivableConfirmation contractReceivableConfirmation =
                contractReceivableConfirmationDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractReceivableConfirmation(contractInfoMapper.do2dto(contractReceivableConfirmation));
        final List<ContractSignList> signList =
                contractSignListDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setSignList(contractInfoMapper.doList2dtoListSign(signList));
        final List<ContractAttach> attachList = contractAttachDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setAttachList(contractInfoMapper.doList2dtoListAttach(attachList));
        return contractInfoDTO;
    }

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
    @Transactional
    public Long insert(ContractInfoDTO contractInfoDTO) {
        final ContractInfo contractInfo = contractInfoMapper.dto2do(contractInfoDTO.getBaseInfo());
        String contractSerialNo = SerialNoGenerator.generateSerialNo("C",7);
        contractInfo.setSerialNo(contractSerialNo);
        Date nowDate = new Date();
        contractInfo.setGmtCreate(nowDate);
        contractInfo.setGmtModify(nowDate);
        contractInfo.setStatus(ContractStatusEnum.APPLY.getValue());
        LoginUser loginUser = LoginHandler.getLoginUser();
        contractInfo.setCreateUser(loginUser.getSerialNo());

        //保存签署清单
        insertSignList(contractInfoDTO.getSignList(), contractSerialNo);

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

        //保险附件
        insertAttachList(contractInfoDTO.getAttachList(), contractSerialNo);
        return contractInfoDao.insert(contractInfo);
    }

    /**
     * 修改合同基本信息
     * @param contractInfoDTO
     * @return
     */
    @Override
    @Transactional
    public void update(ContractInfoDTO contractInfoDTO) {
        final ContractInfo contractInfo = contractInfoMapper.dto2do(contractInfoDTO.getBaseInfo());
        contractInfo.setGmtModify(new Date());
        LoginUser loginUser = LoginHandler.getLoginUser();
        contractInfo.setModifyUser(loginUser.getSerialNo());
        contractInfoDao.update(contractInfo);

        //更新合同信息
        final ContractExtendInfo contractExtendInfo = contractInfoMapper.dto2do(contractInfoDTO.getContractExtendInfo());
        contractExtendInfoDao.update(contractExtendInfo);

        //更新应收账款转让确认函
        final ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractInfoDTO.getContractReceivableConfirmation());
        contractReceivableConfirmationDao.update(contractReceivableConfirmation);

        //更新应收账款转让登记协议
        final ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractInfoDTO.getContractReceivableAgreement());
        contractReceivableAgreementDao.update(contractReceivableAgreement);

        //更新签署清单
        contractSignListDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        insertSignList(contractInfoDTO.getSignList(), contractInfo.getSerialNo());

        //更新附件信息
        contractAttachDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        insertAttachList(contractInfoDTO.getAttachList(), contractInfo.getSerialNo());
    }

    /**
     * 插入签署清单
     * @param signList
     * @param contractSerialNo
     */
    private void insertSignList(List<ContractSignListDTO> signList, String contractSerialNo) {
        if(!CollectionUtils.isEmpty(signList)) {
            signList.forEach(e -> {
                ContractSignList contractSignList = new ContractSignList();
                contractSignList.setSerialNo(SerialNoGenerator.generateSerialNo("CSL",5));
                contractSignList.setContractSerialNo(contractSerialNo);
                contractSignList.setSignListName(e.getSignListName());
                contractSignListDao.insert(contractSignList);
            });
        }
    }

    /**
     * 插入附件信息
     * @param attachList
     * @param contractSerialNo
     */
    private void insertAttachList(List<ContractAttachDTO> attachList, String contractSerialNo) {
        if(!CollectionUtils.isEmpty(attachList)) {
            attachList.forEach(e -> {
                e.setSerialNo(SerialNoGenerator.generateSerialNo("CA",5));
                e.setContractSerialNo(contractSerialNo);
                contractAttachDao.insert(contractInfoMapper.dto2do(e));
            });
        }
    }

    /**
     * 删除合同信息
     * @param id
     */
    @Override
    @Transactional
    public void delete(Long id) {
        ContractInfo contractInfo = contractInfoDao.get(id);
        contractSignListDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractReceivableConfirmationDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractReceivableAgreementDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractExtendInfoDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDao.delete(id);
    }
}
