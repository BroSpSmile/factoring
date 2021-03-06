package com.smile.start.service.filing;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.filing.FilingApplyInfo;

@Deprecated
public interface FilingService {

    /**
     *
     * @param filingApplyInfo
     * @return
     */
    BaseResult addFilingApply(FilingApplyInfo filingApplyInfo);

    /**
     * 更新归档申请
     * @param filingApplyInfo
     * @return
     */
    BaseResult updateFilingApply(FilingApplyInfo filingApplyInfo, boolean isUpdateItem);

    /**
     * 删除档申请
     * @param id
     * @return
     */
    BaseResult delete(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    PageInfo<FilingApplyInfo> queryPage(PageRequest<FilingApplyInfo> page);

    /**
     *
     * @param projectId
     * @return
     */
    FilingApplyInfo findByProjectId(Long projectId);

}
