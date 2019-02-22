/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;

/**
 * 
 * @author smile.jing
 * @version $Id: ProjectMeeting.java, v 0.1 Feb 18, 2019 10:52:49 PM smile.jing Exp $
 */
public class ProjectMeeting implements Serializable {

    /** UID */
    private static final long serialVersionUID = -5364358986204242729L;

    /** 编号 */
    private long              id;

    /** 项目编号 */
    private long              projectId;

    /** 会议编号 */
    private long              meetingId;

    /**
     * 
     */
    public ProjectMeeting() {
        super();
    }

    /**
     * @param projectId
     * @param meetingId
     */
    public ProjectMeeting(long projectId, long meetingId) {
        super();
        this.projectId = projectId;
        this.meetingId = meetingId;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\", \"projectId\":\"" + projectId + "\", \"meetingId\":\"" + meetingId + "\"}  ";
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>projectId</tt>.
     * 
     * @return property value of projectId
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Setter method for property <tt>projectId</tt>.
     * 
     * @param projectId value to be assigned to property projectId
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Getter method for property <tt>meetingId</tt>.
     * 
     * @return property value of meetingId
     */
    public long getMeetingId() {
        return meetingId;
    }

    /**
     * Setter method for property <tt>meetingId</tt>.
     * 
     * @param meetingId value to be assigned to property meetingId
     */
    public void setMeetingId(long meetingId) {
        this.meetingId = meetingId;
    }

}
