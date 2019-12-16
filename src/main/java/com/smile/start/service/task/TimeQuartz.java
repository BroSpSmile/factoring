/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.model.base.BaseResult;
import com.smile.start.service.AbstractService;
import com.smile.start.service.meeting.MeetingService;
import com.smile.start.service.wechat.AccessTokenService;

/**
 * 调度服务
 * @author smile.jing
 * @version $Id: TimeQuartz.java, v 0.1 Feb 24, 2019 10:20:20 PM smile.jing Exp $
 */
@Service
public class TimeQuartz extends AbstractService {
    /** 会议服务 */
    @Resource
    private MeetingService     meetingService;

    /** token服务 */
    @Resource
    private AccessTokenService accessTokenService;

    /**
     * 每分钟执行调度任务
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void executeMeetingTask() {
        BaseResult result = meetingService.schedule();
        LoggerUtils.info(logger, "执行调度任务result={}", result.isSuccess());
    }

    /**
     * 每小时执行调度任务
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void executeTokenTask() {
        accessTokenService.synchronousToken();
        LoggerUtils.info(logger, "执行token调度任务");
    }
}
