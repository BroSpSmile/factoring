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
        long effect = filingDao.insert(filingApplyInfo);
        LoggerUtils.info(logger, "新增归档申请，影响行effect={}", effect);
        for (FilingFileItem item : filingApplyInfo.getItems()) {
            filingDao.insertFileItem(item);
        }

        //更新项目状态为归档审核状态
        long updateProgressEffect = projectDao.updateProjectProgress(filingApplyInfo.getProjectId(), filingApplyInfo.getProgress());
        LoggerUtils.info(logger, "更新项目状态，影响行effect={}", updateProgressEffect);
        BaseResult result = new BaseResult();
        if (effect > 0 && updateProgressEffect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011001");
            result.setErrorMessage("归档申请失败,请重试!");
        }
        return result;
    }

    @Override
    public BaseResult updateFilingApply(FilingApplyInfo filingApplyInfo) {
        List<FilingApplyInfo> filingApplyInfos = filingDao.findByProjectId(filingApplyInfo.getProjectId());
        if (!CollectionUtils.isEmpty(filingApplyInfos) && filingApplyInfos.size() > 1) {
            throw new RuntimeException("当前项目ID重复,无法更新");
        }
        if (!CollectionUtils.isEmpty(filingApplyInfos)) {
            for (FilingApplyInfo old : filingApplyInfos) {
                if (old.getId() != filingApplyInfo.getId()) {
                    throw new RuntimeException("当前归档申请ID重复,无法更新");
                }
            }
        }
        int effect = filingDao.update(filingApplyInfo);
        LoggerUtils.info(logger, "修改归档申请，影响行effect={}", effect);

        filingDao.delFileItemByProjectId(filingApplyInfo.getProjectId());
        for (FilingFileItem item : filingApplyInfo.getItems()) {
            filingDao.insertFileItem(item);
        }

        //更新项目状态为归档审核状态
        long updateProgressEffect = projectDao.updateProjectProgress(filingApplyInfo.getProjectId(), filingApplyInfo.getProgress());
        LoggerUtils.info(logger, "更新项目状态，影响行effect={}", updateProgressEffect);

        BaseResult result = new BaseResult();
        if (effect > 0 && updateProgressEffect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011002");
            result.setErrorMessage("归档审批失败,请重试!");
        }
        return result;
    }

    @Override
    @Transactional
    public BaseResult delete(Long id) {
        FilingApplyInfo filingApplyInfo = filingDao.get(id);
        if (filingApplyInfo == null) {
            throw new RuntimeException("删除项归档申请失败,当前归档申请不存在");
        }
        if (!FilingProgress.TOBEFILED.getCode().equals(filingApplyInfo.getProgress())) {
            throw new RuntimeException("删除归档申请失败,当前归档申请状态非法");
        }
        int effect = filingDao.delete(id);
        LoggerUtils.info(logger, "删除项归档申请影响行effect={}", effect);

        //删除文件
        List<FilingFileItem> items = filingDao.findItemByProjectId(filingApplyInfo.getProjectId());
        for (FilingFileItem item : items) {
            LoggerUtils.info(logger, "删除文件ID={}", item.getItemValue());
            fileService.delete(item.getItemValue());
        }

        //删除归档文件 in db
        long effectDelItem = filingDao.delFileItemByProjectId(filingApplyInfo.getProjectId());

        BaseResult result = new BaseResult();
        if (effect > 0 && effectDelItem > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011003");
            result.setErrorMessage("删除归档申请失败,请重试!");
        }
        return result;
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
        return filingApplyInfo;
    }
}
