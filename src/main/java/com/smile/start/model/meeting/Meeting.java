/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.meeting;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.smile.start.model.auth.User;
import com.smile.start.model.enums.MeetingKind;
import com.smile.start.model.enums.MeetingStatus;
import com.smile.start.model.enums.MinutesKind;
import com.smile.start.model.project.Project;

/**
 * 
 * @author smile.jing
 * @version $Id: Meeting.java, v 0.1 Jan 8, 2019 10:14:48 PM smile.jing Exp $
 */
public class Meeting implements Serializable {

    /** UID */
    private static final long serialVersionUID = -2282254743544937776L;

    /** ID */
    private long              id;

    /** 会议类型 */
    private MeetingKind       kind;

    /** 会议状态 */
    private MeetingStatus     status;

    /** 会议主题 */
    private String            theme;

    /** 会议开始时间 */
    private Date              beginTime;

    /** 会议结束时间 */
    private Date              endTime;

    /** 会议地点 */
    private String            place;

    /** 会议内容 */
    private String            content;

    /** 提醒时间 */
    private int               remind;

    /** 关联项目 */
    private List<Project>     projects;

    /** 会议材料 */
    private String            material;

    /** 会议纪要 */
    private String            minutes;

    /** 发起人 */
    private User              originator;

    /** 参与人员 */
    private List<User>        participant;

    /** 会议纪要模板 */
    private MinutesKind       minutesKind;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\", \"" + (kind != null ? "kind\":\"" + kind + "\", \"" : "") + (status != null ? "status\":\"" + status + "\", \"" : "")
               + (theme != null ? "theme\":\"" + theme + "\", \"" : "") + (beginTime != null ? "beginTime\":\"" + beginTime + "\", \"" : "")
               + (endTime != null ? "endTime\":\"" + endTime + "\", \"" : "") + (place != null ? "place\":\"" + place + "\", \"" : "")
               + (content != null ? "content\":\"" + content + "\", \"" : "") + "remind\":\"" + remind + "\", \"" + (projects != null ? "projects\":\"" + projects + "\", \"" : "")
               + (minutes != null ? "minutes\":\"" + minutes + "\", \"" : "") + (originator != null ? "originator\":\"" + originator + "\", \"" : "")
               + (participant != null ? "participant\":\"" + participant + "\", \"" : "") + (minutesKind != null ? "minutesKind\":\"" + minutesKind : "") + "\"}  ";
    }

    /**
     * 增加参会人员
     * @param user
     * @return
     */
    public boolean addParticipant(User user) {
        if (participant == null) {
            participant = Lists.newArrayList();
        }
        return participant.add(user);
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
     * Getter method for property <tt>place</tt>.
     * 
     * @return property value of place
     */
    public String getPlace() {
        return place;
    }

    /**
     * Setter method for property <tt>place</tt>.
     * 
     * @param place value to be assigned to property place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Getter method for property <tt>content</tt>.
     * 
     * @return property value of content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter method for property <tt>content</tt>.
     * 
     * @param content value to be assigned to property content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter method for property <tt>remind</tt>.
     * 
     * @return property value of remind
     */
    public int getRemind() {
        return remind;
    }

    /**
     * Setter method for property <tt>remind</tt>.
     * 
     * @param remind value to be assigned to property remind
     */
    public void setRemind(int remind) {
        this.remind = remind;
    }

    /**
     * Getter method for property <tt>projects</tt>.
     * 
     * @return property value of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Setter method for property <tt>projects</tt>.
     * 
     * @param projects value to be assigned to property projects
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Getter method for property <tt>minutes</tt>.
     * 
     * @return property value of minutes
     */
    public String getMinutes() {
        return minutes;
    }

    /**
     * Setter method for property <tt>minutes</tt>.
     * 
     * @param minutes value to be assigned to property minutes
     */
    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    /**
     * Getter method for property <tt>originator</tt>.
     * 
     * @return property value of originator
     */
    public User getOriginator() {
        return originator;
    }

    /**
     * Setter method for property <tt>originator</tt>.
     * 
     * @param originator value to be assigned to property originator
     */
    public void setOriginator(User originator) {
        this.originator = originator;
    }

    /**
     * Getter method for property <tt>participant</tt>.
     * 
     * @return property value of participant
     */
    public List<User> getParticipant() {
        return participant;
    }

    /**
     * Setter method for property <tt>participant</tt>.
     * 
     * @param participant value to be assigned to property participant
     */
    public void setParticipant(List<User> participant) {
        this.participant = participant;
    }

    /**
     * Getter method for property <tt>minutesKind</tt>.
     * 
     * @return property value of minutesKind
     */
    public MinutesKind getMinutesKind() {
        return minutesKind;
    }

    /**
     * Setter method for property <tt>minutesKind</tt>.
     * 
     * @param minutesKind value to be assigned to property minutesKind
     */
    public void setMinutesKind(MinutesKind minutesKind) {
        this.minutesKind = minutesKind;
    }

    /**
     * Getter method for property <tt>material</tt>.
     *
     * @return property value of material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Setter method for property <tt>material</tt>.
     *
     * @param material value to be assigned to property  material
     */
    public void setMaterial(String material) {
        this.material = material;
    }
}
