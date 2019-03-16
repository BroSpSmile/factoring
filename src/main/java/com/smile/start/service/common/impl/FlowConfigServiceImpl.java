package com.smile.start.service.common.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smile.start.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.commons.LoginHandler;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.FlowConfigDao;
import com.smile.start.exception.ValidateException;
import com.smile.start.mapper.FlowConfigMapper;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.common.FlowConfig;
import com.smile.start.model.common.FlowStatus;
import com.smile.start.model.common.FlowStatusRole;
import com.smile.start.model.enums.ContractStatusEnum;
import com.smile.start.model.enums.FileFlowEnum;
import com.smile.start.model.enums.FlowTypeEnum;
import com.smile.start.model.enums.LoanFlowEnum;
import com.smile.start.model.enums.TuneUpFlowEnum;
import com.smile.start.model.login.LoginUser;
import com.smile.start.service.auth.RoleInfoService;
import com.smile.start.service.common.FlowConfigService;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 13:21, FlowConfigServiceImpl.java
 * @since 1.8
 */
@Service
public class FlowConfigServiceImpl implements FlowConfigService {

    @Resource
    private FlowConfigDao    flowConfigDao;

    @Resource
    private FlowConfigMapper flowConfigMapper;

    @Resource
    private RoleInfoService  roleInfoService;

    /**
     * 根据主键查询流程配置信息
     *
     * @param id
     * @return
     */
    @Override
    public FlowConfigDTO get(Long id) {
        final FlowConfig flowConfig = flowConfigDao.get(id);
        final FlowConfigDTO flowConfigDTO = flowConfigMapper.do2dto(flowConfig);
        final List<FlowStatus> statusList = flowConfigDao.findStatusByFlowSerialNo(flowConfigDTO.getSerialNo());
        final List<FlowStatusDTO> flowStatusDTOS = flowConfigMapper.doList2dtoListStatus(statusList);
        flowStatusDTOS.forEach(status -> {
            status.setRoleList(roleInfoService.findAll());
            List<FlowStatusRole> roleList = flowConfigDao.findRoleByStatusSerialNo(status.getSerialNo());
            if (!CollectionUtils.isEmpty(roleList)) {
                List<String> checkedRoleList = Lists.newArrayList();
                roleList.forEach(role -> checkedRoleList.add(role.getRoleSerialNo()));
                status.setCheckedRoleList(checkedRoleList);
            }
        });
        flowConfigDTO.setStatusList(flowStatusDTOS);
        return flowConfigDTO;
    }

    /** 
     * @see com.smile.start.service.common.FlowConfigService#getByType(com.smile.start.model.enums.FlowTypeEnum)
     */
    @Override
    public FlowConfigDTO getByType(FlowTypeEnum type) {
        FlowConfig flowConfig = flowConfigDao.getByType(type.getValue());
        return this.get(flowConfig.getId());
    }

