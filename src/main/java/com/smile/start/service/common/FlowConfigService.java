package com.smile.start.service.common;

import com.github.pagehelper.PageInfo;
import com.smile.start.dto.FlowConfigDTO;
import com.smile.start.dto.FlowConfigSearchDTO;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.common.StatusInfo;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 13:21, FlowConfigService.java
 * @since 1.8
 */
public interface FlowConfigService {

    /**
     * 根据主键查询流程配置信息
     *
     * @param id
     * @return
     */
    FlowConfigDTO get(Long id);

    /**
     * 查询所有流程配置信息
     * @return
     */
    PageInfo<FlowConfigDTO> findAll(PageRequest<FlowConfigSearchDTO> page);

    /**
     * 新增流程配置信息
     *
     * @param flowConfigDTO
     * @return
     */
    Long insert(FlowConfigDTO flowConfigDTO);

    /**
     * 更新流程配置信息
     *
     * @param flowConfigDTO
     */
    void update(FlowConfigDTO flowConfigDTO);

    /**
     * 删除流程配置信息
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 根据流程类型获取对应状态列表
     * @param flowType
     * @return
     */
    List<StatusInfo> getStatusList(int flowType);
}
