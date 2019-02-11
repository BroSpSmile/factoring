package com.smile.start.model.filing;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：xioutman
 * @description：
 * @date ：Created in 2019/2/8 15:49
 * @modified By：
 * @version: $
 */
public class FilingApplyInfo implements Serializable {
    private static final long serialVersionUID = 371278758555526014L;

    private Integer id;

    private String applyType;

    private String applicant;

    private Date applyTime;

    private String projectId;

    private String[] filingList;

    private String filingListStr;

    /**
     * 归档进度
     * 0:待归档；1：提出申请；2：法务风控审核；3:归档完成
     */
    private String progress = "0";

    /**
     * 归档附件
     */
    private List<FilingFileItem> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String[] getFilingList() {
        return filingList;
    }

    public void setFilingList(String[] filingList) {
        this.filingList = filingList;
        if (null != filingList && filingList.length != 0) {
            this.filingListStr = StringUtils.join(filingList, ",");
        } else {
            this.filingListStr = "";
        }
    }

    public List<FilingFileItem> getItems() {
        return items;
    }

    public void setItems(List<FilingFileItem> items) {
        this.items = items;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getFilingListStr() {
        return filingListStr;
    }

    public void setFilingListStr(String filingListStr) {
        this.filingListStr = filingListStr;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\", \"applyType\":\"" + applyType + "\", \"applicant\":\"" + applicant +
            "\", \"applyTime\":\"" + applyTime + "\", \"projectId\":\"" + projectId + "\", \"filingList\":\"" +
            filingList + "\", \"filingListStr\":\"" + filingListStr + "\", \"progress\":\"" + progress +
            "\", \"items\":\"" + items
            + "\"}  ";
    }
}
