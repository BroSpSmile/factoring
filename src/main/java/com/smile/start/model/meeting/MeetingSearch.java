/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.meeting;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.smile.start.model.enums.MeetingStatus;

/**
 * 会议搜索对象
 * @author smile.jing
 * @version $Id: MeetingSearch.java, v 0.1 Jan 17, 2019 7:34:17 PM smile.jing Exp $
 */
public class MeetingSearch implements Serializable {

    /** uid */
    private static final long serialVersionUID = 440763405185353667L;

    /** 会议状态 */
    private MeetingStatus     status;

    /** 会议主题 */
    private String            theme;

    /** 开始时间 */
    private Date              beginTime;

    /** 结束时间 */
    private Date              endTime;

    /** 会议类别 */
    private List<String>      type;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"status\":\"" + status + "\", \"theme\":\"" + theme + "\", \"beginTime\":\"" + beginTime + "\", \"endTime\":\"" + endTime + "\", \"type\":\"" + type + "\"}  ";
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public MeetingStatus getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(MeetingStatus status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>theme</tt>.
     * 
     * @return property value of theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Setter method for property <tt>theme</tt>.
     * 
     * @param theme value to be assigned to property theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Getter method for property <tt>beginTime</tt>.
     * 
     * @return property value of beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * Setter method for property <tt>beginTime</tt>.
     * 
     * @param beginTime value to be assigned to property beginTime
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Getter method for property <tt>endTime</tt>.
     * 
     * @return property value of endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     * 
     * @param endTime value to be assigned to property endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Getter method for property <tt>type</tt>.
     * 
     * @return property value of type
     */
    public List<String> getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     * 
     * @param type value to be assigned to property type
     */
    public void setType(List<String> type) {
        this.type = type;
    }

}
