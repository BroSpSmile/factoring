package com.smile.start.service.contract.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.smile.start.commons.DateUtil;
import com.smile.start.commons.DocUtil;
import com.smile.start.dao.*;
import com.smile.start.dto.*;
import com.smile.start.model.common.FileInfo;
import com.smile.start.model.common.FlowStatus;
import com.smile.start.model.enums.*;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.service.audit.AuditService;
import com.smile.start.service.auth.RoleInfoService;
import com.smile.start.service.common.FileService;
import com.smile.start.service.project.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoginHandler;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.exception.ValidateException;
import com.smile.start.mapper.ContractInfoMapper;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.contract.ContractExtendInfo;
import com.smile.start.model.contract.ContractInfo;
import com.smile.start.model.contract.ContractReceivableAgreement;
import com.smile.start.model.contract.ContractReceivableConfirmation;
import com.smile.start.model.contract.ContractSignList;
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

        //获取合同附件
        final List<ProjectItem> attachs = projectItemDao.getTypeItems(contractInfo.getProjectId(), ProjectItemType.CONTRACT);
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
        contractInfo.setStatus(ContractStatusEnum.NEW.getValue());
        LoginUser loginUser = LoginHandler.getLoginUser();
        contractInfo.setCreateUser(loginUser.getSerialNo());
        contractInfo.setDeleteFlag(DeleteFlagEnum.UNDELETED.getValue());
        final Project project = projectService.getProject(contractInfoDTO.getBaseInfo().getProjectId());

        //保存签署清单
        insertSignList(contractInfoDTO.getSignList(), contractSerialNo);

        //保存合同信息
        final ContractExtendInfo contractExtendInfo = contractInfoMapper.dto2do(contractInfoDTO.getContractExtendInfo());
        contractExtendInfo.setSerialNo(SerialNoGenerator.generateSerialNo("CEI", 5));
        contractExtendInfo.setContractSerialNo(contractSerialNo);
        contractExtendInfo.setContractCode(project.getProjectId() + "-1");
        contractExtendInfoDao.insert(contractExtendInfo);

        //保存应收账款转让确认函
        final ContractReceivableConfirmation contractReceivableConfirmation = contractInfoMapper.dto2do(contractInfoDTO.getContractReceivableConfirmation());
        contractReceivableConfirmation.setSerialNo(SerialNoGenerator.generateSerialNo("CRC", 5));
        contractReceivableConfirmation.setContractSerialNo(contractSerialNo);
        contractReceivableConfirmation.setConfirmationCode(project.getProjectId() + "-2");
        contractReceivableConfirmationDao.insert(contractReceivableConfirmation);

        //保存应收账款转让登记协议
        final ContractReceivableAgreement contractReceivableAgreement = contractInfoMapper.dto2do(contractInfoDTO.getContractReceivableAgreement());
        contractReceivableAgreement.setSerialNo(SerialNoGenerator.generateSerialNo("CRA", 5));
        contractReceivableAgreement.setContractSerialNo(contractSerialNo);
        contractReceivableAgreement.setProtocolCode(project.getProjectId() + "-3");
        contractReceivableAgreementDao.insert(contractReceivableAgreement);

        //附件合同
        insertAttachList(contractInfoDTO);
        Long contractId = contractInfoDao.insert(contractInfo);

        //生成标准合同文件
        try {
            String fileName = "应收账款转让登记协议" + contractReceivableAgreement.getProtocolCode();
            File file = DocUtil.createDoc(fileName,
                    "附件2：应收账款转让登记协议RJBL-2018-005-3_模板.xml", buildTemplateData(contractReceivableAgreement));
            if (file.exists()) {
                final FileInfo upload = fileService.upload(file, fileName);
                ProjectItem projectItem = new ProjectItem();
                projectItem.setAttachType(ContractAttachTypeEnum.STANDARD.getValue());
                projectItem.setProjectId(contractInfoDTO.getBaseInfo().getProjectId());
                projectItem.setItemType(ProjectItemType.CONTRACT);
                projectItem.setItemName(fileName);
                projectItem.setItemValue(upload.getFileId());
                projectItemDao.insert(projectItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contractId;
    }

    /**
     * 构建登录协议模板数据
     * @param contractReceivableAgreement
     * @return
     */
    private Map<String, Object> buildTemplateData(ContractReceivableAgreement contractReceivableAgreement) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("protocolCode", contractReceivableAgreement.getProtocolCode());
        data.put("spName", contractReceivableAgreement.getSpName());
        data.put("spResidence", contractReceivableAgreement.getSpResidence());
        data.put("spLegalPerson", contractReceivableAgreement.getSpLegalPerson());
        data.put("spContactAddress", contractReceivableAgreement.getSpContactAddress());
        data.put("spPostCode", contractReceivableAgreement.getSpPostCode());
        data.put("spTelephone", contractReceivableAgreement.getSpTelephone());
        data.put("spFax", contractReceivableAgreement.getSpFax());
        data.put("contractSignDate", DateUtil.format(contractReceivableAgreement.getContractSignDate(), DateUtil.chineseDtFormat));
        return data;
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
        final Project project = projectService.getProject(contractInfoDTO.getBaseInfo().getProjectId());
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
//        contractAttachDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        List<ProjectItem> typeItems = projectItemDao.getTypeItems(contractInfo.getProjectId(),
                ProjectItemType.CONTRACT);
        if(!CollectionUtils.isEmpty(typeItems)) {
            typeItems.forEach(e -> projectItemDao.delete(e));
        }
//        projectItemDao.delete(contractInfo.getProjectId(), ProjectItemType.CONTRACT.getCode());
        insertAttachList(contractInfoDTO);

        //生成标准合同文件
        try {
            String fileName = "应收账款转让登记协议" + contractReceivableAgreement.getProtocolCode() + ".doc";
            File file = DocUtil.createDoc(fileName,
                    "附件2：应收账款转让登记协议RJBL-2018-005-3_模板.xml", buildTemplateData(contractReceivableAgreement));
            if (file.exists()) {
                final FileInfo upload = fileService.upload(file, fileName);
                ProjectItem projectItem = new ProjectItem();
                projectItem.setAttachType(ContractAttachTypeEnum.STANDARD.getValue());
                projectItem.setProjectId(contractInfoDTO.getBaseInfo().getProjectId());
                projectItem.setItemType(ProjectItemType.CONTRACT);
                projectItem.setItemName(fileName);
                projectItem.setItemValue(upload.getFileId());
                projectItemDao.insert(projectItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                ProjectItem projectItem = new ProjectItem();
                projectItem.setAttachType(ContractAttachTypeEnum.USER_DEFINED.getValue());
                projectItem.setProjectId(contractInfoDTO.getBaseInfo().getProjectId());
                projectItem.setItemType(ProjectItemType.CONTRACT);
                projectItem.setItemName(e.getAttachName());
                projectItem.setItemValue(e.getFileId());
                projectItemDao.insert(projectItem);
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
        //        contractSignListDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        //        contractReceivableConfirmationDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        //        contractReceivableAgreementDao.deleteByContractSerialNo(contractInfo.getSerialNo());
        //        contractExtendInfoDao.deleteByContractSerialNo(contractInfo.getSerialNo());
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

        final LoginUser loginUser = LoginHandler.getLoginUser();
        //此处先从数据库中查询是否有记录，当驳回到拟定状态时需要做更新，不能直接插入
        Audit audit = auditService.getAuditByProjectFlowAndType(contractInfo.getProjectId(), AuditType.CONTRACT.getCode());
        if (audit == null) {
            audit = new Audit();
        }
        //TODO 查询数据库次数太多，可能影响性能
        audit.setApplicant(userDao.findBySerialNo(loginUser.getSerialNo()));
        audit.setProject(projectService.getProject(contractInfo.getProjectId()));
        audit.setCreateTime(new Date());
        audit.setAuditType(AuditType.CONTRACT);
        audit.setStep(ContractStatusEnum.APPLY.getValue());
        //获取审核流程
        final FlowStatus flowStatus = flowConfigDao.findByFlowTypeAndStatus(FlowTypeEnum.CONTRACT.getValue(), contractInfo.getStatus() + 1);
        audit.setRole(roleInfoService.getBySerialNo(flowStatus.getRoleSerialNo()));
        if (audit.getId() == null) {
            auditDao.insert(audit);
        } else {
            auditDao.updateRole(audit);
        }

        //保存审核记录
        AuditRecord record = new AuditRecord();
        record.setStatus("成功");
        record.setRemark("申请成功");
        record.setType("提出申请");
        record.setAudit(audit);
        record.setAuditor(audit.getApplicant());
        record.setResult(AuditResult.APPLY);
        record.setStatus(record.getResult().getDesc());
        record.setAuditTime(new Date());
        auditRecordDao.insert(record);
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
        audit.setAuditType(AuditType.CONTRACT);

        //审核通过
        if (contractAuditDTO.getOperationType() == 1) {
            contractInfo.setStatus(currentStatus.getNextStatus().getValue());
            //如果集团正总审核完成，则直接将合同状态更新为完成，流转到办公室人员，并修改项目状态为拟定合同
            if (currentStatus.getNextStatus() == ContractStatusEnum.GENERAL_MANAGER_AUDIT) {
                contractInfo.setStatus(ContractStatusEnum.FINISH.getValue());

                //状态流转到下一级
                audit.setStep(ContractStatusEnum.SIGN.getValue());

                //更新项目状态
                project.setProgress(Progress.DRAWUP);
                projectService.turnover(project);
            } else {
                //状态流转到下一级
                audit.setStep(currentStatus.getNextStatus().getValue());
            }

            record.setType(currentStatus.getNextStatus().getDesc());
            record.setStatus(currentStatus.getNextStatus().getDesc() + "通过");
            record.setResult(AuditResult.PASS);
            //获取审核流程
            final FlowStatus flowStatus = flowConfigDao.findByFlowTypeAndStatus(FlowTypeEnum.CONTRACT.getValue(), contractInfo.getStatus());
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
            final FlowStatus flowStatus = flowConfigDao.findByFlowTypeAndStatus(FlowTypeEnum.CONTRACT.getValue(), contractInfo.getStatus());
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
