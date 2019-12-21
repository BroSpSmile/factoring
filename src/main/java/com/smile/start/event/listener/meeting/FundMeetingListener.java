/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener.meeting;

import javax.annotation.Resource;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.smile.start.event.MeetingEvent;
import com.smile.start.mapper.ProjectMapper;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.BaseProject;
import com.smile.start.service.fund.FundService;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundMeetingListener
 * @date Date : 2019年11月03日 22:43
 */
@Component
public class FundMeetingListener implements MeetingListener {

    /** fundService */
    @Resource
    private FundService fundService;

    /**
     * 监听方法
     *
     * @param event 事件
     */
    @Override
    @Async
    @EventListener
    public void listener(MeetingEvent event) {
        BaseProject<FundTarget> project = ProjectMapper.mapper(event.getProject(), FundTarget.class);
        FundTarget fundTarget = fundService.getTarget(project.getProjectId());
        if (fundTarget.getInvestment() > 15000000) {
            fundTarget.setProjectStep(FundStatus.SASAC_APPROVAL);
        } else {
            fundTarget.setProjectStep(FundStatus.CONTRACT_SIGN);
        }
        project.setDetail(fundTarget);
        fundService.modifyTarget(project);
    }
}
