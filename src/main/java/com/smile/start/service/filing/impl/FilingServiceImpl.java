package com.smile.start.service.filing.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.FilingDao;
import com.smile.start.dao.ProjectDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.FilingProgress;
import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.filing.FilingFileItem;
import com.smile.start.model.project.Project;
import com.smile.start.service.AbstractService;
import com.smile.start.service.common.FileService;
import com.smile.start.service.filing.FilingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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

    /**
     * 归档DAO
     */
    @Resource
    private FilingDao filingDao;

    /**
     * 项目DAO
     */
    @Resource
    private ProjectDao projectDao;

    /**
     * 文件服务
     */
    @Resource
    private FileService fileService;

    @Override
    @Transactional
    public BaseResult addFilingApply(FilingApplyInfo filingApplyInfo) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        List<FilingApplyInfo> filingApplyInfos = filingDao.findByProjectId(filingApplyInfo.getProjectId());
        long effect = 0l;
        if (!CollectionUtils.isEmpty(filingApplyInfos) && filingApplyInfos.size() > 0) {
            //保存过，需要更新
            effect = update(filingApplyInfo, true);
        } else {
            effect = add(filingApplyInfo);
        }

        //更新项目状态为归档审核状态
        long updateProjectEffect = updateProject(filingApplyInfo);

        if (effect > 0 && updateProjectEffect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011001");
            result.setErrorMessage("归档申请失败,请重试!");
        }
        return result;
    }

    @Override
    @Transactional
    public BaseResult updateFilingApply(FilingApplyInfo filingApplyInfo, boolean isUpdateItem) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        List<FilingApplyInfo> filingApplyInfos = filingDao.findByProjectId(filingApplyInfo.getProjectId());
        if (CollectionUtils.isEmpty(filingApplyInfos)) {
            throw new RuntimeException("当前归档申请不存在");
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
        return result;
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
            filingDao.delFileItemByProjectId(filingApplyInfo.getProjectId());
            for (FilingFileItem item : filingApplyInfo.getItems()) {
                filingDao.insertFileItem(item);
            }
        }

        return effect;
    }

    private long updateProject(FilingApplyInfo filingApplyInfo) {
        long updateProjectEffect =
                projectDao.updateProjectProgress(filingApplyInfo.getProjectId(), filingApplyInfo.getProgress());
        LoggerUtils.info(logger, "更新项目状态，影响行effect={}", updateProjectEffect);
        return updateProjectEffect;
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
            if (!FilingProgress.TOBEFILED.getCode().equals(filingApplyInfo.getProgress())) {
                throw new RuntimeException("删除归档申请失败,当前归档申请状态非法");
            }
            int effect = filingDao.delete(id);
            LoggerUtils.info(logger, "删除归档申请影响行effect={}", effect);

            //删除归档文件 in db
            long effectDelItem = filingDao.delFileItemByProjectId(filingApplyInfo.getProjectId());
            if (effect > 0 && effectDelItem > 0) {
                result.setSuccess(true);
            } else {
                result.setErrorCode("VP00011003");
                result.setErrorMessage("删除归档申请失败,请重试!");
            }
            return result;
        } finally {
            //删除文件
            List<FilingFileItem> items = filingDao.findItemByProjectId(filingApplyInfo.getProjectId());
            for (FilingFileItem item : items) {
                LoggerUtils.info(logger, "删除文件ID={}", item.getItemValue());
                fileService.delete(item.getItemValue());
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

    @Override
    public FilingApplyInfo findByProjectId(String projectId) {
        FilingApplyInfo filingApplyInfo = null;
        List<FilingApplyInfo> filingApplyInfos = filingDao.findByProjectId(projectId);
        if (null != filingApplyInfos && !filingApplyInfos.isEmpty()) {
            filingApplyInfo = filingApplyInfos.get(0);
            List<FilingFileItem> items = filingDao.findItemByProjectId(projectId);
            filingApplyInfo.setItems(items);
        }
        List<Project> projectList = projectDao.findByProjectId(projectId);
        if (!CollectionUtils.isEmpty(projectList) && null != projectList.get(0)) {
            filingApplyInfo.setProjectName(projectList.get(0).getProjectName());
        }
        return filingApplyInfo;
    }
}
