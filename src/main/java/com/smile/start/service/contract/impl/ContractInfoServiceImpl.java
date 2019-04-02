package com.smile.start.service.contract.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.smile.start.model.enums.AuditResult;
import com.smile.start.model.enums.AuditType;
import com.smile.start.model.enums.ContractStatusEnum;
import com.smile.start.model.enums.ContractTemplateEnum;
import com.smile.start.model.enums.DeleteFlagEnum;
import com.smile.start.model.enums.FlowTypeEnum;
import com.smile.start.model.enums.Progress;
import com.smile.start.model.enums.ProjectItemType;
import com.smile.start.model.enums.SealStatusEnum;
import com.smile.start.model.enums.Step;
import com.smile.start.model.login.LoginUser;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.auth.RoleInfoService;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.common.FileService;
import com.smile.start.service.contract.ContractInfoService;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.project.ProjectService;

import freemarker.template.TemplateException;

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
        final List<ProjectItem> attachs = projectItemDao.getTypeItems(contractInfo.getProjectId(), ProjectItemType.DRAWUP);
        if (!CollectionUtils.isEmpty(attachs)) {
            List<ContractAttachDTO> attachList = Lists.newArrayList();
            attachs.forEach(e -> {
                ContractAttachDTO attachDTO = new ContractAttachDTO();
                attachDTO.setContractSerialNo(contractInfo.getSerialNo());
                attachDTO.setAttachType(e.getAttachType());
                attachDTO.setAttachName(e.getItemName());
                attachDTO.setFileId(e.getItemValue());
                attachList.add(attachDTO);
            });
            contractInfoDTO.setAttachList(attachList);
        }

        final Project project = projectService.getProject(contractInfo.getProjectId());
        contractInfoDTO.setProject(project);
        return contractInfoDTO;
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
        insertSignList(contractInfoDTO.getSignList(), contractSerialNo);

        //标准模板
        if (contractInfo.getContractTemplate() == ContractTemplateEnum.STANDARD.getValue()) {
            //保存合同信息
            ContractExtendInfoDTO contractExtendInfoDTO = contractInfoDTO.getContractExtendInfo();
            contractExtendInfoDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CEI", 5));
            contractExtendInfoDTO.setContractSerialNo(contractSerialNo);
            contractExtendInfoDTO.setContractCode(project.getProjectId() + "-1");
            ContractExtendInfo contractExtendInfo = contractInfoMapper.dto2do(contractExtendInfoDTO);
            contractExtendInfoDao.insert(contractExtendInfo);

            //保存应收账款转让确认函
            ContractReceivableConfirmationDTO contractReceivableConfirmationDTO = contractInfoDTO.getContractReceivableConfirmation();
            contractReceivableConfirmationDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CRC", 5));
            contractReceivableConfirmationDTO.setContractSerialNo(contractSerialNo);
            contractReceivableConfirmationDTO.setConfirmationCode(project.getProjectId() + "-2");
            ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractReceivableConfirmationDTO);
            contractReceivableConfirmationDao.insert(contractReceivableConfirmation);

            //保存应收账款转让登记协议
            ContractReceivableAgreementDTO contractReceivableAgreementDTO = contractInfoDTO.getContractReceivableAgreement();
            contractReceivableAgreementDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CRA", 5));
            contractReceivableAgreementDTO.setContractSerialNo(contractSerialNo);
            contractReceivableAgreementDTO.setProtocolCode(project.getProjectId() + "-3");
            ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractReceivableAgreementDTO);
            contractReceivableAgreementDao.insert(contractReceivableAgreement);

            //保存财务顾问协议，无追合同才有
            if (contractInfoDTO.getBaseInfo().getProjectMode() == 2) {
                ContractFasaDTO contractFasaDTO = contractInfoDTO.getContractFasa();
                contractFasaDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CF", 5));
                contractFasaDTO.setContractSerialNo(contractSerialNo);
                contractFasaDTO.setFasaCode(project.getProjectId() + "-4");
                ContractFasa contractFasa = contractInfoMapper.dto2do(contractFasaDTO);
                contractFasaDao.insert(contractFasa);

            }

            //保存股东会决议
            ContractShareholderMeetingDTO contractShareholderMeetingDTO = contractInfoDTO.getContractShareholderMeeting();
            contractShareholderMeetingDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CSM", 5));
            contractShareholderMeetingDTO.setContractSerialNo(contractSerialNo);
            ContractShareholderMeeting contractShareholderMeeting = contractInfoMapper.dto2do(contractShareholderMeetingDTO);
            contractShareholderMeetingDao.insert(contractShareholderMeeting);

            //标准合同
            uploadStandardTemplate(contractInfoDTO, project);
        }

        //自定义附件合同
        insertAttachList(contractInfoDTO);
        return contractInfoDao.insert(contractInfo);
    }

    private void uploadStandardTemplate(ContractInfoDTO contractInfoDTO, Project project) throws IOException, TemplateException {
        ContractExtendInfoDTO contractExtendInfo = contractInfoDTO.getContractExtendInfo();
        Integer projectMode = contractInfoDTO.getBaseInfo().getProjectMode();
        String contractFileName;
        if (projectMode == 1) {
            contractFileName = "保理合同（有追索权）" + contractExtendInfo.getContractCode();
        } else {
            contractFileName = "保理合同（无追索权）" + contractExtendInfo.getContractCode();
        }
        File contractFile = DocUtil.createDoc(contractFileName, "factoringContract_" + projectMode + ".xml", buildTemplateData(contractExtendInfo, project.getProjectId() + "-4"));
        upload(contractFile, contractFileName, contractInfoDTO.getBaseInfo().getProjectId());

        ContractReceivableConfirmationDTO contractReceivableConfirmation = contractInfoDTO.getContractReceivableConfirmation();
        String confirmationFileName = "附件1：应收账款转让确认函" + contractReceivableConfirmation.getConfirmationCode();
        File confirmationFile = DocUtil.createDoc(confirmationFileName, "confirmationLetter_" + projectMode + ".xml",
            buildTemplateData(contractReceivableConfirmation, contractExtendInfo));
        upload(confirmationFile, confirmationFileName, contractInfoDTO.getBaseInfo().getProjectId());

        ContractReceivableAgreementDTO contractReceivableAgreement = contractInfoDTO.getContractReceivableAgreement();
        String agreementFileName = "附件2：应收账款转让登记协议" + contractReceivableAgreement.getProtocolCode();
        File agreementFile = DocUtil.createDoc(agreementFileName, "registrationAgreement_" + projectMode + ".xml",
            buildTemplateData(contractReceivableAgreement, contractExtendInfo));
        upload(agreementFile, agreementFileName, contractInfoDTO.getBaseInfo().getProjectId());

        ContractFasaDTO contractFasa = contractInfoDTO.getContractFasa();
        String fasaFileName = "财务顾问服务协议" + contractFasa.getFasaCode();
        File fasaFile = DocUtil.createDoc(fasaFileName, "financialAgreement_" + projectMode + ".xml", buildTemplateData(contractFasa, contractExtendInfo));
        upload(fasaFile, fasaFileName, contractInfoDTO.getBaseInfo().getProjectId());

        String shareholderFileName = "股东会决议";
        File shareholderFile = DocUtil.createDoc(shareholderFileName, "shareholderResolution_" + projectMode + ".xml",
            buildTemplateData(contractInfoDTO.getContractShareholderMeeting(), contractExtendInfo));
        upload(shareholderFile, shareholderFileName, contractInfoDTO.getBaseInfo().getProjectId());
    }

    /**
     * 标准合同文件上传并保存数据库
     * @param file
     * @param fileName
     */
    private void upload(File file, String fileName, Long projectId) {
        if (file.exists()) {
            final FileInfo upload = fileService.upload(file, fileName);
            ProjectItem projectItem = new ProjectItem();
            projectItem.setAttachType(ContractTemplateEnum.STANDARD.getValue());
            projectItem.setProjectId(projectId);
            projectItem.setItemType(ProjectItemType.DRAWUP);
            projectItem.setItemName(fileName);
            projectItem.setItemValue(upload.getFileId());
            projectItemDao.insert(projectItem);
        }
    }

    /**
     * 构建登记协议模板数据
     * @param contractReceivableAgreement
     * @param contractExtendInfo
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractReceivableAgreementDTO contractReceivableAgreement, ContractExtendInfoDTO contractExtendInfo) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("protocolCode", Strings.nullToEmpty(contractReceivableAgreement.getProtocolCode()));
        data.put("spName", Strings.nullToEmpty(contractReceivableAgreement.getSpName()));
        data.put("spResidence", Strings.nullToEmpty(contractReceivableAgreement.getSpResidence()));
        data.put("spLegalPerson", Strings.nullToEmpty(contractReceivableAgreement.getSpLegalPerson()));
        data.put("spContactAddress", Strings.nullToEmpty(contractReceivableAgreement.getSpContactAddress()));
        data.put("spPostCode", Strings.nullToEmpty(contractReceivableAgreement.getSpPostCode()));
        data.put("spTelephone", Strings.nullToEmpty(contractReceivableAgreement.getSpTelephone()));
        data.put("spFax", Strings.nullToEmpty(contractReceivableAgreement.getSpFax()));
        data.put("signDate", DateUtil.format(contractReceivableAgreement.getSignDate(), DateUtil.chineseDtFormat));
        data.put("fpSignatureDate", DateUtil.format(contractReceivableAgreement.getFpSignatureDate(), DateUtil.spotFormat));
        data.put("spSignatureDate", DateUtil.format(contractReceivableAgreement.getSpSignatureDate(), DateUtil.spotFormat));
        data.put("contractCode", Strings.nullToEmpty(contractExtendInfo.getContractCode()));
        data.put("contractSignDateYear", DateUtil.getYeah(contractExtendInfo.getSignDate()));
        data.put("contractSignDateMonth", DateUtil.getMonth(contractExtendInfo.getSignDate()));
        data.put("contractSignDateDay", DateUtil.getDay(contractExtendInfo.getSignDate()));
        return data;
    }

    /**
     * 构建股东会决议模板数据
     * @param contractShareholderMeeting
     * @param contractExtendInfo
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractShareholderMeetingDTO contractShareholderMeeting, ContractExtendInfoDTO contractExtendInfo) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractCode", Strings.nullToEmpty(contractExtendInfo.getContractCode()));
        data.put("meetingTimeYear", DateUtil.getYeah(contractShareholderMeeting.getMeetingTime()));
        data.put("meetingTimeMonth", DateUtil.getMonth(contractShareholderMeeting.getMeetingTime()));
        data.put("meetingTimeDay", DateUtil.getDay(contractShareholderMeeting.getMeetingTime()));
        data.put("meetingAddress", Strings.nullToEmpty(contractShareholderMeeting.getMeetingAddress()));
        data.put("spCompanyName", Strings.nullToEmpty(contractShareholderMeeting.getSpCompanyName()));
        data.put("attendingShareholders", Strings.nullToEmpty(contractShareholderMeeting.getAttendingShareholders()));
        data.put("meetingNumber", contractShareholderMeeting.getMeetingNumber());
        data.put("passingRate", Strings.nullToEmpty(contractShareholderMeeting.getPassingRate()));
        data.put("signatureDateYear", DateUtil.getYeah(contractShareholderMeeting.getSignatureDate()));
        data.put("signatureDateMonth", DateUtil.getMonth(contractShareholderMeeting.getSignatureDate()));
        data.put("signatureDateDay", DateUtil.getDay(contractShareholderMeeting.getSignatureDate()));
        return data;
    }

    /**
     * 构建财务顾问服务协议模板数据
     * @param contractFasa
     * @param contractExtendInfo
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractFasaDTO contractFasa, ContractExtendInfoDTO contractExtendInfo) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractCode", Strings.nullToEmpty(contractExtendInfo.getContractCode()));
        data.put("fasaCode", Strings.nullToEmpty(contractFasa.getFasaCode()));
        data.put("fpCompanyName", Strings.nullToEmpty(contractFasa.getFpCompanyName()));
        data.put("fpResidence", Strings.nullToEmpty(contractFasa.getFpResidence()));
        data.put("fpLegalPerson", Strings.nullToEmpty(contractFasa.getFpLegalPerson()));
        data.put("fpPostCode", Strings.nullToEmpty(contractFasa.getFpPostCode()));
        data.put("fpTelephone", Strings.nullToEmpty(contractFasa.getFpTelephone()));
        data.put("fpFax", Strings.nullToEmpty(contractFasa.getFpFax()));
        data.put("signAddress", Strings.nullToEmpty(contractFasa.getSignAddress()));
        data.put("signDateYear", DateUtil.getYeah(contractFasa.getSingDate()));
        data.put("signDateMonth", DateUtil.getMonth(contractFasa.getSingDate()));
        data.put("signDateDay", DateUtil.getDay(contractFasa.getSingDate()));

        data.put("advisoryServiceMoney", contractFasa.getAdvisoryServiceMoney());
        data.put("advisoryServiceMoneyUpper", contractFasa.getAdvisoryServiceMoneyUpper());
        data.put("advisoryServiceMoneyAppointment", Strings.nullToEmpty(contractFasa.getAdvisoryServiceMoneyAppointment()));
        data.put("spBankName", Strings.nullToEmpty(contractFasa.getSpBankName()));
        data.put("spAccount", Strings.nullToEmpty(contractFasa.getSpAccount()));
        data.put("expiryDateMonth", contractFasa.getExpiryDateMonth());
        data.put("fpSignatureDate", DateUtil.format(contractFasa.getFpSignatureDate(), DateUtil.spotFormat));
        data.put("spSignatureDate", DateUtil.format(contractFasa.getSpSignatureDate(), DateUtil.spotFormat));
        return data;
    }

    /**
     * 构建确认函模板数据
     * @param contractReceivableConfirmation
     * @param contractExtendInfo
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractReceivableConfirmationDTO contractReceivableConfirmation, ContractExtendInfoDTO contractExtendInfo) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractCode", Strings.nullToEmpty(contractExtendInfo.getContractCode()));
        data.put("confirmationCode", Strings.nullToEmpty(contractReceivableConfirmation.getConfirmationCode()));
        data.put("signDate", DateUtil.format(contractReceivableConfirmation.getSignDate(), DateUtil.chineseDtFormat));
        data.put("assignor", Strings.nullToEmpty(contractReceivableConfirmation.getAssignor()));
        data.put("signDateYear", DateUtil.getYeah(contractReceivableConfirmation.getSignDate()));
        data.put("signDateMonth", DateUtil.getMonth(contractReceivableConfirmation.getSignDate()));
        data.put("signDateDay", DateUtil.getDay(contractReceivableConfirmation.getSignDate()));

        data.put("obligor", Strings.nullToEmpty(contractReceivableConfirmation.getObligor()));
        data.put("businessContractName", Strings.nullToEmpty(contractReceivableConfirmation.getBusinessContractName()));
        data.put("receivableAssigneeMoneyUpper", contractReceivableConfirmation.getReceivableAssigneeMoneyUpper());
        data.put("receivableAssigneeMoneyType", contractReceivableConfirmation.getReceivableAssigneeMoneyType());
        data.put("unpaidReceivableAssigneeMoney", contractReceivableConfirmation.getUnpaidReceivableAssigneeMoney());
        data.put("unpaidReceivableAssigneeMoneyUpper", contractReceivableConfirmation.getUnpaidReceivableAssigneeMoneyUpper());
        data.put("unpaidReceivableAssigneeMoneyType", contractReceivableConfirmation.getUnpaidReceivableAssigneeMoneyType());
        data.put("receivableExpiryDate", DateUtil.format(contractReceivableConfirmation.getReceivableExpiryDate(), DateUtil.chineseDtFormat));

        data.put("receivableRecoveryMoney", contractReceivableConfirmation.getReceivableRecoveryMoney());
        // TODO
        data.put("receivableRecoveryMoneyUpper", "");
//        data.put("receivableRecoveryMoneyUpper", contractReceivableConfirmation.getReceivableRecoveryMoneyUpper());
        data.put("contractReceivable", contractReceivableConfirmation.getContractReceivable());
        data.put("contractReceivableUpper", contractReceivableConfirmation.getContractReceivableUpper());
        data.put("assignorAbligorReceivable", contractReceivableConfirmation.getAssignorAbligorReceivable());
        data.put("assignorAbligorReceivableUpper", contractReceivableConfirmation.getAssignorAbligorReceivableUpper());
        data.put("unpaidAssignorAbligorReceivable", contractReceivableConfirmation.getUnpaidAssignorAbligorReceivable());
        data.put("unpaidAssignorAbligorReceivableUpper", contractReceivableConfirmation.getUnpaidAssignorAbligorReceivableUpper());
        data.put("receivableAssigneeMoneyPaid", contractReceivableConfirmation.getReceivableAssigneeMoneyPaid());
        data.put("receivableAssigneeMoneyPaidUpper", contractReceivableConfirmation.getReceivableAssigneeMoneyPaidUpper());

        data.put("assignorCommitDate", DateUtil.format(contractReceivableConfirmation.getAssignorCommitDate(), DateUtil.spotFormat));
        data.put("assigneeAccountName", Strings.nullToEmpty(contractReceivableConfirmation.getAssigneeAccountName()));
        data.put("assigneeBankName", Strings.nullToEmpty(contractReceivableConfirmation.getAssigneeBankName()));
        data.put("assigneeAccount", Strings.nullToEmpty(contractReceivableConfirmation.getAssigneeAccount()));

        data.put("assigneeSignatureDate", DateUtil.format(contractReceivableConfirmation.getAssigneeSignatureDate(), DateUtil.spotFormat));
        data.put("assignorCompanyName", Strings.nullToEmpty(contractReceivableConfirmation.getAssignorCompanyName()));
        data.put("assignorSignatureDate", DateUtil.format(contractReceivableConfirmation.getAssignorSignatureDate(), DateUtil.spotFormat));
        data.put("obligorCompanyName", Strings.nullToEmpty(contractReceivableConfirmation.getObligorCompanyName()));
        data.put("obligorSignatureDate", DateUtil.format(contractReceivableConfirmation.getObligorSignatureDate(), DateUtil.spotFormat));
        data.put("nameOfSubject", Strings.nullToEmpty(contractReceivableConfirmation.getNameOfSubject()));
        data.put("invoiceMoney", contractReceivableConfirmation.getInvoiceMoney());
        //TODO
        data.put("invoiceMoneyUpper", "");
        //data.put("invoiceMoneyUpper", MoneyToChineseUtil.convert(contractReceivableConfirmation.getInvoiceMoney().toString()));
        data.put("invoiceMoneyType", contractReceivableConfirmation.getInvoiceMoneyType());
        return data;
    }

    /**
     * 构建保理合同模板数据
     * @param contractExtendInfo
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractExtendInfoDTO contractExtendInfo, String fasaCode) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractCode", Strings.nullToEmpty(contractExtendInfo.getContractCode()));
        data.put("signDate", DateUtil.format(contractExtendInfo.getSignDate(), DateUtil.chineseDtFormat));
        data.put("spCompanyName", Strings.nullToEmpty(contractExtendInfo.getSpCompanyName()));
        data.put("spResidence", Strings.nullToEmpty(contractExtendInfo.getSpResidence()));
        data.put("spLegalPerson", Strings.nullToEmpty(contractExtendInfo.getSpLegalPerson()));
        data.put("spContactAddress", Strings.nullToEmpty(contractExtendInfo.getSpContactAddress()));
        data.put("spPostCode", Strings.nullToEmpty(contractExtendInfo.getSpPostCode()));
        data.put("spTelephone", Strings.nullToEmpty(contractExtendInfo.getSpTelephone()));
        data.put("spFax", Strings.nullToEmpty(contractExtendInfo.getSpFax()));
        data.put("obligor", Strings.nullToEmpty(contractExtendInfo.getObligor()));
        data.put("signDateYear", DateUtil.getYeah(contractExtendInfo.getSignDate()));
        data.put("signDateMonth", DateUtil.getMonth(contractExtendInfo.getSignDate()));
        data.put("signDateDay", DateUtil.getDay(contractExtendInfo.getSignDate()));

        data.put("contractName", Strings.nullToEmpty(contractExtendInfo.getContractName()));
        data.put("receivableMoney", contractExtendInfo.getReceivableMoney());
        data.put("receivableMoneyUpper", contractExtendInfo.getReceivableMoneyUpper());
        data.put("receivableMone", contractExtendInfo.getReceivableMoneyType());
        data.put("receivableMoneyAdditional", contractExtendInfo.getReceivableMoneyAdditional());
        data.put("obligorEnjoyMoney", contractExtendInfo.getObligorEnjoyMoney());
        data.put("obligorEnjoyMoneyUpper", contractExtendInfo.getObligorEnjoyMoneyUpper());
        data.put("receivableAssigneeMoney", contractExtendInfo.getReceivableAssigneeMoney());
        data.put("receivableAssigneeMoneyUpper", contractExtendInfo.getReceivableAssigneeMoneyUpper());
        data.put("receivableAssigneeFirstMoney", contractExtendInfo.getReceivableAssigneeFirstMoney());
        data.put("receivableAssigneeFirstMoneyUpper", contractExtendInfo.getReceivableAssigneeMoneyUpper());
        data.put("interestRate", contractExtendInfo.getInterestRate());
        data.put("billingStartDate", DateUtil.format(contractExtendInfo.getBillingStartDate(), DateUtil.spotFormat));

        data.put("receivableRecoveryMoney", contractExtendInfo.getReceivableRecoveryMoney());
        data.put("receivableRecoveryMoneyUpper", contractExtendInfo.getReceivableRecoveryMoneyUpper());
        data.put("receivableRecoveryMoneyType", contractExtendInfo.getReceivableRecoveryMoneyType());
        data.put("receivableRecoveryMoneyPaytime", DateUtil.format(contractExtendInfo.getReceivableRecoveryMoneyPaytime(), DateUtil.spotFormat));
        data.put("fpAccountName", Strings.nullToEmpty(contractExtendInfo.getFpAccountName()));
        data.put("fpBankName", Strings.nullToEmpty(contractExtendInfo.getFpBankName()));
        data.put("fpAccount", Strings.nullToEmpty(contractExtendInfo.getFpAccount()));
        data.put("spAccountName", Strings.nullToEmpty(contractExtendInfo.getSpAccountName()));
        data.put("spBankName", Strings.nullToEmpty(contractExtendInfo.getSpBankName()));
        data.put("spAccount", Strings.nullToEmpty(contractExtendInfo.getSpAccount()));

        data.put("compulsoryRescissionDateYear", DateUtil.getYeah(contractExtendInfo.getCompulsoryRescissionDate()));
        data.put("compulsoryRescissionDateMonth", DateUtil.getMonth(contractExtendInfo.getCompulsoryRescissionDate()));
        data.put("compulsoryRescissionDateDay", DateUtil.getDay(contractExtendInfo.getCompulsoryRescissionDate()));
        data.put("fpSignatureDate", DateUtil.format(contractExtendInfo.getFpSignatureDate(), DateUtil.spotFormat));
        data.put("spSignatureDate", DateUtil.format(contractExtendInfo.getSpSignatureDate(), DateUtil.spotFormat));
        data.put("fasaCode", Strings.nullToEmpty(fasaCode));
        return data;
    }

    /**
     * 修改合同基本信息
     * @param contractInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ContractInfoDTO contractInfoDTO) throws Exception {
        final ContractInfo contractInfo = contractInfoMapper.dto2do(contractInfoDTO.getBaseInfo());
        contractInfo.setGmtModify(new Date());
        LoginUser loginUser = LoginHandler.getLoginUser();
        contractInfo.setModifyUser(loginUser.getSerialNo());
        contractInfoDao.update(contractInfo);

        final Project project = projectService.getProject(contractInfoDTO.getBaseInfo().getProjectId());

        if (contractInfo.getContractTemplate() == ContractTemplateEnum.STANDARD.getValue()) {
            //更新合同信息

            ContractExtendInfoDTO contractExtendInfoDTO = contractInfoDTO.getContractExtendInfo();
            if (Strings.isNullOrEmpty(contractExtendInfoDTO.getSerialNo())) {
                contractExtendInfoDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CEI", 5));
                contractExtendInfoDTO.setContractSerialNo(contractInfo.getSerialNo());
                contractExtendInfoDTO.setContractCode(project.getProjectId() + "-1");
                ContractExtendInfo contractExtendInfo = contractInfoMapper.dto2do(contractExtendInfoDTO);
                contractExtendInfoDao.insert(contractExtendInfo);
            } else {
                ContractExtendInfo contractExtendInfo = contractInfoMapper.dto2do(contractExtendInfoDTO);
                contractExtendInfoDao.update(contractExtendInfo);
            }

            //更新应收账款转让确认函
            ContractReceivableConfirmationDTO contractReceivableConfirmationDTO = contractInfoDTO.getContractReceivableConfirmation();
            if (Strings.isNullOrEmpty(contractReceivableConfirmationDTO.getSerialNo())) {
                contractReceivableConfirmationDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CRC", 5));
                contractReceivableConfirmationDTO.setContractSerialNo(contractInfo.getSerialNo());
                contractReceivableConfirmationDTO.setConfirmationCode(project.getProjectId() + "-2");
                ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractReceivableConfirmationDTO);
                contractReceivableConfirmationDao.insert(contractReceivableConfirmation);
            } else {
                ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractReceivableConfirmationDTO);
                contractReceivableConfirmationDao.update(contractReceivableConfirmation);
            }

            //更新应收账款转让登记协议
            ContractReceivableAgreementDTO contractReceivableAgreementDTO = contractInfoDTO.getContractReceivableAgreement();
            if (Strings.isNullOrEmpty(contractReceivableAgreementDTO.getSerialNo())) {
                contractReceivableAgreementDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CRA", 5));
                contractReceivableAgreementDTO.setContractSerialNo(contractInfo.getSerialNo());
                contractReceivableAgreementDTO.setProtocolCode(project.getProjectId() + "-3");
                ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractReceivableAgreementDTO);
                contractReceivableAgreementDao.insert(contractReceivableAgreement);
            } else {
                ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractReceivableAgreementDTO);
                contractReceivableAgreementDao.update(contractReceivableAgreement);
            }

            //保存财务顾问协议，无追合同才有
            if (contractInfoDTO.getBaseInfo().getProjectMode() == 2) {
                ContractFasaDTO contractFasaDTO = contractInfoDTO.getContractFasa();
                if (Strings.isNullOrEmpty(contractFasaDTO.getSerialNo())) {
                    contractFasaDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CF", 5));
                    contractFasaDTO.setContractSerialNo(contractInfo.getSerialNo());
                    contractFasaDTO.setFasaCode(project.getProjectId() + "-4");
                    ContractFasa contractFasa = contractInfoMapper.dto2do(contractFasaDTO);
                    contractFasaDao.insert(contractFasa);
                } else {
                    ContractFasa contractFasa = contractInfoMapper.dto2do(contractFasaDTO);
                    contractFasaDao.update(contractFasa);
                }
            }

            //保存股东会决议
            ContractShareholderMeetingDTO contractShareholderMeetingDTO = contractInfoDTO.getContractShareholderMeeting();
            if (Strings.isNullOrEmpty(contractShareholderMeetingDTO.getSerialNo())) {
                contractShareholderMeetingDTO.setSerialNo(SerialNoGenerator.generateSerialNo("CSM", 5));
                contractShareholderMeetingDTO.setContractSerialNo(contractInfo.getSerialNo());
                ContractShareholderMeeting contractShareholderMeeting = contractInfoMapper.dto2do(contractShareholderMeetingDTO);
                contractShareholderMeetingDao.insert(contractShareholderMeeting);
            } else {
                ContractShareholderMeeting contractShareholderMeeting = contractInfoMapper.dto2do(contractShareholderMeetingDTO);
                contractShareholderMeetingDao.update(contractShareholderMeeting);
            }
        }

        //更新签署清单
        contractSignListDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        insertSignList(contractInfoDTO.getSignList(), contractInfo.getSerialNo());

        //更新附件信息，先批量删除再插入
        List<ProjectItem> typeItems = projectItemDao.getTypeItems(contractInfo.getProjectId(), ProjectItemType.DRAWUP);
        if (!CollectionUtils.isEmpty(typeItems)) {
            typeItems.forEach(e -> projectItemDao.delete(e));
        }
        insertAttachList(contractInfoDTO);

        //生成标准合同文件
        uploadStandardTemplate(contractInfoDTO, project);
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
        for (ContractSignListDTO sign : signs) {
            ContractSignList list = new ContractSignList();
            list.setSerialNo(sign.getSerialNo());
            list.setFilingStatus(sign.getFilingStatus());
            int effct = contractSignListDao.updateFilingStatus(list);
            if (effct <= 0) {
                throw new RuntimeException("更新签署附件归档状态异常");
            }
        }
        return new BaseResult();
    }

}
