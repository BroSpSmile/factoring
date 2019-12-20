/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import java.io.Serializable;

import com.smile.start.model.enums.project.ProjectModel;

/**
 * 项目合同
 * 
 * @author smile.jing
 * @version $Id: Contract.java, v 0.1 Jan 8, 2019 10:00:42 PM smile.jing Exp $
 */
public class Contract implements Serializable {

    /** UID */
    private static final long serialVersionUID = 1148996664369269108L;

    /** ID */
    private long              id;

    /** 项目模式 */
    private ProjectModel      model;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\", \"model\":\"" + model + "\"}  ";
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

}
