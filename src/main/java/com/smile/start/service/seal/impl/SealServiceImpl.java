package com.smile.start.service.seal.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoginHandler;
import com.smile.start.dao.SealDao;
import com.smile.start.dao.SealRecordDao;
import com.smile.start.dao.factoring.ContractInfoDao;
import com.smile.start.dao.factoring.ContractSignListDao;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.contract.ContractInfo;
import com.smile.start.model.dto.SealSearchDTO;
import com.smile.start.model.enums.SealStatusEnum;
import com.smile.start.model.seal.ProjectSeal;
import com.smile.start.service.seal.SealService;

/**
 * 用印管理
 * @author Joseph
 * @version v1.0 2019/3/17 19:45, SealServiceImpl.java
 * @since 1.8
 */
@Service
public class SealServiceImpl implements SealService {

    @Resource
    private ContractInfoDao     contractInfoDao;

    @Resource
    private ContractSignListDao contractSignListDao;

    @Resource
    private SealDao             sealDao;

    @Resource
    private SealRecordDao       sealRecordDao;

    /**
     * 查询所有待用印项目信息
     * @return
     */
    @Override
    public PageInfo<ProjectSeal> findAll(PageRequest<SealSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "fp.id desc");
        final List<ProjectSeal> projectList = sealDao.findByParam(pageRequest.getCondition());
        return new PageInfo<>(projectList);
    }

    /**
     * 查询当前登录用户所有用印记录
     * @return
     */
    @Override
    public PageInfo<ProjectSeal> findAllRecord(PageRequest<SealSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "fp.id desc");
        SealSearchDTO condition = pageRequest.getCondition();
        condition.setSealUser(LoginHandler.getLoginUser().getSerialNo());
        final List<ProjectSeal> projectList = sealRecordDao.findByParam(condition);
        return new PageInfo<>(projectList);
    }

    /**
     * 用印完成
     * @param projectId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sealFinish(Long projectId) {
        ContractInfo contractInfo = contractInfoDao.getByProjectId(projectId);
        contractInfo.setSealStatus(SealStatusEnum.STAMPED.getValue());
        contractInfo.setSealFinishTime(new Date());
        contractInfoDao.update(contractInfo);

        //判断签署清单中是否存在 用印完成 项，如果有直接更新状态，没有则插入一条
        //        ContractSignList contractSignList = contractSignListDao.findByContractSerialNoAndName(contractInfo.getSerialNo(), "用印完成");
        //        if(contractSignList != null) {
        //            contractSignList.setStatus(true);
        //            contractSignListDao.update(contractSignList);
        //        } else {
        //            contractSignList = new ContractSignList();
        //            contractSignList.setSerialNo(SerialNoGenerator.generateSerialNo("CSL", 5));
        //            contractSignList.setContractSerialNo(contractInfo.getSerialNo());
        //            contractSignList.setSignListName("用印完成");
        //            contractSignList.setStatus(true);
        //            contractSignList.setIsRequired(1);
        //            contractSignListDao.insert(contractSignList);
        //        }
    }
}
