package com.smile.start.service.common.impl;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.FlowConfigDao;
import com.smile.start.dto.FlowConfigDTO;
import com.smile.start.dto.FlowConfigSearchDTO;
import com.smile.start.mapper.FlowConfigMapper;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.common.FlowConfig;
import com.smile.start.model.common.StatusInfo;
import com.smile.start.model.enums.FlowTypeEnum;
import com.smile.start.service.common.FlowConfigService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 13:21, FlowConfigServiceImpl.java
 * @since 1.8
 */
@Service
public class FlowConfigServiceImpl implements FlowConfigService {

    @Resource
    private FlowConfigDao flowConfigDao;

    @Resource
    private FlowConfigMapper flowConfigMapper;

    /**
     * 根据主键查询流程配置信息
     *
     * @param id
     * @return
     */
    @Override
    public FlowConfigDTO get(Long id) {
        return null;
    }

    /**
     * 查询所有流程配置信息
     * @return
     */
    @Override
    public PageInfo<FlowConfigDTO> findAll(PageRequest<FlowConfigSearchDTO> page) {
        final PageInfo<FlowConfigDTO> result = new PageInfo<>();
        final List<FlowConfig> doList = flowConfigDao.findByParam(page.getCondition());
        result.setTotal(doList.size());
        result.setPageSize(10);
        result.setList(flowConfigMapper.doList2dtoListConfig(doList));
        return result;
    }

    /**
     * 新增流程配置信息
     *
     * @param flowConfigDTO
     * @return
     */
    @Override
    public Long insert(FlowConfigDTO flowConfigDTO) {
        FlowConfig flowConfig = flowConfigMapper.dto2do(flowConfigDTO);
        flowConfig.setSerialNo(SerialNoGenerator.generateSerialNo("FC", 6));
        Date nowDate = new Date();
        flowConfig.setGmtCreate(nowDate);
        flowConfig.setGmtModify(nowDate);
        return flowConfigDao.insert(flowConfig);
    }

    /**
     * 更新流程配置信息
     *
     * @param flowConfigDTO
     */
    @Override
    public void update(FlowConfigDTO flowConfigDTO) {
        FlowConfig flowConfig = flowConfigMapper.dto2do(flowConfigDTO);
        flowConfig.setGmtModify(new Date());
        flowConfigDao.update(flowConfig);
    }

    /**
     * 删除流程配置信息
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        final FlowConfig flowConfig = flowConfigDao.get(id);
        flowConfigDao.deleteStatusRole(flowConfig.getSerialNo());
        flowConfigDao.deleteFlowStatus(flowConfig.getSerialNo());
        flowConfigDao.delete(id);
    }

    /**
     * 根据流程类型获取对应状态列表
     * @param flowType
     * @return
     */
    @Override
    public List<StatusInfo> getStatusList(int flowType) {
        FlowTypeEnum flowTypeEnum = FlowTypeEnum.fromValue(flowType);
        switch (flowTypeEnum) {
            case PROJECT:
                break;
            case CONTRACT:
                break;
            default:
                ;
        }
        return null;
    }
}
