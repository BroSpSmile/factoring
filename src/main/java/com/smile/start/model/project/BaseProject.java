/**
 * VIP.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.util.Date;
import java.util.List;

import com.smile.start.model.auth.User;
import com.smile.start.model.enums.project.ProjectKind;

/**
 * 抽象项目
 *
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: BaseProject
 * @date Date : 2019年10月03日 20:18
 */
public class BaseProject<T> {

    /** ID */
    private long              id;

    /** 项目编号 */
    private String            projectId;

    /** 项目类型 */
    private ProjectKind       kind;

    /** 项目名称 */
    private String            projectName;

    /** 项目附件 */
    private List<ProjectItem> items;

    /** 项目发起人 */
    private User              operator;

    /** 创建时间 */
    private Date              createTime;

    /** 项目明细 */
    private T                 detail;

    /**
     * @return
     */
    @Override
    public String toString() {
        return "{\"BaseProject\":{" + "\"id\":" + id + ",\"projectId\":\"" + projectId + '\"' + ",\"kind\":" + kind + ",\"projectName\":\"" + projectName + '\"' + ",\"items\":"
               + items + ",\"user\":" + operator + ",\"createTime\":\"" + createTime + '\"' + ",\"detail\":" + detail.toString() + "}}";

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

    /**
     * Getter method for property <tt>operator</tt>.
     * 
     * @return property value of operator
     */
    public User getOperator() {
        return operator;
    }

    /**
     * Setter method for property <tt>operator</tt>.
     * 
     * @param operator value to be assigned to property operator
     */
    public void setOperator(User operator) {
        this.operator = operator;
    }

    /**
     * Getter method for property <tt>createTime</tt>.
     * 
     * @return property value of createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Setter method for property <tt>createTime</tt>.
     * 
     * @param createTime value to be assigned to property createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter method for property <tt>detail</tt>.
     * 
     * @return property value of detail
     */
    public T getDetail() {
        return detail;
    }

    /**
     * Setter method for property <tt>detail</tt>.
     * 
     * @param detail value to be assigned to property detail
     */
    public void setDetail(T detail) {
        this.detail = detail;
    }

}
