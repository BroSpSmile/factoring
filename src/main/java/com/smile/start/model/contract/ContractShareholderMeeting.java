package com.smile.start.model.contract;

import java.io.Serializable;
import java.util.Date;

/**
 * 已方股东会决议
 * @author Joseph
 * @version v1.0 2019/3/23 15:53, ContractShareholderMeeting.java
 * @since 1.8
 */
public class ContractShareholderMeeting implements Serializable {
    private static final long serialVersionUID = 5436188465562822324L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 合同流水
     */
    private String contractSerialNo;

    /**
     * 会议时间
     */
    private Date mettingTime;

    /**
     * 会议地点
     */
    private String mettingAddress;

    /**
     * 乙方公司名称
     */
    private String spCompanyName;

    /**
     * 出席股东
     */
    private String attendingShareholders;

    /**
     * 会议次数
     */
    private Integer meetingNumber;

    /**
     * 表决权通过率
     */
    private String passingRate;

    /**
     * 签字日期
     */
    private Date signatureDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getContractSerialNo() {
        return contractSerialNo;
    }

    public void setContractSerialNo(String contractSerialNo) {
        this.contractSerialNo = contractSerialNo;
    }

    public Date getMettingTime() {
        return mettingTime;
    }

    public void setMettingTime(Date mettingTime) {
        this.mettingTime = mettingTime;
    }

    public String getMettingAddress() {
        return mettingAddress;
    }

    public void setMettingAddress(String mettingAddress) {
        this.mettingAddress = mettingAddress;
    }

    public String getSpCompanyName() {
        return spCompanyName;
    }

    public void setSpCompanyName(String spCompanyName) {
        this.spCompanyName = spCompanyName;
    }

    public String getAttendingShareholders() {
        return attendingShareholders;
    }

    public void setAttendingShareholders(String attendingShareholders) {
        this.attendingShareholders = attendingShareholders;
    }

    public Integer getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(Integer meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public String getPassingRate() {
        return passingRate;
    }

    public void setPassingRate(String passingRate) {
        this.passingRate = passingRate;
    }

    public Date getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(Date signatureDate) {
        this.signatureDate = signatureDate;
    }
}
