/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;
import java.util.List;

import com.smile.start.model.enums.Progress;
import com.smile.start.model.enums.ProjectKind;
import com.smile.start.model.enums.ProjectModel;

/**
 * 项目
 * 
 * @author smile.jing
 * @version $Id: Project.java, v 0.1 Jan 8, 2019 9:44:16 PM smile.jing Exp $
 */
public class Project implements Serializable {

    /** UID */
    private static final long serialVersionUID = -530319698748537765L;

    /** ID */
    private long              id;

    /** 项目编号 */
    private String            projectId;

    /** 项目类型 */
    private ProjectKind       kind;

    /** 项目名称 */
    private String            projectName;

    /** 项目发起人 */
    private String            person;

    /** 当前进度 */
    private Progress          progress;

    /** 项目模式 */
    private ProjectModel      model;

    /** 项目附件 */
    private List<ProjectItem> items;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\", \"projectId\":\"" + projectId + "\", \"kind\":\"" + kind + "\", \"projectName\":\"" + projectName + "\", \"person\":\"" + person
               + "\", \"progress\":\"" + progress + "\", \"model\":\"" + model + "\"}  ";
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
    public String getProjectId() {
        return projectId;
    }

    /**
     * Setter method for property <tt>projectId</tt>.
     * 
     * @param projectId value to be assigned to property projectId
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Getter method for property <tt>kind</tt>.
     * 
     * @return property value of kind
     */
    public ProjectKind getKind() {
        return kind;
    }

    /**
     * Setter method for property <tt>kind</tt>.
     * 
     * @param kind value to be assigned to property kind
     */
    public void setKind(ProjectKind kind) {
        this.kind = kind;
    }

    /**
     * Getter method for property <tt>projectName</tt>.
     * 
     * @return property value of projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Setter method for property <tt>projectName</tt>.
     * 
     * @param projectName value to be assigned to property projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Getter method for property <tt>person</tt>.
     * 
     * @return property value of person
     */
    public String getPerson() {
        return person;
    }

    /**
     * Setter method for property <tt>person</tt>.
     * 
     * @param person value to be assigned to property person
     */
    public void setPerson(String person) {
        this.person = person;
    }

    /**
     * Getter method for property <tt>progress</tt>.
     * 
     * @return property value of progress
     */
    public Progress getProgress() {
        return progress;
    }

    /**
     * Setter method for property <tt>progress</tt>.
     * 
     * @param progress value to be assigned to property progress
     */
    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    /**
     * Getter method for property <tt>model</tt>.
     * 
     * @return property value of model
     */
    public ProjectModel getModel() {
        return model;
    }

    /**
     * Setter method for property <tt>model</tt>.
     * 
     * @param model value to be assigned to property model
     */
    public void setModel(ProjectModel model) {
        this.model = model;
    }

    /**
     * Getter method for property <tt>items</tt>.
     * 
     * @return property value of items
     */
    public List<ProjectItem> getItems() {
        return items;
    }

    /**
     * Setter method for property <tt>items</tt>.
     * 
     * @param items value to be assigned to property items
     */
    public void setItems(List<ProjectItem> items) {
        this.items = items;
    }

}
