package com.smile.start.service.project;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.project.ApplyHistory;
import com.smile.start.model.project.ApplyHistoryParam;

/**
 * @author Joseph
 * @version v1.0 2019/5/3 10:58, ApplyHistoryService.java
 * @since 1.8
 */
public interface ApplyHistoryService {

    /**
     * 查询当前用户申请历史
     * @param param
     * @return
     */
    PageInfo<ApplyHistory> query(PageRequest<ApplyHistoryParam> param);
}
