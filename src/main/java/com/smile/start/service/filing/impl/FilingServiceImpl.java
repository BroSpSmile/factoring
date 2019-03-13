package com.smile.start.service.filing.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.*;
import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.dto.FlowConfigDTO;
import com.smile.start.dto.FlowStatusDTO;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.enums.*;
import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.filing.FilingFileItem;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditRecord;
import com.smile.start.model.project.Project;
import com.smile.start.service.AbstractService;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.common.FileService;
import com.smile.start.service.common.FlowConfigService;
import com.smile.start.service.filing.FilingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author ：xioutman
 * @date ：Created in 2019/2/4 11:10
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class FilingServiceImpl extends AbstractService implements FilingService {

    private static final FlowTypeEnum FLOW_TYPE  = FlowTypeEnum.valueOf(Progress.FILE.name());

    private static final AuditType    AUDIT_TYPE = AuditType.valueOf(Progress.FILE.name());

    /**
     * 归档DAO
     */
    @Resource
    private FilingDao                 filingDao;

    /**
     * 项目DAO
     */
    @Resource
    private ProjectDao                projectDao;

    /**
     * auditDao
     */
    @Resource
    private AuditDao                  auditDao;

    /**
     * auditRecordDao
     */
    @Resource
    private AuditRecordDao            auditRecordDao;

    /**
     * auditRecordItemDao
     */
    @Resource
    private AuditRecordItemDao        auditRecordItemDao;

    /**
     * 文件服务
     */
    @Resource
    private FileService               fileService;

    /**
     * 用户服务
     */
    @Resource
    private UserInfoService           userInfoService;

    /**
     * flowConfigService
     */
    @Resource
    private FlowConfigService         flowConfigService;

    @Override
    @Transactional
    public BaseResult addFilingApply(FilingApplyInfo filingApplyInfo) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        List<FilingApplyInfo> filingApplyInfos = filingDao.findByProjectId(filingApplyInfo.getProject());
        long effect = 0l;
        if (!CollectionUtils.isEmpty(filingApplyInfos) && filingApplyInfos.size() > 0) {
            //保存过，需要更新
            effect = update(filingApplyInfo, true);
        } else {
            effect = add(filingApplyInfo);
        }

        if (effect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011001");
            result.setErrorMessage("归档申请失败,请重试!");
        }

        if (result.isSuccess() && !filingApplyInfo.getProgress().equals(FilingSubProgress.FILE_TOBE_APPLY)) {
            //commit, 更新项目状态
            long updateProjectEffect = updateProject(filingApplyInfo);
            if (updateProjectEffect > 0) {
                result.setSuccess(true);
            } else {
                result.setErrorCode("VP00011001");
                result.setErrorMessage("归档申请失败,更新项目进度失败,请重试!");
                return result;
            }

            Project project = projectDao.get(filingApplyInfo.getProject());
            if (null != project) {
                User user = filingApplyInfo.getRecord().getAuditor();
                Audit audit = auditDao.getByProjectAndType(project.getId(), AuditType.FILE.getCode());
                if (null != audit) {
                    //流程第一步，user为申请人
                    //step 处理完之后的流程状态
                    FlowStatusDTO step = getStep(filingApplyInfo.getProgress());
                    audit.setApplicant(user);

                    //变更流程表
                    updateAuditStep(filingApplyInfo, audit, step);
                    addAuditRecord(filingApplyInfo, audit);
                } else {
                    //流程第一步,user为申请人, 这里需要变更
                    audit = getAudit(project, user, filingApplyInfo.getProgress());

                    //新增流程表
                    SingleResult<Audit> addAuditResult = addAudit(audit);
                    if (addAuditResult.isSuccess()) {
                        addAuditRecord(filingApplyInfo, audit);
                    } else {
                        return addAuditResult;
                    }
                }
            }
        }

        return result;
    }

    private void addAuditRecord(FilingApplyInfo filingApplyInfo, Audit audit) {
        AuditRecord record = filingApplyInfo.getRecord();
        FlowStatusDTO step = getStep(FilingSubProgress.last(filingApplyInfo.getProgress()));
        record.setType(step.getFlowStatusDesc());
        record.setAudit(audit);
        if (AuditResult.APPLY == record.getResult()) {
            record.setStatus(FilingSubProgress.getByIndex(audit.getStep()).getDesc());
        }
        auditRecordDao.insert(record);
    }

    @Override
    @Transactional
    public BaseResult updateFilingApply(FilingApplyInfo filingApplyInfo, boolean isUpdateItem) {
        AuditRecord record = filingApplyInfo.getRecord();

        BaseResult result = new BaseResult();
        result.setSuccess(false);
        FilingApplyInfo oldFilingApplyInfo = null;
        List<FilingApplyInfo> filingApplyInfos = filingDao.findByProjectId(filingApplyInfo.getProject());
        if (CollectionUtils.isEmpty(filingApplyInfos)) {
            throw new RuntimeException("当前归档申请不存在");
        } else {
            oldFilingApplyInfo = filingApplyInfos.get(0);
        }
        int effect = update(filingApplyInfo, isUpdateItem);

        //更新项目状态为归档审核状态
        long updateProjectEffect = updateProject(filingApplyInfo);

        if (effect > 0 && updateProjectEffect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011002");
            result.setErrorMessage("归档审批失败,请重试!");
        }
        if (result.isSuccess() && null != record) {
            Audit audit = auditDao.getByProjectAndType(filingApplyInfo.getProject(), AuditType.FILE.getCode());
            updateAuditStep(filingApplyInfo, audit, getStep(filingApplyInfo.getProgress()));

            FlowStatusDTO step = getStep(oldFilingApplyInfo.getProgress());
            record.setType(step.getFlowStatusDesc());
            record.setAuditTime(new Date());
            record.setAudit(audit);
            if (AuditResult.PASS == record.getResult()) {
                record.setStatus(FilingSubProgress.getByIndex(audit.getStep()).getDesc() + "通过");
            }
            if (AuditResult.REJECTED == record.getResult()) {
                record.setStatus("驳回至" + FilingSubProgress.getByIndex(audit.getStep() + 1).getDesc());
            }
            if (AuditResult.COMPLETE == record.getResult()) {
                record.setStatus(Progress.FILED.getDesc());
            }
            auditRecordDao.insert(record);
        }
        return result;
    }

    @Override
    @Transactional
    public BaseResult delete(Long id) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        FilingApplyInfo filingApplyInfo = filingDao.get(id);
        try {
            if (filingApplyInfo == null) {
                throw new RuntimeException("删除归档申请失败,当前归档申请不存在");
            }
            if (!FilingSubProgress.FILE_TOBE_APPLY.equals(filingApplyInfo.getProgress())) {
                throw new RuntimeException("删除归档申请失败,当前归档申请状态非法");
            }
            int effect = filingDao.delete(id);
            LoggerUtils.info(logger, "删除归档申请影响行effect={}", effect);

            //删除归档文件 in db
            long effectDelItem = filingDao.delFileItemByProjectId(filingApplyInfo.getProject(), Progress.FILE.getCode());

            //更新项目状态为待归档状态即 已放款状态
            long updateProjectEffect = updateProject(filingApplyInfo);

            if (effect > 0 && effectDelItem > 0 && updateProjectEffect > 0) {
                result.setSuccess(true);
            } else {
                result.setErrorCode("VP00011003");
                result.setErrorMessage("删除归档申请失败,请重试!");
            }

            return result;
        } finally {
            //删除文件
            if (null != filingApplyInfo) {
                List<FilingFileItem> items = filingDao.findItemByProjectId(filingApplyInfo.getProject(), Progress.FILE.getCode());
                for (FilingFileItem item : items) {
                    LoggerUtils.info(logger, "删除文件ID={}", item.getItemValue());
                    fileService.delete(item.getItemValue());
                }
            }
        }
    }

    @Override
    public PageInfo<FilingApplyInfo> queryPage(PageRequest<FilingApplyInfo> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), "id desc");
        List<FilingApplyInfo> filingApplyInfoList = filingDao.findByParam(page.getCondition());
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<FilingApplyInfo> result = new PageInfo<FilingApplyInfo>(filingApplyInfoList);
        return result;
    }

    /**
     * @param project
     * @return
     */
    @Override
    public FilingApplyInfo findByProjectId(Long project) {
        FilingApplyInfo filingApplyInfo = null;
        List<FilingApplyInfo> filingApplyInfos = filingDao.findByProjectId(project);
        if (null != filingApplyInfos && !filingApplyInfos.isEmpty()) {
            filingApplyInfo = filingApplyInfos.get(0);

            List<FilingFileItem> items = filingDao.findItemByProjectId(project, Progress.FILE.getCode());
            filingApplyInfo.setItems(items);
        }
        Project projectObj = projectDao.get(project);
        if (null == filingApplyInfo) {
            return null;
        }
        if (null != projectObj) {
            filingApplyInfo.setProjectName(projectObj.getProjectName());
            filingApplyInfo.setProjectId(projectObj.getProjectId());
        }
        try {
            User user = userInfoService.getUserById(Long.parseLong(filingApplyInfo.getApplicant()));
            filingApplyInfo.setApplicant(user.getUsername());
        } catch (Exception e) {
            LoggerUtils.info(logger, "查询归档申请人失败, ID={} ", filingApplyInfo.getApplicant());
        }

        return filingApplyInfo;
    }

    private long add(FilingApplyInfo filingApplyInfo) {
        long effect = filingDao.insert(filingApplyInfo);
        LoggerUtils.info(logger, "新增归档申请，影响行effect={}", effect);
        for (FilingFileItem item : filingApplyInfo.getItems()) {
            filingDao.insertFileItem(item);
        }

        return effect;
    }

    private int update(FilingApplyInfo filingApplyInfo, boolean isUpdateItem) {
        int effect = filingDao.update(filingApplyInfo);

        LoggerUtils.info(logger, "修改归档申请，影响行effect={}", effect);

        if (isUpdateItem) {
            filingDao.delFileItemByProjectId(filingApplyInfo.getProject(), Progress.FILE.getCode());
            for (FilingFileItem item : filingApplyInfo.getItems()) {
                filingDao.insertFileItem(item);
            }
        }

        return effect;
    }

    private long updateProject(FilingApplyInfo filingApplyInfo) {
        Progress progress = filingApplyInfo.getProgress().equals(FilingSubProgress.FILE_OFFICER) ? Progress.FILED : Progress.FILE;

        if (filingApplyInfo.getProgress().equals(FilingSubProgress.FILE_TOBE_APPLY)) {
            progress = Progress.LOANED;
        }
        long updateProjectEffect = projectDao.updateProjectProgress(filingApplyInfo.getProject(), progress.getCode());
        LoggerUtils.info(logger, "更新项目状态，影响行effect={}", updateProjectEffect);
        return updateProjectEffect;
    }

    private void updateAuditStep(FilingApplyInfo filingApplyInfo, Audit audit, FlowStatusDTO step) {
        if (null != step) {
            audit.setStep(step.getFlowStatus());
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            //role.setSerialNo(step.getCheckedRoleList().get(0));
            role.setSerialNo(step.getRoleSerialNo());
            audit.setRole(role);
        } else {
            //审批流程已结束
            audit.setStep(-1);
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(StringUtils.EMPTY);
            audit.setRole(role);
        }
        auditDao.updateRoleAndApplicant(audit);
    }

    public SingleResult<Audit> addAudit(Audit audit) {
        long effect = auditDao.insert(audit);
        SingleResult<Audit> result = new SingleResult<Audit>();
        if (effect > 0) {
            result.setData(audit);
            return result;
        } else {
            result.setSuccess(false);
            result.setErrorCode("VP00011001");
            result.setErrorMessage("新增失败,请重试!");
            return result;
        }
    }

    private Audit getAudit(Project project, User user, FilingSubProgress progress) {
        Audit audit = new Audit();
        audit.setAuditType(AUDIT_TYPE);
        audit.setApplicant(user);
        audit.setProject(project);
        audit.setCreateTime(new Date());
        audit.setStep(progress.getIndex());
        audit.setAuditType(AuditType.getByCode(project.getProgress().getCode()));
        FlowStatusDTO step = getStep(progress);
        if (null == step) {
            //审批流程已结束
            audit.setStep(-1);
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            role.setSerialNo(StringUtils.EMPTY);
            audit.setRole(role);
        } else {
            audit.setStep(step.getFlowStatus());

            //获取审核流程
            AuthRoleInfoDTO role = new AuthRoleInfoDTO();
            //role.setSerialNo(step.getCheckedRoleList().get(0));
            role.setSerialNo(step.getRoleSerialNo());
            audit.setRole(role);
        }
        return audit;
    }

    /**
     * 获取审核步骤
     *
     * @param progress
     * @return
     */
    private FlowStatusDTO getStep(FilingSubProgress progress) {
        if (null != progress) {
            FlowConfigDTO config = flowConfigService.getByType(FLOW_TYPE);
            List<FlowStatusDTO> steps = config.getStatusList();
            for (FlowStatusDTO step : steps) {
                if (step.getFlowStatus() == progress.getIndex()) {
                    return step;
                }
            }
        }
        return null;
    }

}
