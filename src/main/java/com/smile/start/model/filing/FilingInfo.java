package com.smile.start.model.filing;

import com.smile.start.model.project.ProjectItem;

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
public class FilingInfo implements Serializable {
    private static final long serialVersionUID = 371278758555526014L;

    private String applyType;

    private String applicant;

    private Date applyTime;

    private String projectId;

    private String[] filingList;

    /**
     * 归档附件
     */
    private List<FilingFileItem> items;


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
    }

    public List<FilingFileItem> getItems() {
        return items;
    }

    public void setItems(List<FilingFileItem> items) {
        this.items = items;
    }
}
