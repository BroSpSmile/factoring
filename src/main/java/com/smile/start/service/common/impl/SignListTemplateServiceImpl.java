package com.smile.start.service.common.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.SerialNoGenerator;
import com.smile.start.dao.SignListTemplateDao;
import com.smile.start.dto.SignListTemplateDTO;
import com.smile.start.dto.SignListTemplateSearchDTO;
import com.smile.start.mapper.SignListTemplateMapper;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.common.SignListTemplate;
import com.smile.start.service.common.SignListTemplateService;
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
    public PageInfo<SignListTemplateDTO> findAll(PageRequest<SignListTemplateSearchDTO> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), "id desc");
        final List<SignListTemplate> signList = signListTemplateDao.findByParam(pageRequest.getCondition());
        PageInfo<SignListTemplateDTO> pageInfo = new PageInfo<>(signListTemplateMapper.doList2dtoList(signList));
        Page page = (Page) signList;
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageNum(pageRequest.getPageNum());
        pageInfo.setPageSize(pageRequest.getPageSize());
        return pageInfo;
    }

    @Override
    public List<SignListTemplateDTO> findAll() {
        return signListTemplateMapper.doList2dtoList(signListTemplateDao.findAll());
    }

    /**
     * 按项目模式查询清单列表
     * @param projectMode
     * @return
     */
    @Override
    public List<SignListTemplateDTO> findByProjectMode(Integer projectMode) {
        return signListTemplateMapper.doList2dtoList(signListTemplateDao.findByProjectMode(projectMode));
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
