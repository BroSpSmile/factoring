/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.meeting;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 会议扩展对象
 * @author smile.jing
 * @version $Id: MeetingExt.java, v 0.1 Feb 3, 2019 9:51:17 PM smile.jing Exp $
 */
public class MeetingExt extends Meeting {

    /**  */
    private static final long serialVersionUID = 302690389666800571L;

    /** 参与人员工号 */
    private List<String>      participantNo;

    /** 参与人员工号集合 */
    private String            participantNoList;

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"" + (participantNo != null ? "participantNo\":\"" + participantNo + "\", \"" : "")
               + (participantNoList != null ? "participantNoList\":\"" + participantNoList : "") + "\"}  ";
    }

    /**
     * 转换数组集合
     */
    public void toParticipantNoList() {
        participantNoList = StringUtils.EMPTY;
        participantNo.forEach(e -> participantNoList += e + ",");
        if (participantNoList.endsWith(",")) {
            participantNoList = participantNoList.substring(0, participantNoList.length() - 1);
        }
    }

    /**
     * Getter method for property <tt>participantNo</tt>.
     * 
     * @return property value of participantNo
     */
    public List<String> getParticipantNo() {
        return participantNo;
    }

    /**
     * Setter method for property <tt>participantNo</tt>.
     * 
     * @param participantNo value to be assigned to property participantNo
     */
    public void setParticipantNo(List<String> participantNo) {
        this.participantNo = participantNo;
    }

    /**
     * Getter method for property <tt>participantNoList</tt>.
     * 
     * @return property value of participantNoList
     */
    public String getParticipantNoList() {
        return participantNoList;
    }

    /**
     * Setter method for property <tt>participantNoList</tt>.
     * 
     * @param participantNoList value to be assigned to property participantNoList
     */
    public void setParticipantNoList(String participantNoList) {
        this.participantNoList = participantNoList;
    }

}
