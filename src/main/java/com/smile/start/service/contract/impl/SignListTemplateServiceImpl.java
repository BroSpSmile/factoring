package com.smile.start.service.contract.impl;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.SignListTemplateDao;
import com.smile.start.dto.SignListTemplateDTO;
import com.smile.start.dto.SignListTemplateSearchDTO;
import com.smile.start.mapper.SignListTemplateMapper;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.contract.SignListTemplate;
import com.smile.start.service.contract.SignListTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/13 20:49, SignListTemplateServiceImpl.java
 * @since 1.8
 */
@Service
public class SignListTemplateServiceImpl implements SignListTemplateService {

    @Resource
    private SignListTemplateDao signListTemplateDao;

    @Resource
    private SignListTemplateMapper signListTemplateMapper;

    @Override
    public SignListTemplateDTO get(Long id) {
        return signListTemplateMapper.do2dto(signListTemplateDao.get(id));
    }

    @Override
    public PageInfo<SignListTemplateDTO> findAll(PageRequest<SignListTemplateSearchDTO> page) {
        final PageInfo<SignListTemplateDTO> result = new PageInfo<>();
        final List<SignListTemplate> doList = signListTemplateDao.findByParam(page.getCondition());
        result.setTotal(doList.size());
        result.setPageSize(10);
        result.setList(signListTemplateMapper.doList2dtoList(doList));
        return result;
    }

    @Override
    public List<SignListTemplateDTO> findAll() {
        return signListTemplateMapper.doList2dtoList(signListTemplateDao.findAll());
    }

    @Override
    public Long insert(SignListTemplateDTO signListTemplateDTO) {
        signListTemplateDTO.setSerialNo(SerialNoGenerator.generateSerialNo("S", 7));
        return signListTemplateDao.insert(signListTemplateMapper.dto2do(signListTemplateDTO));
    }

    @Override
    public void update(SignListTemplateDTO signListTemplateDTO) {
        signListTemplateDao.update(signListTemplateMapper.dto2do(signListTemplateDTO));
    }

    @Override
    public void delete(Long id) {
        signListTemplateDao.delete(id);
    }
}
