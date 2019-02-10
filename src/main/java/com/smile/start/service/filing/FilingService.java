package com.smile.start.service.filing;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.project.Project;

public interface FilingService {

    /**
     * 新增归档申请
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
    BaseResult updateFilingApply(FilingApplyInfo filingApplyInfo);


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
}
