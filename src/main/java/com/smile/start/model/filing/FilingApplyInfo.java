package com.smile.start.model.filing;

import com.smile.start.model.enums.FilingSubProgress;
import com.smile.start.model.project.Audit;
import com.smile.start.model.project.AuditRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
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

    private String applyTime;

    private long project;

    private String projectId;

    private String projectName;

    private String[] filingList;

    private String filingListStr;

    private FilingSubProgress progress;

    /**
     * 归档附件
     */
    private List<FilingFileItem> items;

    /**
     * 当前审批信息
     */
    private AuditRecord record;

//    private Integer rejectStep;

    /**
     * 审核对象
     */
    private Audit audit;

    public AuditRecord getRecord() {
        return record;
    }

    public void setRecord(AuditRecord record) {
        this.record = record;
    }

//    public Integer getRejectStep() {
//        return rejectStep;
//    }
//
//    public void setRejectStep(Integer rejectStep) {
//        this.rejectStep = rejectStep;
//    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public long getProject() {
        return project;
    }

    public void setProject(long project) {
        this.project = project;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public FilingSubProgress getProgress() {
        return progress;
    }

    public void setProgress(FilingSubProgress progress) {
        this.progress = progress;
    }

    public String getFilingListStr() {
        return filingListStr;
    }

    public void setFilingListStr(String filingListStr) {
        if (null != filingList && filingList.length != 0) {
            this.filingListStr = StringUtils.join(filingList, ",");
        } else {
            this.filingList = filingListStr.split(",");
            this.filingListStr = filingListStr;
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\", \"applyType\":\"" + applyType + "\", \"applicant\":\"" + applicant +
                "\", \"applyTime\":\"" + applyTime + "\", \"project\":\"" + project + "\", \"filingList\":\"" +
                filingList + "\", \"filingListStr\":\"" + filingListStr + "\", \"progress\":\"" + progress +
                "\", \"items\":\"" + items
                + "\"}  ";
    }
}
