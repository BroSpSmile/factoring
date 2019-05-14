package com.smile.start.service.project.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smile.start.commons.LoginHandler;
import com.smile.start.dao.AuditDao;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.login.LoginUser;
import com.smile.start.model.project.ApplyHistory;
import com.smile.start.model.project.ApplyHistoryParam;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.Project;
import com.smile.start.service.project.ApplyHistoryService;
import com.smile.start.service.project.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/5/3 10:29, ApplyHistoryServiceImpl.java
 * @since 1.8
 */
@Service
public class ApplyHistoryServiceImpl implements ApplyHistoryService {

    @Resource
    private AuditDao auditDao;

    @Resource
    private ProjectService projectService;

    /**
     * 查询当前用户申请历史
     * @param param
     * @return
     */
    @Override
    public PageInfo<ApplyHistory> query(PageRequest<ApplyHistoryParam> param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize(), "a.create_time DESC");
        LoginUser loginUser = LoginHandler.getLoginUser();
        ApplyHistoryParam condition = param.getCondition();
        condition.setApplyUser(loginUser.getSerialNo());
        List<ApplyHistory> applyHistoryList = auditDao.queryApplyHistory(condition);
        for (ApplyHistory applyHistory : applyHistoryList) {
            Project project = projectService.getProject(applyHistory.getProjectId());
            applyHistory.setProject(project);
        }
        PageInfo<ApplyHistory> result = new PageInfo<>(applyHistoryList);
        return result;
    }
}
