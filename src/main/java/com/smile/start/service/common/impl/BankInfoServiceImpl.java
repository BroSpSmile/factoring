package com.smile.start.service.common.impl;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.BankInfoDao;
import com.smile.start.dto.BankInfoDTO;
import com.smile.start.dto.BankInfoSearchDTO;
import com.smile.start.mapper.BankInfoMapper;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.common.BankInfo;
import com.smile.start.service.common.BankInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/3/9 14:31, BankInfoServiceImpl.java
 * @since 1.8
 */
@Service
public class BankInfoServiceImpl implements BankInfoService {

    @Resource
    private BankInfoDao bankInfoDao;

    @Resource
    private BankInfoMapper bankInfoMapper;

    /**
     * 根据主键查银行信息
     * @param id
     * @return
     */
    public BankInfoDTO get(Long id) {
        return bankInfoMapper.do2dto(bankInfoDao.get(id));
    }

    /**
     * 分页查询银行信息
     * @return
     */
    @Override
    public PageInfo<BankInfoDTO> findAll(PageRequest<BankInfoSearchDTO> page) {
        final PageInfo<BankInfoDTO> result = new PageInfo<>();
        final List<BankInfo> bankList = bankInfoDao.findByParam(page.getCondition());
        result.setTotal(bankList.size());
        result.setPageSize(10);
        result.setList(bankInfoMapper.doList2dtoList(bankList));
        return result;
    }

    /**
     * 查询所有银行信息
     * @return
     */
    @Override
    public List<BankInfoDTO> findAll() {
        return bankInfoMapper.doList2dtoList(bankInfoDao.findAll());
    }

    /**
     * 新增银行信息
     * @param bankInfoDTO
     * @return
     */
    @Override
    public Long insert(BankInfoDTO bankInfoDTO) {
        bankInfoDTO.setSerialNo(SerialNoGenerator.generateSerialNo("B", 7));
        return bankInfoDao.insert(bankInfoMapper.dto2do(bankInfoDTO));
    }

    /**
     * 更新银行信息
     * @param bankInfoDTO
     */
    @Override
    public void update(BankInfoDTO bankInfoDTO) {
        bankInfoDao.update(bankInfoMapper.dto2do(bankInfoDTO));
    }

    /**
     * 删除银行信息
     * @param id
     */
    @Override
    public void delete(Long id) {
        bankInfoDao.delete(id);
    }
}
