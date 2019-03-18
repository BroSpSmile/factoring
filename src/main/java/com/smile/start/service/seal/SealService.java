package com.smile.start.service.seal;

import com.github.pagehelper.PageInfo;
import com.smile.start.dto.SealSearchDTO;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.seal.ProjectSeal;

/**
 * @author Joseph
 * @version v1.0 2019/3/17 21:27, SealService.java
 * @since 1.8
 */
public interface SealService {

    /**
     * 查询所有待签署项目信息
     * @return
     */
    PageInfo<ProjectSeal> findAll(PageRequest<SealSearchDTO> pageRequest);
}
