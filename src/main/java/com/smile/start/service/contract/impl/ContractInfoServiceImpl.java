package com.smile.start.service.contract.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.smile.start.model.enums.*;
import com.smile.start.model.project.*;
import com.smile.start.service.project.FactoringService;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.DocUtil;
import com.smile.start.commons.LoginHandler;
import com.smile.start.commons.MoneyToChineseUtil;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.AuditDao;
import com.smile.start.dao.AuditRecordDao;
import com.smile.start.dao.ContractAuditRecordDao;
import com.smile.start.dao.ContractExtendInfoDao;
import com.smile.start.dao.ContractFasaDao;
import com.smile.start.dao.ContractInfoDao;
import com.smile.start.dao.ContractReceivableAgreementDao;
import com.smile.start.dao.ContractReceivableConfirmationDao;
import com.smile.start.dao.ContractShareholderMeetingDao;
import com.smile.start.dao.ContractSignListDao;
import com.smile.start.dao.FlowConfigDao;
import com.smile.start.dao.ProjectItemDao;
import com.smile.start.dao.UserDao;
import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.dto.ContractAttachDTO;
import com.smile.start.dto.ContractAuditDTO;
import com.smile.start.dto.ContractAuditRecordDTO;
import com.smile.start.dto.ContractAuditSearchDTO;
import com.smile.start.dto.ContractBaseInfoDTO;
import com.smile.start.dto.ContractExtendInfoDTO;
import com.smile.start.dto.ContractFasaDTO;
import com.smile.start.dto.ContractInfoDTO;
import com.smile.start.dto.ContractInfoSearchDTO;
import com.smile.start.dto.ContractReceivableAgreementDTO;
import com.smile.start.dto.ContractReceivableConfirmationDTO;
import com.smile.start.dto.ContractShareholderMeetingDTO;
import com.smile.start.dto.ContractSignDTO;
import com.smile.start.dto.ContractSignListDTO;
import com.smile.start.exception.ValidateException;
import com.smile.start.mapper.ContractInfoMapper;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.common.FileInfo;
import com.smile.start.model.common.FlowStatus;
import com.smile.start.model.contract.ContractExtendInfo;
import com.smile.start.model.contract.ContractFasa;
import com.smile.start.model.contract.ContractInfo;
import com.smile.start.model.contract.ContractReceivableAgreement;
import com.smile.start.model.contract.ContractReceivableConfirmation;
import com.smile.start.model.contract.ContractShareholderMeeting;
import com.smile.start.model.contract.ContractSignList;
import com.smile.start.model.login.LoginUser;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.auth.RoleInfoService;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.common.FileService;
import com.smile.start.service.contract.ContractInfoService;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.project.ProjectService;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:49, ContractInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {
    private Logger                            logger = LoggerFactory.getLogger(ContractInfoServiceImpl.class);

    @Resource
    private ContractInfoDao                   contractInfoDao;

    @Resource
    private ContractExtendInfoDao             contractExtendInfoDao;

    @Resource
    private ContractReceivableAgreementDao    contractReceivableAgreementDao;

    @Resource
    private ContractReceivableConfirmationDao contractReceivableConfirmationDao;

    @Resource
    private ContractFasaDao                   contractFasaDao;

    @Resource
    private ContractShareholderMeetingDao     contractShareholderMeetingDao;

    @Resource
    private ContractSignListDao               contractSignListDao;

    @Resource
    private ContractAuditRecordDao            contractAuditRecordDao;

    @Resource
    private AuditDao                          auditDao;

    @Resource
    private AuditRecordDao                    auditRecordDao;

    @Resource
    private ProjectItemDao                    projectItemDao;

    @Resource
    private UserDao                           userDao;

    @Resource
    private FlowConfigDao                     flowConfigDao;

    @Resource
    private UserInfoService                   userInfoService;

    @Resource
    private RoleInfoService                   roleInfoService;

    @Resource
    private AuditService                      auditService;

    @Resource
    private ProjectService                    projectService;

    @Resource
    private FactoringService                  factoringService;

    @Resource
    private FileService                       fileService;

    @Resource
    private ProcessEngine                     processEngine;

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

        //保理合同信息
        final ContractExtendInfo contractExtendInfo = contractExtendInfoDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractExtendInfo(contractInfoMapper.do2dto(contractExtendInfo));

        //登记协议
        final ContractReceivableAgreement contractReceivableAgreement = contractReceivableAgreementDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractReceivableAgreement(contractInfoMapper.do2dto(contractReceivableAgreement));

        //确认函
        final ContractReceivableConfirmation contractReceivableConfirmation = contractReceivableConfirmationDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractReceivableConfirmation(contractInfoMapper.do2dto(contractReceivableConfirmation));

        //财务顾问服务协议
        if (contractInfo.getProjectMode() == 2) {
            final ContractFasa contractFasa = contractFasaDao.findByContractSerialNo(contractInfo.getSerialNo());
            contractInfoDTO.setContractFasa(contractInfoMapper.do2dto(contractFasa));
        }

        //股东会决议
        final ContractShareholderMeeting contractShareholderMeeting = contractShareholderMeetingDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setContractShareholderMeeting(contractInfoMapper.do2dto(contractShareholderMeeting));

        //签署清单
        final List<ContractSignList> signList = contractSignListDao.findByContractSerialNo(contractInfo.getSerialNo());
        contractInfoDTO.setSignList(contractInfoMapper.doList2dtoListSign(signList));

        //获取合同附件
        contractInfoDTO.setAttachList(getAttachList(contractInfo.getProjectId()));
        final Project project = projectService.getProject(contractInfo.getProjectId());
        contractInfoDTO.setProject(project);
        return contractInfoDTO;
    }

    /**
     * 根据主键获取合同基础信息
     * @param projectId
     * @return
     */
    @Override
    public ContractInfo getBaseInfo(Long projectId) {
        return contractInfoDao.getByProjectId(projectId);
    }

    /**
     * 根据项目主键获取合同信息
     * @param projectId
     * @return
     */
    @Override
    public ContractInfoDTO getByProjectId(Long projectId) {
        final ContractInfo contractInfo = contractInfoDao.getByProjectId(projectId);
        if (contractInfo == null) {
            return null;
        }
        return get(contractInfo.getId());
    }

    @Override
    public PageInfo<ContractBaseInfoDTO> findAll(PageRequest<ContractInfoSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "id desc");
        final List<ContractInfo> contractList = contractInfoDao.findByParam(pageRequest.getCondition());
        PageInfo<ContractBaseInfoDTO> pageInfo = new PageInfo<>(contractInfoMapper.doList2dtoListBase(contractList));
        Page<ContractInfo> page = (Page<ContractInfo>) contractList;
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageNum(pageRequest.getPageNum());
        pageInfo.setPageSize(pageRequest.getPageSize());
        return pageInfo;
    }

    /**
     * 插入合同基本信息
     * @param contractInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insert(ContractInfoDTO contractInfoDTO) throws Exception {
        final ContractBaseInfoDTO contractBaseInfoDTO = contractInfoDTO.getBaseInfo();
        String contractSerialNo = SerialNoGenerator.generateSerialNo("C", 7);
        contractBaseInfoDTO.setSerialNo(contractSerialNo);
        contractBaseInfoDTO.setStatus(ContractStatusEnum.NEW.getValue());
        LoginUser loginUser = LoginHandler.getLoginUser();
        contractBaseInfoDTO.setCreateUser(loginUser.getSerialNo());
        contractBaseInfoDTO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getValue());
        contractBaseInfoDTO.setSealStatus(SealStatusEnum.NOT_STAMPED.getValue());
        final Project project = projectService.getProject(contractInfoDTO.getBaseInfo().getProjectId());
        final ContractInfo contractInfo = contractInfoMapper.dto2do(contractBaseInfoDTO);
        Date nowDate = new Date();
        contractInfo.setGmtCreate(nowDate);
        contractInfo.setGmtModify(nowDate);

        //保存签署清单
        insertSignList(contractInfoDTO.getSignList(), contractSerialNo, project.getId());

        //保理合同编号
        String contractCode = project.getProjectId() + "-1";

        //保存合同信息
        if (contractInfo.getFactoringContract() != null && contractInfo.getFactoringContract()) {
            ContractExtendInfoDTO contractExtendInfoDTO = contractInfoDTO.getContractExtendInfo();
            contractExtendInfoDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CEI", 5));
            contractExtendInfoDTO.setContractSerialNo(contractSerialNo);
            contractExtendInfoDTO.setContractCode(contractCode);
            ContractExtendInfo contractExtendInfo = contractInfoMapper.dto2do(contractExtendInfoDTO);
            contractExtendInfoDao.insert(contractExtendInfo);

            Integer projectMode = contractInfoDTO.getBaseInfo().getProjectMode();
            String contractFileName;
            if (projectMode == 1) {
                contractFileName = "保理合同（有追索权）" + contractExtendInfo.getContractCode() + ".doc";
            } else {
                contractFileName = "保理合同（无追索权）" + contractExtendInfo.getContractCode() + ".doc";
            }
            File contractFile = DocUtil.createDoc(contractFileName, "factoringContract_" + projectMode + ".xml",
                buildTemplateData(contractExtendInfoDTO, project.getProjectId() + "-4", projectMode));
            upload(contractFile, contractFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        }

        //保存应收账款转让确认函
        if (contractInfo.getConfirmationLetter() != null && contractInfo.getConfirmationLetter()) {
            ContractReceivableConfirmationDTO contractReceivableConfirmationDTO = contractInfoDTO.getContractReceivableConfirmation();
            contractReceivableConfirmationDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CRC", 5));
            contractReceivableConfirmationDTO.setContractSerialNo(contractSerialNo);
            contractReceivableConfirmationDTO.setConfirmationCode(project.getProjectId() + "-2");
            ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractReceivableConfirmationDTO);
            contractReceivableConfirmationDao.insert(contractReceivableConfirmation);

            String confirmationFileName = "附件1：应收账款转让确认函" + contractReceivableConfirmation.getConfirmationCode() + ".doc";
            File confirmationFile = DocUtil.createDoc(confirmationFileName, "confirmationLetter_" + contractInfoDTO.getBaseInfo().getProjectMode() + ".xml",
                buildTemplateData(contractReceivableConfirmationDTO, contractCode));
            upload(confirmationFile, confirmationFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        }

        //保存应收账款转让登记协议
        if (contractInfo.getRegistrationAgreement() != null && contractInfo.getRegistrationAgreement()) {
            ContractReceivableAgreementDTO contractReceivableAgreementDTO = contractInfoDTO.getContractReceivableAgreement();
            contractReceivableAgreementDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CRA", 5));
            contractReceivableAgreementDTO.setContractSerialNo(contractSerialNo);
            contractReceivableAgreementDTO.setProtocolCode(project.getProjectId() + "-3");
            ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractReceivableAgreementDTO);
            contractReceivableAgreementDao.insert(contractReceivableAgreement);

            String agreementFileName = "附件2：应收账款转让登记协议" + contractReceivableAgreement.getProtocolCode() + ".doc";
            File agreementFile = DocUtil.createDoc(agreementFileName, "registrationAgreement_" + contractInfoDTO.getBaseInfo().getProjectMode() + ".xml",
                buildTemplateData(contractReceivableAgreementDTO, contractCode));
            upload(agreementFile, agreementFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        }

        //保存财务顾问协议，无追合同才有
        if (contractInfoDTO.getBaseInfo().getProjectMode() == 2 && contractInfo.getFinancialAgreement() != null && contractInfo.getFinancialAgreement()) {
            ContractFasaDTO contractFasaDTO = contractInfoDTO.getContractFasa();
            contractFasaDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CF", 5));
            contractFasaDTO.setContractSerialNo(contractSerialNo);
            contractFasaDTO.setFasaCode(project.getProjectId() + "-4");
            ContractFasa contractFasa = contractInfoMapper.dto2do(contractFasaDTO);
            contractFasaDao.insert(contractFasa);

            String fasaFileName = "财务顾问服务协议" + contractFasa.getFasaCode() + ".doc";
            File fasaFile = DocUtil.createDoc(fasaFileName, "financialAgreement_" + contractInfoDTO.getBaseInfo().getProjectMode() + ".xml",
                buildTemplateData(contractFasaDTO, contractCode));
            upload(fasaFile, fasaFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        }

        //保存股东会决议
        if (contractInfo.getShareholderResolution() != null && contractInfo.getShareholderResolution()) {
            ContractShareholderMeetingDTO contractShareholderMeetingDTO = contractInfoDTO.getContractShareholderMeeting();
            contractShareholderMeetingDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CSM", 5));
            contractShareholderMeetingDTO.setContractSerialNo(contractSerialNo);
            ContractShareholderMeeting contractShareholderMeeting = contractInfoMapper.dto2do(contractShareholderMeetingDTO);
            contractShareholderMeetingDao.insert(contractShareholderMeeting);

            String shareholderFileName = "股东会决议.doc";
            File shareholderFile = DocUtil.createDoc(shareholderFileName, "shareholderResolution_" + contractInfoDTO.getBaseInfo().getProjectMode() + ".xml",
                buildTemplateData(contractShareholderMeetingDTO, contractCode));
            upload(shareholderFile, shareholderFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        }

        //自定义附件合同
        insertAttachList(contractInfoDTO);
        contractInfoDao.insert(contractInfo);
        return contractInfo.getId();
    }

    /**
     * 修改合同基本信息
     * @param contractInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContractInfoDTO contractInfoDTO) throws Exception {
        final ContractBaseInfoDTO baseInfo = contractInfoDTO.getBaseInfo();
        final ContractInfo contractInfo = contractInfoDao.get(baseInfo.getId());
        contractInfo.setGmtModify(new Date());
        LoginUser loginUser = LoginHandler.getLoginUser();
        contractInfo.setModifyUser(loginUser.getSerialNo());
        contractInfo.setContractTemplate(baseInfo.getContractTemplate());
        contractInfo.setFactoringContract(baseInfo.getFactoringContract());
        contractInfo.setRegistrationAgreement(baseInfo.getRegistrationAgreement());
        contractInfo.setConfirmationLetter(baseInfo.getConfirmationLetter());
        contractInfo.setFinancialAgreement(baseInfo.getFinancialAgreement());
        contractInfo.setShareholderResolution(baseInfo.getShareholderResolution());
        contractInfoDao.update(contractInfo);

        final Project project = projectService.getProject(baseInfo.getProjectId());

        //更新附件信息，先批量删除再插入
        List<ProjectItem> typeItems = projectItemDao.getTypeItems(contractInfo.getProjectId(), ProjectItemType.DRAWUP);
        if (!CollectionUtils.isEmpty(typeItems)) {
            typeItems.forEach(e -> projectItemDao.delete(e));
        }

        //插入自定义附件
        insertAttachList(contractInfoDTO);

        //保理合同编号
        String contractCode = project.getProjectId() + "-1";

        //更新保理合同信息
        if (baseInfo.getFactoringContract() != null && baseInfo.getFactoringContract()) {
            final ContractExtendInfo oldContractExtendInfo = contractExtendInfoDao.findByContractSerialNo(contractInfo.getSerialNo());
            ContractExtendInfoDTO contractExtendInfoDTO = contractInfoDTO.getContractExtendInfo();
            if (oldContractExtendInfo == null) {
                contractExtendInfoDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CEI", 5));
                contractExtendInfoDTO.setContractSerialNo(contractInfo.getSerialNo());
                contractExtendInfoDTO.setContractCode(contractCode);
                ContractExtendInfo contractExtendInfo = contractInfoMapper.dto2do(contractExtendInfoDTO);
                contractExtendInfoDao.insert(contractExtendInfo);
            } else {
                contractExtendInfoDTO.setContractCode(oldContractExtendInfo.getContractCode());
                ContractExtendInfo contractExtendInfo = contractInfoMapper.dto2do(contractExtendInfoDTO);
                contractExtendInfo.setContractSerialNo(oldContractExtendInfo.getContractSerialNo());
                contractExtendInfo.setId(oldContractExtendInfo.getId());
                contractExtendInfoDao.update(contractExtendInfo);
            }

            Integer projectMode = baseInfo.getProjectMode();
            String contractFileName;
            if (projectMode == 1) {
                contractFileName = "保理合同（有追索权）" + contractExtendInfoDTO.getContractCode() + ".doc";
            } else {
                contractFileName = "保理合同（无追索权）" + contractExtendInfoDTO.getContractCode() + ".doc";
            }
            File contractFile = DocUtil.createDoc(contractFileName, "factoringContract_" + projectMode + ".xml",
                buildTemplateData(contractExtendInfoDTO, project.getProjectId() + "-4", projectMode));
            upload(contractFile, contractFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        } else {
            contractExtendInfoDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        }

        //更新应收账款转让确认函
        if (baseInfo.getConfirmationLetter() != null && baseInfo.getConfirmationLetter()) {
            ContractReceivableConfirmation oldContractReceivableConfirmation = contractReceivableConfirmationDao.findByContractSerialNo(contractInfo.getSerialNo());
            ContractReceivableConfirmationDTO contractReceivableConfirmationDTO = contractInfoDTO.getContractReceivableConfirmation();
            if (oldContractReceivableConfirmation == null) {
                contractReceivableConfirmationDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CRC", 5));
                contractReceivableConfirmationDTO.setContractSerialNo(contractInfo.getSerialNo());
                contractReceivableConfirmationDTO.setConfirmationCode(project.getProjectId() + "-2");
                ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractReceivableConfirmationDTO);
                contractReceivableConfirmationDao.insert(contractReceivableConfirmation);
            } else {
                contractReceivableConfirmationDTO.setConfirmationCode(oldContractReceivableConfirmation.getConfirmationCode());
                ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractReceivableConfirmationDTO);
                contractReceivableConfirmation.setContractSerialNo(oldContractReceivableConfirmation.getContractSerialNo());
                contractReceivableConfirmation.setId(oldContractReceivableConfirmation.getId());
                contractReceivableConfirmationDao.update(contractReceivableConfirmation);
            }

            String confirmationFileName = "附件1：应收账款转让确认函" + contractReceivableConfirmationDTO.getConfirmationCode() + ".doc";
            File confirmationFile = DocUtil.createDoc(confirmationFileName, "confirmationLetter_" + contractInfoDTO.getBaseInfo().getProjectMode() + ".xml",
                buildTemplateData(contractReceivableConfirmationDTO, contractCode));
            upload(confirmationFile, confirmationFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        } else {
            contractReceivableConfirmationDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        }

        //更新应收账款转让登记协议
        if (baseInfo.getRegistrationAgreement() != null && baseInfo.getRegistrationAgreement()) {
            ContractReceivableAgreement oldContractReceivableAgreement = contractReceivableAgreementDao.findByContractSerialNo(contractInfo.getSerialNo());
            ContractReceivableAgreementDTO contractReceivableAgreementDTO = contractInfoDTO.getContractReceivableAgreement();
            if (oldContractReceivableAgreement == null) {
                contractReceivableAgreementDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CRA", 5));
                contractReceivableAgreementDTO.setContractSerialNo(contractInfo.getSerialNo());
                contractReceivableAgreementDTO.setProtocolCode(project.getProjectId() + "-3");
                ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractReceivableAgreementDTO);
                contractReceivableAgreementDao.insert(contractReceivableAgreement);
            } else {
                contractReceivableAgreementDTO.setProtocolCode(oldContractReceivableAgreement.getProtocolCode());
                ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractReceivableAgreementDTO);
                contractReceivableAgreement.setContractSerialNo(oldContractReceivableAgreement.getContractSerialNo());
                contractReceivableAgreement.setId(oldContractReceivableAgreement.getId());
                contractReceivableAgreementDao.update(contractReceivableAgreement);
            }

            String agreementFileName = "附件2：应收账款转让登记协议" + contractReceivableAgreementDTO.getProtocolCode() + ".doc";
            File agreementFile = DocUtil.createDoc(agreementFileName, "registrationAgreement_" + contractInfoDTO.getBaseInfo().getProjectMode() + ".xml",
                buildTemplateData(contractReceivableAgreementDTO, contractCode));
            upload(agreementFile, agreementFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        } else {
            contractReceivableAgreementDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        }

        //保存财务顾问协议，无追合同才有
        if (contractInfoDTO.getBaseInfo().getProjectMode() == 2 && baseInfo.getFinancialAgreement() != null && baseInfo.getFinancialAgreement()) {
            ContractFasa oldContractFasa = contractFasaDao.findByContractSerialNo(contractInfo.getSerialNo());
            ContractFasaDTO contractFasaDTO = contractInfoDTO.getContractFasa();
            if (oldContractFasa == null) {
                contractFasaDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CF", 5));
                contractFasaDTO.setContractSerialNo(contractInfo.getSerialNo());
                contractFasaDTO.setFasaCode(project.getProjectId() + "-4");
                ContractFasa contractFasa = contractInfoMapper.dto2do(contractFasaDTO);
                contractFasaDao.insert(contractFasa);
            } else {
                contractFasaDTO.setFasaCode(oldContractFasa.getFasaCode());
                ContractFasa contractFasa = contractInfoMapper.dto2do(contractFasaDTO);
                contractFasa.setContractSerialNo(oldContractFasa.getContractSerialNo());
                contractFasa.setId(oldContractFasa.getId());
                contractFasaDao.update(contractFasa);
            }

            String fasaFileName = "财务顾问服务协议" + contractFasaDTO.getFasaCode() + ".doc";
            File fasaFile = DocUtil.createDoc(fasaFileName, "financialAgreement_" + contractInfoDTO.getBaseInfo().getProjectMode() + ".xml",
                buildTemplateData(contractFasaDTO, contractCode));
            upload(fasaFile, fasaFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        } else {
            contractFasaDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        }

        //保存股东会决议
        if (baseInfo.getShareholderResolution() != null && baseInfo.getShareholderResolution()) {
            ContractShareholderMeeting oldContractShareholderMeeting = contractShareholderMeetingDao.findByContractSerialNo(contractInfo.getSerialNo());
            ContractShareholderMeetingDTO contractShareholderMeetingDTO = contractInfoDTO.getContractShareholderMeeting();
            if (oldContractShareholderMeeting == null) {
                contractShareholderMeetingDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CSM", 5));
                contractShareholderMeetingDTO.setContractSerialNo(contractInfo.getSerialNo());
                ContractShareholderMeeting contractShareholderMeeting = contractInfoMapper.dto2do(contractShareholderMeetingDTO);
                contractShareholderMeetingDao.insert(contractShareholderMeeting);
            } else {
                ContractShareholderMeeting contractShareholderMeeting = contractInfoMapper.dto2do(contractShareholderMeetingDTO);
                contractShareholderMeeting.setContractSerialNo(oldContractShareholderMeeting.getContractSerialNo());
                contractShareholderMeeting.setId(oldContractShareholderMeeting.getId());
                contractShareholderMeetingDao.update(contractShareholderMeeting);
            }

            String shareholderFileName = "股东会决议.doc";
            File shareholderFile = DocUtil.createDoc(shareholderFileName, "shareholderResolution_" + contractInfoDTO.getBaseInfo().getProjectMode() + ".xml",
                buildTemplateData(contractShareholderMeetingDTO, contractCode));
            upload(shareholderFile, shareholderFileName, contractInfoDTO.getBaseInfo().getProjectId(), ProjectItemType.DRAWUP);
        } else {
            contractShareholderMeetingDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        }

        //更新签署清单
        contractSignListDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        insertSignList(contractInfoDTO.getSignList(), contractInfo.getSerialNo(), project.getId());
    }

    /**
     * 更新合同基础信息
     * @param contractInfo
     */
    @Override
    public void updateBaseInfo(ContractInfo contractInfo) {
        contractInfoDao.update(contractInfo);
    }

    /**
     * 标准合同文件上传并保存数据库
     * @param file
     * @param fileName
     */
    private void upload(File file, String fileName, Long projectId, ProjectItemType projectItemType) {
        if (file.exists()) {
            projectItemDao.deleteItems(projectId, ProjectItemType.FILE);
            final FileInfo upload = fileService.upload(file, fileName);
            ProjectItem projectItem = new ProjectItem();
            projectItem.setAttachType(ContractTemplateEnum.STANDARD.getValue());
            projectItem.setProjectId(projectId);
            projectItem.setItemType(projectItemType);
            projectItem.setItemName(fileName);
            projectItem.setItemValue(upload.getFileId());
            projectItemDao.insert(projectItem);
        }
    }

    /**
     * 构建登记协议模板数据
     * @param contractReceivableAgreement
     * @param contractCode
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractReceivableAgreementDTO contractReceivableAgreement, String contractCode) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("protocolCode", Strings.nullToEmpty(contractReceivableAgreement.getProtocolCode()));
        data.put("spName", Strings.nullToEmpty(contractReceivableAgreement.getSpName()));
        data.put("spResidence", Strings.nullToEmpty(contractReceivableAgreement.getSpResidence()));
        data.put("spLegalPerson", Strings.nullToEmpty(contractReceivableAgreement.getSpLegalPerson()));
        data.put("spContactAddress", Strings.nullToEmpty(contractReceivableAgreement.getSpContactAddress()));
        data.put("spPostCode", Strings.nullToEmpty(contractReceivableAgreement.getSpPostCode()));
        data.put("spTelephone", Strings.nullToEmpty(contractReceivableAgreement.getSpTelephone()));
        data.put("spFax", Strings.nullToEmpty(contractReceivableAgreement.getSpFax()));
        data.put("signDate", Strings.nullToEmpty(DateUtil.format(contractReceivableAgreement.getSignDate(), DateUtil.chineseShortFormat)));
        data.put("fpSignatureDate", Strings.nullToEmpty(DateUtil.format(contractReceivableAgreement.getSignDate(), DateUtil.spotFormat)));
        data.put("spSignatureDate", Strings.nullToEmpty(DateUtil.format(contractReceivableAgreement.getSignDate(), DateUtil.spotFormat)));
        data.put("contractCode", contractCode);
        data.put("contractSignDateYear", DateUtil.getYeah(contractReceivableAgreement.getSignDate()));
        data.put("contractSignDateMonth", DateUtil.getMonth(contractReceivableAgreement.getSignDate()));
        data.put("contractSignDateDay", DateUtil.getDay(contractReceivableAgreement.getSignDate()));
        return data;
    }

    /**
     * 构建股东会决议模板数据
     * @param contractShareholderMeeting
     * @param contractCode
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractShareholderMeetingDTO contractShareholderMeeting, String contractCode) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractCode", contractCode);
        if (contractShareholderMeeting.getMeetingTime() != null) {
            data.put("meetingTimeYear", DateUtil.getYeah(contractShareholderMeeting.getMeetingTime()));
            data.put("meetingTimeMonth", DateUtil.getMonth(contractShareholderMeeting.getMeetingTime()));
            data.put("meetingTimeDay", DateUtil.getDay(contractShareholderMeeting.getMeetingTime()));
        } else {
            data.put("meetingTimeYear", "");
            data.put("meetingTimeMonth", "");
            data.put("meetingTimeDay", "");
        }
        data.put("meetingAddress", Strings.nullToEmpty(contractShareholderMeeting.getMeetingAddress()));
        data.put("spCompanyName", Strings.nullToEmpty(contractShareholderMeeting.getSpCompanyName()));
        data.put("attendingShareholders", Strings.nullToEmpty(contractShareholderMeeting.getAttendingShareholders()));
        data.put("shareholderNumber", contractShareholderMeeting.getShareholderNumber() == null ? "" : contractShareholderMeeting.getShareholderNumber());
        data.put("meetingNumber", contractShareholderMeeting.getMeetingNumber() == null ? "" : contractShareholderMeeting.getMeetingNumber());
        data.put("passingRate", Strings.nullToEmpty(contractShareholderMeeting.getPassingRate()));
        if (contractShareholderMeeting.getSignatureDate() != null) {
            data.put("signatureDateYear", DateUtil.getYeah(contractShareholderMeeting.getSignatureDate()));
            data.put("signatureDateMonth", DateUtil.getMonth(contractShareholderMeeting.getSignatureDate()));
            data.put("signatureDateDay", DateUtil.getDay(contractShareholderMeeting.getSignatureDate()));
        } else {
            data.put("signatureDateYear", "");
            data.put("signatureDateMonth", "");
            data.put("signatureDateDay", "");
        }
        return data;
    }

    /**
     * 构建财务顾问服务协议模板数据
     * @param contractFasa
     * @param contractCode
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractFasaDTO contractFasa, String contractCode) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractCode", contractCode);
        data.put("fasaCode", Strings.nullToEmpty(contractFasa.getFasaCode()));
        data.put("fpCompanyName", Strings.nullToEmpty(contractFasa.getFpCompanyName()));
        data.put("fpResidence", Strings.nullToEmpty(contractFasa.getFpResidence()));
        data.put("fpLegalPerson", Strings.nullToEmpty(contractFasa.getFpLegalPerson()));
        data.put("fpPostCode", Strings.nullToEmpty(contractFasa.getFpPostCode()));
        data.put("fpTelephone", Strings.nullToEmpty(contractFasa.getFpTelephone()));
        data.put("fpFax", Strings.nullToEmpty(contractFasa.getFpFax()));
        data.put("signAddress", Strings.nullToEmpty(contractFasa.getSignAddress()));
        if (contractFasa.getSignDate() != null) {
            data.put("signDateYear", DateUtil.getYeah(contractFasa.getSignDate()));
            data.put("signDateMonth", DateUtil.getMonth(contractFasa.getSignDate()));
            data.put("signDateDay", DateUtil.getDay(contractFasa.getSignDate()));
        } else {
            data.put("signDateYear", "");
            data.put("signDateMonth", "");
            data.put("signDateDay", "");
        }
        data.put("advisoryServiceMoney", contractFasa.getAdvisoryServiceMoney() == null ? "" : contractFasa.getAdvisoryServiceMoney());
        data.put("advisoryServiceMoneyUpper", Strings.nullToEmpty(contractFasa.getAdvisoryServiceMoneyUpper()));
        data.put("advisoryServiceMoneyAppointment", Strings.nullToEmpty(contractFasa.getAdvisoryServiceMoneyAppointment()));
        data.put("spBankName", Strings.nullToEmpty(contractFasa.getSpBankName()));
        data.put("spAccount", Strings.nullToEmpty(contractFasa.getSpAccount()));
        data.put("expiryDateMonth", contractFasa.getExpiryDateMonth() == null ? "" : contractFasa.getExpiryDateMonth());
        data.put("fpSignatureDate", Strings.nullToEmpty(DateUtil.format(contractFasa.getSignDate(), DateUtil.spotFormat)));
        data.put("spSignatureDate", Strings.nullToEmpty(DateUtil.format(contractFasa.getSignDate(), DateUtil.spotFormat)));
        return data;
    }

    /**
     * 构建确认函模板数据
     * @param contractReceivableConfirmation
     * @param contractCode
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractReceivableConfirmationDTO contractReceivableConfirmation, String contractCode) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractCode", contractCode);
        data.put("confirmationCode", Strings.nullToEmpty(contractReceivableConfirmation.getConfirmationCode()));
        data.put("signDate", Strings.nullToEmpty(DateUtil.format(contractReceivableConfirmation.getSignDate(), DateUtil.chineseShortFormat)));
        data.put("assignor", Strings.nullToEmpty(contractReceivableConfirmation.getAssignor()));
        if (contractReceivableConfirmation.getSignDate() != null) {
            data.put("signDateYear", DateUtil.getYeah(contractReceivableConfirmation.getSignDate()));
            data.put("signDateMonth", DateUtil.getMonth(contractReceivableConfirmation.getSignDate()));
            data.put("signDateDay", DateUtil.getDay(contractReceivableConfirmation.getSignDate()));
        } else {
            data.put("signDateYear", "");
            data.put("signDateMonth", "");
            data.put("signDateDay", "");
        }
        data.put("obligor", Strings.nullToEmpty(contractReceivableConfirmation.getObligor()));
        data.put("businessContractName", Strings.nullToEmpty(contractReceivableConfirmation.getBusinessContractName()));
        data.put("receivableAssigneeMoneyUpper", Strings.nullToEmpty(contractReceivableConfirmation.getReceivableAssigneeMoneyUpper()));
        data.put("receivableAssigneeMoneyType", Strings.nullToEmpty(contractReceivableConfirmation.getReceivableAssigneeMoneyType()));
        data.put("unpaidReceivableAssigneeMoney",
            contractReceivableConfirmation.getReceivableAssigneeMoney() == null ? "" : contractReceivableConfirmation.getReceivableAssigneeMoney());
        data.put("unpaidReceivableAssigneeMoneyUpper", Strings.nullToEmpty(contractReceivableConfirmation.getReceivableAssigneeMoneyUpper()));
        data.put("unpaidReceivableAssigneeMoneyType", Strings.nullToEmpty(contractReceivableConfirmation.getUnpaidReceivableAssigneeMoneyType()));
        data.put("receivableExpiryDate", Strings.nullToEmpty(DateUtil.format(contractReceivableConfirmation.getReceivableExpiryDate(), DateUtil.chineseDtFormat)));
        data.put("receivableRecoveryMoneyUpper", Strings.nullToEmpty(contractReceivableConfirmation.getReceivableAssigneeMoneyUpper()));
        data.put("receivableRecoveryMoney", contractReceivableConfirmation.getReceivableAssigneeMoney() == null ? "" : contractReceivableConfirmation.getReceivableAssigneeMoney());
        data.put("contractReceivable", contractReceivableConfirmation.getReceivableAssigneeMoney() == null ? "" : contractReceivableConfirmation.getReceivableAssigneeMoney());
        data.put("contractReceivableUpper", Strings.nullToEmpty(contractReceivableConfirmation.getReceivableAssigneeMoneyUpper()));
        data.put("assignorAbligorReceivable",
            contractReceivableConfirmation.getReceivableAssigneeMoney() == null ? "" : contractReceivableConfirmation.getReceivableAssigneeMoney());
        data.put("assignorAbligorReceivableUpper", Strings.nullToEmpty(contractReceivableConfirmation.getReceivableAssigneeMoneyUpper()));
        data.put("unpaidAssignorAbligorReceivable",
            contractReceivableConfirmation.getReceivableAssigneeMoney() == null ? "" : contractReceivableConfirmation.getReceivableAssigneeMoney());
        data.put("unpaidAssignorAbligorReceivableUpper", Strings.nullToEmpty(contractReceivableConfirmation.getReceivableAssigneeMoneyUpper()));
        data.put("receivableAssigneeMoneyPaid",
            contractReceivableConfirmation.getReceivableAssigneeMoney() == null ? "" : contractReceivableConfirmation.getReceivableAssigneeMoney());
        data.put("receivableAssigneeMoneyPaidUpper", Strings.nullToEmpty(contractReceivableConfirmation.getReceivableAssigneeMoneyUpper()));

        data.put("assignorCommitDate", Strings.nullToEmpty(DateUtil.format(contractReceivableConfirmation.getAssignorCommitDate(), DateUtil.spotFormat)));
        data.put("assigneeAccountName", Strings.nullToEmpty(contractReceivableConfirmation.getAssigneeAccountName()));
        data.put("assigneeBankName", Strings.nullToEmpty(contractReceivableConfirmation.getAssigneeBankName()));
        data.put("assigneeAccount", Strings.nullToEmpty(contractReceivableConfirmation.getAssigneeAccount()));

        data.put("assigneeSignatureDate", Strings.nullToEmpty(DateUtil.format(contractReceivableConfirmation.getSignDate(), DateUtil.spotFormat)));
        data.put("assignorCompanyName", Strings.nullToEmpty(contractReceivableConfirmation.getAssignorCompanyName()));
        data.put("assignorSignatureDate", Strings.nullToEmpty(DateUtil.format(contractReceivableConfirmation.getSignDate(), DateUtil.spotFormat)));
        data.put("obligorCompanyName", Strings.nullToEmpty(contractReceivableConfirmation.getObligorCompanyName()));
        data.put("obligorSignatureDate", Strings.nullToEmpty(DateUtil.format(contractReceivableConfirmation.getSignDate(), DateUtil.spotFormat)));
        data.put("nameOfSubject", Strings.nullToEmpty(contractReceivableConfirmation.getNameOfSubject()));
        if (contractReceivableConfirmation.getInvoiceMoney() != null) {
            String invoiceMoneyFormat = MoneyToChineseUtil.moneyFormat(contractReceivableConfirmation.getInvoiceMoney().toString());
            data.put("invoiceMoneyUpper", MoneyToChineseUtil.convert(invoiceMoneyFormat));
            data.put("invoiceMoney", contractReceivableConfirmation.getInvoiceMoney());
        } else {
            data.put("invoiceMoneyUpper", "");
            data.put("invoiceMoney", "");
        }
        data.put("invoiceMoneyType", Strings.nullToEmpty(contractReceivableConfirmation.getInvoiceMoneyType()));
        return data;
    }

    /**
     * 构建保理合同模板数据
     * @param contractExtendInfo
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractExtendInfoDTO contractExtendInfo, String fasaCode, Integer projectMode) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractCode", Strings.nullToEmpty(contractExtendInfo.getContractCode()));
        data.put("signDate", Strings.nullToEmpty(DateUtil.format(contractExtendInfo.getSignDate(), DateUtil.chineseShortFormat)));
        data.put("spCompanyName", Strings.nullToEmpty(contractExtendInfo.getSpCompanyName()));
        data.put("spResidence", Strings.nullToEmpty(contractExtendInfo.getSpResidence()));
        data.put("spLegalPerson", Strings.nullToEmpty(contractExtendInfo.getSpLegalPerson()));
        data.put("spContactAddress", Strings.nullToEmpty(contractExtendInfo.getSpContactAddress()));
        data.put("spPostCode", Strings.nullToEmpty(contractExtendInfo.getSpPostCode()));
        data.put("spTelephone", Strings.nullToEmpty(contractExtendInfo.getSpTelephone()));
        data.put("spFax", Strings.nullToEmpty(contractExtendInfo.getSpFax()));
        data.put("obligor", Strings.nullToEmpty(contractExtendInfo.getObligor()));
        if (contractExtendInfo.getBaseSignDate() != null) {
            data.put("signDateYear", DateUtil.getYeah(contractExtendInfo.getBaseSignDate()));
            data.put("signDateMonth", DateUtil.getMonth(contractExtendInfo.getBaseSignDate()));
            data.put("signDateDay", DateUtil.getDay(contractExtendInfo.getBaseSignDate()));
        } else {
            data.put("signDateYear", "");
            data.put("signDateMonth", "");
            data.put("signDateDay", "");
        }
        data.put("contractName", Strings.nullToEmpty(contractExtendInfo.getContractName()));
        data.put("receivableMoney", contractExtendInfo.getReceivableMoney() == null ? "" : contractExtendInfo.getReceivableMoney());
        data.put("receivableMoneyUpper", Strings.nullToEmpty(contractExtendInfo.getReceivableMoneyUpper()));
        data.put("receivableMoneyType", Strings.nullToEmpty(contractExtendInfo.getReceivableMoneyType()));
        data.put("receivableMoneyAdditional", Strings.nullToEmpty(contractExtendInfo.getReceivableMoneyAdditional()));
        data.put("obligorEnjoyMoney", contractExtendInfo.getReceivableAssigneeMoney() == null ? "" : contractExtendInfo.getReceivableAssigneeMoney());
        data.put("obligorEnjoyMoneyUpper", Strings.nullToEmpty(contractExtendInfo.getReceivableAssigneeMoneyUpper()));
        data.put("receivableAssigneeMoney", contractExtendInfo.getReceivableAssigneeMoney() == null ? "" : contractExtendInfo.getReceivableAssigneeMoney());
        data.put("receivableAssigneeMoneyUpper", Strings.nullToEmpty(contractExtendInfo.getReceivableAssigneeMoneyUpper()));
        data.put("interestRate", contractExtendInfo.getInterestRate() == null ? "" : contractExtendInfo.getInterestRate());
        data.put("billingStartDate", Strings.nullToEmpty(DateUtil.format(contractExtendInfo.getBillingStartDate(), DateUtil.spotFormat)));

        if (projectMode == 2) {
            data.put("receivableRecoveryMoney", contractExtendInfo.getReceivableRecoveryMoney() == null ? "" : contractExtendInfo.getReceivableRecoveryMoney());
            data.put("receivableRecoveryMoneyUpper", Strings.nullToEmpty(contractExtendInfo.getReceivableRecoveryMoneyUpper()));
            data.put("receivableRecoveryMoneyType", Strings.nullToEmpty(contractExtendInfo.getReceivableRecoveryMoneyType()));
        } else if (projectMode == 1) {
            data.put("receivableRecoveryMoney", contractExtendInfo.getReceivableMoney() == null ? "" : contractExtendInfo.getReceivableMoney());
            data.put("receivableRecoveryMoneyUpper", Strings.nullToEmpty(contractExtendInfo.getReceivableMoneyUpper()));
            data.put("receivableRecoveryMoneyType", Strings.nullToEmpty(contractExtendInfo.getReceivableMoneyType()));
        }
        data.put("receivableRecoveryMoneyPaytime", Strings.nullToEmpty(DateUtil.format(contractExtendInfo.getReceivableRecoveryMoneyPaytime(), DateUtil.spotFormat)));
        data.put("fpAccountName", Strings.nullToEmpty(contractExtendInfo.getFpAccountName()));
        data.put("fpBankName", Strings.nullToEmpty(contractExtendInfo.getFpBankName()));
        data.put("fpAccount", Strings.nullToEmpty(contractExtendInfo.getFpAccount()));
        data.put("spAccountName", Strings.nullToEmpty(contractExtendInfo.getSpAccountName()));
        data.put("spBankName", Strings.nullToEmpty(contractExtendInfo.getSpBankName()));
        data.put("spAccount", Strings.nullToEmpty(contractExtendInfo.getSpAccount()));

        if (contractExtendInfo.getCompulsoryRescissionDate() != null) {
            data.put("compulsoryRescissionDateYear", DateUtil.getYeah(contractExtendInfo.getCompulsoryRescissionDate()));
            data.put("compulsoryRescissionDateMonth", DateUtil.getMonth(contractExtendInfo.getCompulsoryRescissionDate()));
            data.put("compulsoryRescissionDateDay", DateUtil.getDay(contractExtendInfo.getCompulsoryRescissionDate()));
        } else {
            data.put("compulsoryRescissionDateYear", "");
            data.put("compulsoryRescissionDateMonth", "");
            data.put("compulsoryRescissionDateDay", "");
        }
        data.put("fpSignatureDate", Strings.nullToEmpty(DateUtil.format(contractExtendInfo.getSignDate(), DateUtil.spotFormat)));
        data.put("spSignatureDate", Strings.nullToEmpty(DateUtil.format(contractExtendInfo.getSignDate(), DateUtil.spotFormat)));
        data.put("fasaCode", Strings.nullToEmpty(fasaCode));
        return data;
    }

    /**
     * 插入签署清单
     * @param signList
     * @param contractSerialNo
     * @param projectId
     */
    private void insertSignList(List<ContractSignListDTO> signList, String contractSerialNo, Long projectId) {
        if (!CollectionUtils.isEmpty(signList)) {
            signList.forEach(e -> {
                ContractSignList contractSignList = new ContractSignList();
                contractSignList.setSerialNo(SerialNoGenerator.generateSerialNo("CSL", 5));
                contractSignList.setContractSerialNo(contractSerialNo);
                contractSignList.setSignListName(e.getSignListName());
                contractSignList.setStatus(false);
                contractSignList.setIsRequired(e.getIsRequired());
                contractSignList.setCategory(e.getCategory());
                contractSignList.setProjectId(projectId);
                contractSignList.setFilingStatus(1);
                contractSignListDao.insert(contractSignList);
            });
        }
    }

    /**
     * 插入附件信息
     * @param contractInfoDTO
     */
    private void insertAttachList(ContractInfoDTO contractInfoDTO) {
        if (!CollectionUtils.isEmpty(contractInfoDTO.getAttachList())) {
            contractInfoDTO.getAttachList().forEach(e -> {
                if (e.getAttachType() == null || e.getAttachType() == ContractTemplateEnum.USER_DEFINED.getValue()) {
                    ProjectItem projectItem = new ProjectItem();
                    projectItem.setAttachType(ContractTemplateEnum.USER_DEFINED.getValue());
                    projectItem.setProjectId(contractInfoDTO.getBaseInfo().getProjectId());
                    projectItem.setItemType(ProjectItemType.DRAWUP);
                    projectItem.setItemName(e.getAttachName());
                    projectItem.setItemValue(e.getFileId());
                    projectItemDao.insert(projectItem);
                }
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
        contractInfo.setDeleteFlag(DeleteFlagEnum.DLETED.getValue());
        contractInfoDao.update(contractInfo);
    }

    /**
     * 提交审核
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitAudit(Long id) {
        ContractInfo contractInfo = contractInfoDao.get(id);
        contractInfo.setStatus(ContractStatusEnum.APPLY.getValue());
        contractInfoDao.update(contractInfo);

        //       final LoginUser loginUser = LoginHandler.getLoginUser();
        //此处先从数据库中查询是否有记录，当驳回到拟定状态时需要做更新，不能直接插入
        //        Audit audit = auditService.getAuditByProjectFlowAndType(contractInfo.getProjectId(), AuditType.CONTRACT.getCode());
        //        if (audit == null) {
        //            audit = new Audit();
        //        }
        //TODO 查询数据库次数太多，可能影响性能
        Project project = projectService.getProject(contractInfo.getProjectId());
        project.setItems(null);
        //        audit.setApplicant(userDao.findBySerialNo(loginUser.getSerialNo()));
        //        audit.setProject(project);
        //        audit.setCreateTime(new Date());
        //        audit.setAuditType(AuditType.CONTRACT);
        //        audit.setStep(ContractStatusEnum.APPLY.getValue());
        //        //获取审核流程
        //        final FlowStatus flowStatus = flowConfigDao.findByFlowTypeAndStatus(FlowTypeEnum.CONTRACT.getValue(), contractInfo.getStatus() + 1);
        //        audit.setRole(roleInfoService.getBySerialNo(flowStatus.getRoleSerialNo()));
        //        if (audit.getId() == null) {
        //            auditDao.insert(audit);
        //        } else {
        //            auditDao.updateRole(audit);
        //        }
        //
        //        //保存审核记录
        //        AuditRecord record = new AuditRecord();
        //        record.setStatus("成功");
        //        record.setRemark("申请成功");
        //        record.setType("提出申请");
        //        record.setAudit(audit);
        //        record.setAuditor(audit.getApplicant());
        //        record.setResult(AuditResult.APPLY);
        //        record.setStatus(record.getResult().getDesc());
        //        record.setAuditTime(new Date());
        //        auditRecordDao.insert(record);

        project.setStep(Step.DRAWUP.getIndex());
        processEngine.next(project, true);
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
        final ContractInfo contractInfo = contractInfoDao.getByProjectId(contractAuditDTO.getProjectId());
        ContractStatusEnum currentStatus = ContractStatusEnum.fromValue(contractInfo.getStatus());
        //状态合法性校验
        if (currentStatus == null) {
            throw new ValidateException("合同状态非法，status = " + contractInfo.getStatus());
        }

        //保存审核记录
        AuditRecord record = new AuditRecord();

        //审核流程信息
        Audit audit = auditService.getAudit(contractAuditDTO.getAuditId());
        LoginUser loginUser = LoginHandler.getLoginUser();
        audit.setApplicant(userDao.findBySerialNo(loginUser.getSerialNo()));
        Project project = projectService.getProject(contractInfo.getProjectId());
        project.setItems(null);
        audit.setProject(project);
        audit.setCreateTime(new Date());
        audit.setAuditType(AuditType.DRAWUP);

        //审核通过
        if (contractAuditDTO.getOperationType() == 1) {
            contractInfo.setStatus(currentStatus.getNextStatus().getValue());
            //如果集团正总审核完成，则直接将合同状态更新为完成，流转到办公室人员，并修改项目状态为拟定合同
            if (currentStatus.getNextStatus() == ContractStatusEnum.GENERAL_MANAGER_AUDIT) {
                contractInfo.setStatus(7);

                //状态流转到下一级
                //                audit.setStep(ContractStatusEnum.SIGN.getValue());

                //更新项目状态，后面优化去掉 TODO
                project.setProgress(Progress.DRAWUP);
                projectService.turnover(project);

                project.setStep(Step.DRAWUP_AUDIT.getIndex());
                processEngine.next(project, false);
            } else {
                //状态流转到下一级
                audit.setStep(currentStatus.getNextStatus().getValue());
            }

            record.setType(currentStatus.getNextStatus().getDesc());
            record.setStatus(currentStatus.getNextStatus().getDesc() + "通过");
            record.setResult(AuditResult.PASS);
            //获取审核流程
            final FlowStatus flowStatus = flowConfigDao.findByFlowTypeAndStatus(FlowTypeEnum.DRAWUP.getValue(), contractInfo.getStatus());
            audit.setRole(roleInfoService.getBySerialNo(flowStatus.getRoleSerialNo()));
            auditDao.updateRole(audit);
        } else {
            //默认驳回到上一状态
            Integer rejectStatus;
            if (contractAuditDTO.getRejectStatus() != null) {
                rejectStatus = contractAuditDTO.getRejectStatus();
            } else {
                rejectStatus = currentStatus.getDefaultRejectStatus().getValue();
            }
            contractInfo.setStatus(rejectStatus);

            //如果驳回到 提出申请 状态，则直接将合同状态改为 拟定，这样业务专员才可以重新修改合同信息
            if (contractInfo.getStatus() == ContractStatusEnum.APPLY.getValue()) {
                contractInfo.setStatus(ContractStatusEnum.NEW.getValue());
            }
            audit.setStep(contractInfo.getStatus());
            record.setType(currentStatus.getNextStatus().getDesc());
            record.setStatus("驳回至" + ContractStatusEnum.fromValue(rejectStatus).getDesc());
            record.setResult(AuditResult.REJECTED);
            //获取审核流程
            final FlowStatus flowStatus = flowConfigDao.findByFlowTypeAndStatus(FlowTypeEnum.DRAWUP.getValue(), contractInfo.getStatus());
            audit.setRole(roleInfoService.getBySerialNo(flowStatus.getRoleSerialNo()));
            auditDao.updateRole(audit);
        }
        contractInfoDao.update(contractInfo);

        //保存审核记录
        record.setRemark(contractAuditDTO.getRemark());
        record.setAudit(audit);
        record.setAuditor(audit.getApplicant());
        record.setAuditTime(new Date());
        auditRecordDao.insert(record);
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
     * @param projectId
     * @return
     */
    @Override
    public List<ContractSignListDTO> findSignListByProjectId(Long projectId) {
        ContractInfo contractInfo = contractInfoDao.getByProjectId(projectId);
        return contractInfoMapper.doList2dtoListSign(contractSignListDao.findByContractSerialNo(contractInfo.getSerialNo()));
    }

    /** 
     * @see com.smile.start.service.contract.ContractInfoService#findSinListByProject(java.lang.Long)
     */
    @Override
    public List<ContractSignList> findSinListByProject(Long projectId) {
        return contractSignListDao.findByProjectId(projectId);
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
            final ContractInfo contractInfo = contractInfoDao.getByProjectId(contractSignDTO.getProjectId());
            if (contractInfo.getSealStatus() == SealStatusEnum.NOT_STAMPED.getValue()) {
                throw new ValidateException("用印未完成，不能完成签署");
            }
            //            contractInfo.setStatus(ContractStatusEnum.SIGN_FINISH.getValue());
            //            contractInfoDao.update(contractInfo);

            Project project = projectService.getProject(contractInfo.getProjectId());
            project.setItems(null);
            project.setStep(Step.SIGN.getIndex());
            processEngine.next(project, false);
        }
    }

    /** 
     * @see com.smile.start.service.contract.ContractInfoService#updateFilingStatus(java.util.List)
     */
    @Override
    @Transactional
    public BaseResult updateFilingStatus(List<ContractSignListDTO> signs) {
        for (int i = 0; i < signs.size(); i++) {
            ContractSignListDTO sign = signs.get(i);
            ContractSignList list = new ContractSignList();
            list.setSerialNo(sign.getSerialNo());
            list.setSignListName(sign.getSignListName());
            list.setCategory(sign.getCategory());
            list.setPageCount(sign.getPageCount());
            list.setIsOriginalCopy(sign.getIsOriginalCopy());
            list.setRemark(sign.getRemark());
            list.setCopies(sign.getCopies());

            list.setSort(i);
            if (sign.getGetReady() != null && sign.getGetReady()) {
                list.setFilingStatus(2);
            }
            long effect;
            if (Strings.isNullOrEmpty(sign.getSerialNo())) {
                list.setSerialNo(SerialNoGenerator.generateSerialNo("CSL", 5));
                list.setProjectId(sign.getProjectId());
                list.setStatus(sign.getStatus());
                list.setIsRequired(sign.getIsRequired());
                list.setSort(i);
                effect = contractSignListDao.insert(list);
            } else {
                effect = contractSignListDao.updateFilingStatus(list);
            }
            if (effect <= 0) {
                throw new RuntimeException("保存签署附件信息异常");
            }
        }
        return new BaseResult();
    }

    /**
     * 获取合同附件列表
     * @param projectId
     * @return
     */
    @Override
    public List<ContractAttachDTO> getAttachList(Long projectId) {
        final List<ProjectItem> attachs = projectItemDao.getTypeItems(projectId, ProjectItemType.DRAWUP);
        if (!CollectionUtils.isEmpty(attachs)) {
            List<ContractAttachDTO> attachList = Lists.newArrayList();
            attachs.forEach(e -> {
                ContractAttachDTO attachDTO = new ContractAttachDTO();
                attachDTO.setAttachType(e.getAttachType());
                attachDTO.setAttachName(e.getItemName());
                attachDTO.setFileId(e.getItemValue());
                attachList.add(attachDTO);
            });
            return attachList;
        }
        return null;
    }

    /**
     * 上传移交清单
     * @param projectId
     * @throws FileNotFoundException
     */
    @Override
    public void uploadTransferList(Long projectId) throws IOException {
        List<ContractSignList> transferList = contractSignListDao.findByProjectId(projectId);
        Project project = projectService.getProject(projectId);
        FactoringDetail factoringDetail = factoringService.get(projectId);
        List<ContractSignList> collect = transferList.stream().sorted(Comparator.comparing(ContractSignList::getCategory)).collect(Collectors.toList());

        String fileName = "移交清单" + project.getProjectId() + ".docx";
        File transferFile = new File(fileName);
        logger.info("transferFilePath : {}", transferFile.getAbsolutePath());

        //Blank Document
        @SuppressWarnings("resource")
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(transferFile); // 下载路径/文件名称

        //添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("苏州市相城融金商业保理有限公司 移交清册");
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(16);

        //段落
        XWPFParagraph firstParagraph = document.createParagraph();
        //设置段落居中
        firstParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = firstParagraph.createRun();
        run.setText(buildSubtitle(project, factoringDetail));
        run.setColor("000000");
        run.setFontSize(16);

        //移交清单表格
        XWPFTable comTable = document.createTable();
        buildFirstRow(comTable);

        int category = 0;
        for (int i = 0; i < collect.size(); i++) {
            ContractSignList signList = collect.get(i);
            XWPFTableRow comTableRowTwo = comTable.createRow();
            XWPFTableCell cell0 = comTableRowTwo.getCell(0);
            cell0.setText(String.valueOf(i + 1));
            setVerticalAlignment(cell0);
            setHorizontalAlignment(cell0);
            XWPFTableCell cell1 = comTableRowTwo.getCell(1);
            cell1.setText(SignListCategoryEnum.fromValue(signList.getCategory()).getDesc());
            if (category != signList.getCategory()) {
                category = signList.getCategory();
                cell1.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                cell1.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
            comTableRowTwo.getCell(2).setText(signList.getSignListName());
            //原件
            if (signList.getIsOriginalCopy() == 1) {
                comTableRowTwo.getCell(3).setText(String.format("%d份/各%d页", signList.getCopies(), signList.getPageCount()));
                comTableRowTwo.getCell(4).setText("--");
            } else {
                comTableRowTwo.getCell(3).setText("--");
                comTableRowTwo.getCell(4).setText(String.format("%d份/各%d页", signList.getCopies(), signList.getPageCount()));
            }
            comTableRowTwo.getCell(5).setText(signList.getRemark());
        }

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setText("\r");

        //添加签字栏
        XWPFParagraph transferDateParagraph = document.createParagraph();
        transferDateParagraph.setAlignment(ParagraphAlignment.LEFT);
        transferDateParagraph.setIndentationLeft(567 * 10);

        XWPFRun transferDateParagraphRun = transferDateParagraph.createRun();
        transferDateParagraphRun.setText("移交日期：" + DateUtil.format(new Date(), DateUtil.spotFormat));
        transferDateParagraphRun.setColor("000000");
        transferDateParagraphRun.setFontSize(12);
        transferDateParagraphRun.addBreak();

        XWPFRun transferUserParagraphRun = transferDateParagraph.createRun();
        transferUserParagraphRun.setText("移交人：" + LoginHandler.getLoginUser().getUsername());
        transferUserParagraphRun.setColor("000000");
        transferUserParagraphRun.setFontSize(12);
        transferUserParagraphRun.addBreak();

        XWPFRun transferReviewerParagraphRun = transferDateParagraph.createRun();
        transferReviewerParagraphRun.setText("复核人：");
        transferReviewerParagraphRun.setColor("000000");
        transferReviewerParagraphRun.setFontSize(12);
        transferReviewerParagraphRun.addBreak();

        XWPFRun transferReceiveParagraphRun = transferDateParagraph.createRun();
        transferReceiveParagraphRun.setText("接收人：");
        transferReceiveParagraphRun.setColor("000000");
        transferReceiveParagraphRun.setFontSize(12);
        transferReceiveParagraphRun.addBreak();

        XWPFRun transferRiskParagraphRun = transferDateParagraph.createRun();
        transferRiskParagraphRun.setText("风控：");
        transferRiskParagraphRun.setColor("000000");
        transferRiskParagraphRun.setFontSize(12);

        document.write(out);
        out.close();
        upload(transferFile, fileName, projectId, ProjectItemType.FILE);
        //        transferFile.delete();
    }

    /**
     * 构建第一行
     * @param comTable
     */
    private void buildFirstRow(XWPFTable comTable) {
        //列宽自动分割
        CTTblWidth comTableWidth = comTable.getCTTbl().addNewTblPr().addNewTblW();
        comTableWidth.setType(STTblWidth.DXA);
        comTableWidth.setW(BigInteger.valueOf(9072));

        //表格第一行
        XWPFTableRow comTableRowOne = comTable.getRow(0);
        XWPFTableCell serialNumberCell = comTableRowOne.getCell(0);
        setVerticalAlignment(serialNumberCell);
        setHorizontalAlignment(serialNumberCell);
        serialNumberCell.setText("序号");

        XWPFTableCell categoryCell = comTableRowOne.addNewTableCell();
        setVerticalAlignment(categoryCell);
        setHorizontalAlignment(categoryCell);
        categoryCell.setText("文件分类");

        //        cell00.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        XWPFTableCell fileNameCell = comTableRowOne.addNewTableCell();
        setVerticalAlignment(fileNameCell);
        setHorizontalAlignment(fileNameCell);
        fileNameCell.setText("文件名称");

        //        cell01.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        XWPFTableCell pageCountCell_1 = comTableRowOne.addNewTableCell();
        setVerticalAlignment(pageCountCell_1);
        setHorizontalAlignment(pageCountCell_1);
        //内容换行处理
        XWPFParagraph para1 = pageCountCell_1.getParagraphs().get(0);
        XWPFRun run11 = para1.createRun();
        run11.setText("件/页数");
        run11.addBreak();
        XWPFRun run12 = para1.createRun();
        run12.setText("（原件）");

        //        cell02.setText("件/页数\r\n（原件）");
        XWPFTableCell pageCountCell_2 = comTableRowOne.addNewTableCell();
        setVerticalAlignment(pageCountCell_2);
        setHorizontalAlignment(pageCountCell_2);
        //内容换行处理
        XWPFParagraph para2 = pageCountCell_2.getParagraphs().get(0);
        XWPFRun run21 = para2.createRun();
        run21.setText("件/页数");
        run21.addBreak();
        XWPFRun run22 = para2.createRun();
        run22.setText("（复印件）");

        XWPFTableCell remarkCell = comTableRowOne.addNewTableCell();
        setVerticalAlignment(remarkCell);
        setHorizontalAlignment(remarkCell);
        remarkCell.setText("备注");
    }

    /**
     * 构建副标题
     * @param project
     * @param factoringDetail
     * @return
     */
    private String buildSubtitle(Project project, FactoringDetail factoringDetail) {
        DecimalFormat df = new DecimalFormat("#");
        return String.format("%s：%s%S万", project.getProjectId(), factoringDetail.getCreditor(), df.format(factoringDetail.getAssignee()));
    }

    /**
     * 垂直对齐
     * @param cell
     */
    private void setVerticalAlignment(XWPFTableCell cell) {
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
    }

    /**
     * 水平对齐
     * @param cell
     */
    private void setHorizontalAlignment(XWPFTableCell cell) {
        CTTc ctTc = cell.getCTTc();
        ctTc.addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
        ctTc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
    }

}
