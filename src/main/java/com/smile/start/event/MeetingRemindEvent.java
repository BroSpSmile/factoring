/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event;

import org.springframework.context.ApplicationEvent;

import com.smile.start.model.meeting.MeetingExt;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: MeetingRemindEvent
 * @date Date : 2019年12月16日 22:01
 */
public class MeetingRemindEvent extends ApplicationEvent {

    /** 会议 */
    private MeetingExt meeting;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MeetingRemindEvent(Object source, MeetingExt meeting) {
        super(source);
        this.meeting = meeting;
    }

    /**
     * Getter method for property <tt>meeting</tt>.
     *
     * @return property value of meeting
     */
    public MeetingExt getMeeting() {
        return meeting;
    }

    /**
     * Setter method for property <tt>meeting</tt>.
     *
     * @param meeting value to be assigned to property  meeting
     */
    public void setMeeting(MeetingExt meeting) {
        this.meeting = meeting;
    }
}