    /**
     * 查询所有流程配置信息
     * @return
     */
    @Override
    public PageInfo<FlowConfigDTO> findAll(PageRequest<FlowConfigSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "id desc");
        final List<FlowConfig> flowList = flowConfigDao.findByParam(pageRequest.getCondition());
        PageInfo<FlowConfigDTO> pageInfo = new PageInfo<>(flowConfigMapper.doList2dtoListConfig(flowList));
        Page page = (Page) flowList;
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageNum(pageRequest.getPageNum());
        pageInfo.setPageSize(pageRequest.getPageSize());
        return pageInfo;
    }

    /**
     * 新增流程配置信息
     *
     * @param flowConfigDTO
     * @return
     */
    @Override
    @Transactional
    public Long insert(FlowConfigDTO flowConfigDTO) {
        final List<FlowConfig> flowList = flowConfigDao.findByFlowType(flowConfigDTO.getFlowType());
        if(!CollectionUtils.isEmpty(flowList)) {
            throw new ValidateException("此流程类型已存在记录");
        }
        FlowConfig flowConfig = flowConfigMapper.dto2do(flowConfigDTO);
        String flowSerialNo = SerialNoGenerator.generateSerialNo("FC", 6);
        flowConfig.setSerialNo(flowSerialNo);
        Date nowDate = new Date();
        flowConfig.setGmtCreate(nowDate);
        flowConfig.setGmtModify(nowDate);
        LoginUser loginUser = LoginHandler.getLoginUser();
        flowConfig.setCreateUser(loginUser.getSerialNo());

        //保存状态信息
        saveStatus(flowConfigDTO.getStatusList(), flowSerialNo);
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
        LoginUser loginUser = LoginHandler.getLoginUser();
        flowConfig.setModifyUser(loginUser.getSerialNo());
        flowConfigDao.update(flowConfig);

        //保存状态信息
//        flowConfigDao.deleteStatusRole(flowConfigDTO.getSerialNo());
        flowConfigDao.deleteFlowStatus(flowConfigDTO.getSerialNo());
        saveStatus(flowConfigDTO.getStatusList(), flowConfigDTO.getSerialNo());
    }

    private void saveStatus(List<FlowStatusDTO> statusList, String flowSerialNo) {
        for (FlowStatusDTO flowStatusDTO : statusList) {
            String statusSerialNo = SerialNoGenerator.generateSerialNo("FS", 6);
            FlowStatus flowStatus = new FlowStatus();
            flowStatus.setSerialNo(statusSerialNo);
            flowStatus.setFlowSerialNo(flowSerialNo);
            flowStatus.setFlowStatus(flowStatusDTO.getFlowStatus());
            flowStatus.setFlowStatusDesc(flowStatusDTO.getFlowStatusDesc());
            flowStatus.setRoleSerialNo(flowStatusDTO.getRoleSerialNo());
            flowConfigDao.insertFlowStatus(flowStatus);
//            if (!CollectionUtils.isEmpty(flowStatusDTO.getCheckedRoleList())) {
//                flowStatusDTO.getCheckedRoleList().forEach(e -> {
//                    FlowStatusRole flowStatusRole = new FlowStatusRole();
//                    flowStatusRole.setSerialNo(SerialNoGenerator.generateSerialNo("FSR", 5));
//                    flowStatusRole.setFlowSerialNo(flowSerialNo);
//                    flowStatusRole.setStatusSerialNo(statusSerialNo);
//                    flowStatusRole.setRoleSerialNo(e);
//                    flowConfigDao.insertStatusRole(flowStatusRole);
//                });
//            }
        }
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
    public List<FlowStatusDTO> getStatusList(int flowType) {
        FlowTypeEnum flowTypeEnum = FlowTypeEnum.fromValue(flowType);
        List<FlowStatusDTO> statusList = Lists.newArrayList();
        if (flowTypeEnum == null) {
            return statusList;
        }

        final List<AuthRoleInfoDTO> roleList = roleInfoService.findAll();
        switch (flowTypeEnum) {
            case TUNEUP:
                for (TuneUpFlowEnum tuneUpFlowEnum : TuneUpFlowEnum.values()) {
                    FlowStatusDTO flowStatus = new FlowStatusDTO();
                    flowStatus.setRoleList(roleList);
                    flowStatus.setFlowStatus(tuneUpFlowEnum.getValue());
                    flowStatus.setFlowStatusDesc(tuneUpFlowEnum.getDesc());
                    statusList.add(flowStatus);
                }
                return statusList;
            case CONTRACT:
                for (ContractStatusEnum contractStatusEnum : ContractStatusEnum.values()) {
                    FlowStatusDTO flowStatus = new FlowStatusDTO();
                    flowStatus.setRoleList(roleList);
                    flowStatus.setFlowStatus(contractStatusEnum.getValue());
                    flowStatus.setFlowStatusDesc(contractStatusEnum.getDesc());
                    statusList.add(flowStatus);
                }
                return statusList;
            case FILE:
                for (FileFlowEnum fileFlowEnum : FileFlowEnum.values()) {
                    FlowStatusDTO flowStatus = new FlowStatusDTO();
                    flowStatus.setRoleList(roleList);
                    flowStatus.setFlowStatus(fileFlowEnum.getValue());
                    flowStatus.setFlowStatusDesc(fileFlowEnum.getDesc());
                    statusList.add(flowStatus);
                }
                return statusList;
            case LOAN:
                for (LoanFlowEnum fileFlowEnum : LoanFlowEnum.values()) {
                    FlowStatusDTO flowStatus = new FlowStatusDTO();
                    flowStatus.setRoleList(roleList);
                    flowStatus.setFlowStatus(fileFlowEnum.getValue());
                    flowStatus.setFlowStatusDesc(fileFlowEnum.getDesc());
                    statusList.add(flowStatus);
                }
                return statusList;
            default:
                return statusList;
        }
    }
}
