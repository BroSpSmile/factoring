package com.smile.start.service.contract.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoginHandler;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.ContractAttachDao;
import com.smile.start.dao.ContractAuditRecordDao;
import com.smile.start.dao.ContractExtendInfoDao;
import com.smile.start.dao.ContractInfoDao;
import com.smile.start.dao.ContractReceivableAgreementDao;
import com.smile.start.dao.ContractReceivableConfirmationDao;
import com.smile.start.dao.ContractSignListDao;
import com.smile.start.dao.ProjectDao;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.ContractAttachDTO;
import com.smile.start.dto.ContractAuditDTO;
import com.smile.start.dto.ContractAuditRecordDTO;
import com.smile.start.dto.ContractAuditSearchDTO;
import com.smile.start.dto.ContractBaseInfoDTO;
import com.smile.start.dto.ContractInfoDTO;
import com.smile.start.dto.ContractInfoSearchDTO;
import com.smile.start.dto.ContractSignDTO;
import com.smile.start.dto.ContractSignListDTO;
import com.smile.start.exception.ValidateException;
import com.smile.start.mapper.ContractInfoMapper;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.contract.ContractAttach;
import com.smile.start.model.contract.ContractAuditRecord;
import com.smile.start.model.contract.ContractExtendInfo;
import com.smile.start.model.contract.ContractInfo;
import com.smile.start.model.contract.ContractReceivableAgreement;
import com.smile.start.model.contract.ContractReceivableConfirmation;
import com.smile.start.model.contract.ContractSignList;
import com.smile.start.model.enums.ContractAttachTypeEnum;
import com.smile.start.model.enums.ContractStatusEnum;
import com.smile.start.model.login.LoginUser;
import com.smile.start.model.project.Project;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.contract.ContractInfoService;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:49, ContractInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {

    @Resource
    private ContractInfoDao                   contractInfoDao;

    @Resource
    private ContractExtendInfoDao             contractExtendInfoDao;

    @Resource
    private ContractReceivableAgreementDao    contractReceivableAgreementDao;

    @Resource
    private ContractReceivableConfirmationDao contractReceivableConfirmationDao;

    @Resource
    private ContractSignListDao               contractSignListDao;

    @Resource
    private ContractAttachDao                 contractAttachDao;

    @Resource
    private ContractAuditRecordDao            contractAuditRecordDao;

    @Resource
    private ProjectDao                        projectDao;

    @Resource
    private UserInfoService                   userInfoService;

    @Resource
    private ContractInfoMapper                contractInfoMapper;

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
        AuthUserInfoDTO userInfo = userInfoService.findBySerialNo(contractInfo.getCreateUser());
        contractBaseInfoDTO.setCreateUser(userInfo.getUsername());

        final ContractExtendInfo contractExtendInfo = contractExtendInfoDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractExtendInfo(contractInfoMapper.do2dto(contractExtendInfo));
        final ContractReceivableAgreement contractReceivableAgreement = contractReceivableAgreementDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractReceivableAgreement(contractInfoMapper.do2dto(contractReceivableAgreement));
        final ContractReceivableConfirmation contractReceivableConfirmation = contractReceivableConfirmationDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractReceivableConfirmation(contractInfoMapper.do2dto(contractReceivableConfirmation));
        final List<ContractSignList> signList = contractSignListDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setSignList(contractInfoMapper.doList2dtoListSign(signList));
        final List<ContractAttach> attachList = contractAttachDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setAttachList(contractInfoMapper.doList2dtoListAttach(attachList));

        final Project project = projectDao.get(contractInfo.getProjectId());
        contractInfoDTO.setProject(project);
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
    @Transactional(rollbackFor = Exception.class)
    public Long insert(ContractInfoDTO contractInfoDTO) {
        final ContractInfo contractInfo = contractInfoMapper.dto2do(contractInfoDTO.getBaseInfo());
        String contractSerialNo = SerialNoGenerator.generateSerialNo("C", 7);
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
        contractExtendInfo.setSerialNo(SerialNoGenerator.generateSerialNo("CEI", 5));
        contractExtendInfo.setContractSerialNo(contractSerialNo);
        contractExtendInfoDao.insert(contractExtendInfo);

        //保存应收账款转让确认函
        final ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractInfoDTO.getContractReceivableConfirmation());
        contractReceivableConfirmation.setSerialNo(SerialNoGenerator.generateSerialNo("CRC", 5));
        contractReceivableConfirmation.setContractSerialNo(contractSerialNo);
        contractReceivableConfirmationDao.insert(contractReceivableConfirmation);

        //保存应收账款转让登记协议
        final ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractInfoDTO.getContractReceivableAgreement());
        contractReceivableAgreement.setSerialNo(SerialNoGenerator.generateSerialNo("CRA", 5));
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
    @Transactional(rollbackFor = Exception.class)
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
        if (!CollectionUtils.isEmpty(signList)) {
            signList.forEach(e -> {
                ContractSignList contractSignList = new ContractSignList();
                contractSignList.setSerialNo(SerialNoGenerator.generateSerialNo("CSL", 5));
                contractSignList.setContractSerialNo(contractSerialNo);
                contractSignList.setSignListName(e.getSignListName());
                contractSignList.setStatus(false);
                contractSignList.setIsRequired(e.getIsRequired());
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
        if (!CollectionUtils.isEmpty(attachList)) {
            attachList.forEach(e -> {
                e.setSerialNo(SerialNoGenerator.generateSerialNo("CA", 5));
                e.setContractSerialNo(contractSerialNo);
                e.setAttachType(ContractAttachTypeEnum.USER_DEFINED.getValue());
                contractAttachDao.insert(contractInfoMapper.dto2do(e));
            });
        }
    }

    /**
     * 删除合同信息
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        ContractInfo contractInfo = contractInfoDao.get(id);
        contractSignListDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractReceivableConfirmationDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractReceivableAgreementDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractExtendInfoDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDao.delete(id);
    }

    /**
     * 提交审核
     * @param id
     */
    @Override
    public void submitAudit(Long id) {
        ContractInfo contractInfo = contractInfoDao.get(id);
        contractInfo.setStatus(ContractStatusEnum.DEPARTMENT_AUDIT.getValue());
        contractInfoDao.update(contractInfo);

        //保存审核记录
        ContractAuditRecord contractAuditRecord = new ContractAuditRecord();
        contractAuditRecord.setSerialNo(SerialNoGenerator.generateSerialNo("CAR", 5));
        contractAuditRecord.setContractSerialNo(contractInfo.getSerialNo());
        contractAuditRecord.setOperationStatus(ContractStatusEnum.APPLY.getDesc());
        contractAuditRecord.setOperationType(1);
        contractAuditRecord.setOperationTime(new Date());
        contractAuditRecord.setOperationUser(LoginHandler.getLoginUser().getUsername());
        contractAuditRecordDao.insert(contractAuditRecord);
    }

    /**
     * 此方法只供合同审核列表用，根据当前登录用户查询待审核的合同列表
     * @return
     */
    @Override
    public PageInfo<ContractBaseInfoDTO> findAuditList(PageRequest<ContractAuditSearchDTO> page) {
        final PageInfo<ContractBaseInfoDTO> result = new PageInfo<>();
        String userSerialNo = LoginHandler.getLoginUser().getSerialNo();
        ContractAuditSearchDTO condition = page.getCondition();
        condition.setUserSerialNo(userSerialNo);
        final List<ContractInfo> auditList = contractInfoDao.findAuditList(condition);
        result.setTotal(auditList.size());
        result.setPageSize(10);
        result.setList(contractInfoMapper.doList2dtoListBase(auditList));
        return result;
    }

    /**
     * 合同审核
     * @param contractAuditDTO
     */
    @Override
    public void audit(ContractAuditDTO contractAuditDTO) {
        final ContractInfo contractInfo = contractInfoDao.findBySerialNo(contractAuditDTO.getContractSerialNo());
        ContractStatusEnum currentStatus = ContractStatusEnum.fromValue(contractInfo.getStatus());
        //状态合法性校验
        if (currentStatus == null) {
            throw new ValidateException("合同状态非法，status = " + contractInfo.getStatus());
        }

        //审核通过
        if (contractAuditDTO.getOperationType() == 1) {
            contractInfo.setStatus(currentStatus.getNextStatus().getValue());
            //如果审核完成通知办公室
            if (currentStatus.getNextStatus() == ContractStatusEnum.FINISH) {
                //TODO 调用通知接口
            }
        } else {
            //默认驳回到上一状态
            if (contractAuditDTO.getRejectStatus() != null && contractAuditDTO.getRejectStatus() != 0) {
                contractInfo.setStatus(contractAuditDTO.getRejectStatus());
            } else {
                contractInfo.setStatus(currentStatus.getDefaultRejectStatus().getValue());
            }
        }
        contractInfoDao.update(contractInfo);

        //保存审核记录
        ContractAuditRecord contractAuditRecord = new ContractAuditRecord();
        contractAuditRecord.setSerialNo(SerialNoGenerator.generateSerialNo("CAR", 5));
        contractAuditRecord.setContractSerialNo(contractInfo.getSerialNo());
        contractAuditRecord.setOperationStatus(currentStatus.getDesc());
        contractAuditRecord.setOperationType(contractAuditDTO.getOperationType());
        contractAuditRecord.setOperationTime(new Date());
        contractAuditRecord.setRemark(contractAuditDTO.getRemark());
        contractAuditRecord.setOperationUser(LoginHandler.getLoginUser().getUsername());
        contractAuditRecordDao.insert(contractAuditRecord);
    }

    /**
     * 查询合同审核历史
     * @param contractSerialNo
     * @return
     */
    @Override
    public List<ContractAuditRecordDTO> findAuditRecord(String contractSerialNo) {
        return contractInfoMapper.doList2dtoListAuditRecord(contractAuditRecordDao.findByContractSerialNo(contractSerialNo));
    }

    /**
     * 获取合同签署清单列表
     * @param contractSerialNo
     * @return
     */
    @Override
    public List<ContractSignListDTO> findSignListByContractSerialNo(String contractSerialNo) {
        return contractInfoMapper.doList2dtoListSign(contractSignListDao.findByContractSerialNo(contractSerialNo));
    }

    /**
     * 保存签署信息
     * @param contractSignDTO
     */
    @Override
    public void saveSign(ContractSignDTO contractSignDTO) {
        if (!CollectionUtils.isEmpty(contractSignDTO.getSignList())) {
            contractSignDTO.getSignList().forEach(e -> contractSignListDao.update(contractInfoMapper.dto2do(e)));
        }

        if (contractSignDTO.getFinished()) {
            final ContractInfo contractInfo = contractInfoDao.findBySerialNo(contractSignDTO.getSerialNo());
            contractInfo.setStatus(ContractStatusEnum.SIGN_FINISH.getValue());
            contractInfoDao.update(contractInfo);
        }
    }
}
