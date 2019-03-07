/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.meeting;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.smile.start.model.enums.MeetingKind;
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

    /** 会议类型 */
    private MeetingKind       kind;

    /** 会议主题 */
    private String            theme;

    /** 开始时间 */
    private Date              beginTime;

    /** 结束时间 */
    private Date              endTime;

    /** 会议类别 */
    private List<String>      type;

    /** 发起者ID */
    private Long              launchId;

    /** 参与者ID */
    private Long              partakeId;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (status != null ? "status\":\"" + status + "\", \"" : "") + (kind != null ? "kind\":\"" + kind + "\", \"" : "")
               + (theme != null ? "theme\":\"" + theme + "\", \"" : "") + (beginTime != null ? "beginTime\":\"" + beginTime + "\", \"" : "")
               + (endTime != null ? "endTime\":\"" + endTime + "\", \"" : "") + (type != null ? "type\":\"" + type + "\", \"" : "")
               + (launchId != null ? "launchId\":\"" + launchId + "\", \"" : "") + (partakeId != null ? "partakeId\":\"" + partakeId : "") + "\"}  ";
    }

    /**
     * Getter method for property <tt>kind</tt>.
     * 
     * @return property value of kind
     */
    public MeetingKind getKind() {
        return kind;
    }

    /**
     * Setter method for property <tt>kind</tt>.
     * 
     * @param kind value to be assigned to property kind
     */
    public void setKind(MeetingKind kind) {
        this.kind = kind;
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

    /**
     * Getter method for property <tt>launchId</tt>.
     * 
     * @return property value of launchId
     */
    public Long getLaunchId() {
        return launchId;
    }

    /**
     * Setter method for property <tt>launchId</tt>.
     * 
     * @param launchId value to be assigned to property launchId
     */
    public void setLaunchId(Long launchId) {
        this.launchId = launchId;
    }

    /**
     * Getter method for property <tt>partakeId</tt>.
     * 
     * @return property value of partakeId
     */
    public Long getPartakeId() {
        return partakeId;
    }

    /**
     * Setter method for property <tt>partakeId</tt>.
     * 
     * @param partakeId value to be assigned to property partakeId
     */
    public void setPartakeId(Long partakeId) {
        this.partakeId = partakeId;
    }

}
