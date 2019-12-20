package com.smile.start.service.seal;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.dto.SealSearchDTO;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.seal.ProjectSeal;

/**
 * @author Joseph
 * @version v1.0 2019/3/17 21:27, SealService.java
 * @since 1.8
 */
public interface SealService {

    /**
     * 查询所有待用印项目信息
     * @return
     */
    PageInfo<ProjectSeal> findAll(PageRequest<SealSearchDTO> pageRequest);

    /**
     * 查询当前登录用户所有用印记录
     * @return
     */
    PageInfo<ProjectSeal> findAllRecord(PageRequest<SealSearchDTO> pageRequest);

    /**
     * 用印完成
     * @param projectId
     */
    void sealFinish(Long projectId);
}
