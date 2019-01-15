/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smile.start.commons.DateUtil;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.GenIdDao;
import com.smile.start.model.common.GenId;
import com.smile.start.model.enums.ProjectKind;
import com.smile.start.service.AbstractService;
import com.smile.start.service.IdGenService;

/**
 * 实现
 * 
 * @author smile.jing
 * @version $Id: IdGenServiceImpl.java, v 0.1 Jan 13, 2019 6:18:39 PM smile.jing Exp $
 */
@Service
public class IdGenServiceImpl extends AbstractService implements IdGenService {

    /** genIdDao */
    @Resource
    private GenIdDao genIdDao;

    /**
     * @see com.smile.start.service.IdGenService#genId(com.smile.start.model.enums.ProjectKind)
     */
    @Override
    @Transactional
    public String genId(ProjectKind kind) {
        GenId genId = genIdDao.getIdNoByKind(kind.getScode());
        if (DateUtil.isSameDay(new Date(), genId.getUpdateTime())) {
            genId.setGenIdNo(genId.getGenIdNo() + 1);
        } else {
            genId.setGenIdNo(1L);
        }
        genId.setUpdateTime(new Date());
        int effect = genIdDao.updateIdNo(genId);
        LoggerUtils.info(logger, "更新GenId影响行effect={}", effect);
        String idString = kind.getScode() + formatTime() + String.format("%04d", genId.getGenIdNo());
        LoggerUtils.info(logger, "生成ID={}", idString);
        return idString;
    }

}
