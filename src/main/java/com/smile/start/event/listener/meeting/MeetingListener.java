/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event.listener.meeting;

import com.smile.start.event.MeetingEvent;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: MeetingListener
 * @date Date : 2019年11月03日 22:42
 */
public interface MeetingListener {
    /**
     * 监听方法
     * @param event 事件
     */
    void listener(MeetingEvent event);
}
