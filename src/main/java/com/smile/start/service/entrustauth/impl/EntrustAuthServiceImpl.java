package com.smile.start.service.entrustauth.impl;

import com.smile.start.dao.EntrustAuthDao;
import com.smile.start.model.enums.Step;
import com.smile.start.model.project.EntrustAuth;
import com.smile.start.service.entrustauth.EntrustAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：xioutman
 * @description：
 * @date ：Created in 2019/3/29 20:57
 * @modified By：
 * @version: $
 */
@Service
public class EntrustAuthServiceImpl implements EntrustAuthService {

    @Resource
    private EntrustAuthDao entrustAuthDao;

    @Override
    public Long insert(EntrustAuth entrustAuth) {
        return entrustAuthDao.insert(entrustAuth);
    }

    @Override
    public EntrustAuth query(Long projectId) {
        return entrustAuthDao.get(projectId);
    }

    @Override
    public int update(EntrustAuth entrustAuth) {
        return entrustAuthDao.update(entrustAuth);
    }

    @Override
    public void delete(Long projectId) {
        entrustAuthDao.delete(projectId);
    }

    @Override
    public List<Long> getEntrustAuthProjectIds(Long toUserId, Step type) {
        return entrustAuthDao.getEntrustAuthProjectIds(toUserId, type);
    }
}
