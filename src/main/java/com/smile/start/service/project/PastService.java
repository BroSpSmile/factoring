/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project;

import java.util.List;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.project.Past;

/**
 * 
 * @author smile.jing
 * @version $Id: PastService.java, v 0.1 Feb 18, 2019 11:14:06 PM smile.jing Exp $
 */
public interface PastService {
    /**
     * 保存三重一大
     * @param past
     * @return
     */
    BaseResult save(Past past);

    /**
     * 获取已关联会议
     * @param projectId
     * @return
     */
    List<Meeting> getMeetings(Long projectId);
}
