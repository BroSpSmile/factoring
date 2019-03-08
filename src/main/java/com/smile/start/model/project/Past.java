/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author smile.jing
 * @version $Id: Past.java, v 0.1 Feb 17, 2019 10:29:57 PM smile.jing Exp $
 */
public class Past implements Serializable {

    /** UID */
    private static final long serialVersionUID = -2541137761260519578L;

    /** 项目ID */
    private Long              projectId;

    /** 会议列表 */
    private List<Long>        meetingIds;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (projectId != null ? "projectId\":\"" + projectId + "\", \"" : "") + (meetingIds != null ? "meetingIds\":\"" + meetingIds : "") + "\"}  ";
    }

    /**
     * Getter method for property <tt>projectId</tt>.
     * 
     * @return property value of projectId
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Setter method for property <tt>projectId</tt>.
     * 
     * @param projectId value to be assigned to property projectId
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Getter method for property <tt>meetingIds</tt>.
     * 
     * @return property value of meetingIds
     */
    public List<Long> getMeetingIds() {
        return meetingIds;
    }

    /**
     * Setter method for property <tt>meetingIds</tt>.
     * 
     * @param meetingIds value to be assigned to property meetingIds
     */
    public void setMeetingIds(List<Long> meetingIds) {
        this.meetingIds = meetingIds;
    }

}
