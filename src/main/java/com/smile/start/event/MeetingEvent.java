/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event;

import org.springframework.context.ApplicationEvent;

import com.smile.start.model.project.Project;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: MeetingEvent
 * @date Date : 2019年11月03日 22:39
 */
public class MeetingEvent extends ApplicationEvent {

    /** 项目 */
    private Project project;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MeetingEvent(Object source, Project project) {
        super(source);
        this.project = project;
    }

    /**
     * Getter method for property <tt>project</tt>.
     *
     * @return property value of project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Setter method for property <tt>project</tt>.
     *
     * @param project value to be assigned to property  project
     */
    public void setProject(Project project) {
        this.project = project;
    }
}
