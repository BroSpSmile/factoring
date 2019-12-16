/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.mapper;

import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.Project;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: ProjectMapper
 * @date Date : 2019年10月20日 00:06
 */
public class ProjectMapper {
    /**
     * 私有构造函数
     */
    private ProjectMapper() {

    }

    /**
     * mapper
     * @param base baseProject
     * @return
     */
    public static <T> Project mapper(BaseProject<T> base) {
        Project project = new Project();
        project.setId(base.getId());
        project.setKind(base.getKind());
        project.setStep(2);//转换主项目状态默认2尽调审核
        project.setUser(base.getOperator());
        project.setProjectName(base.getProjectName());
        project.setProjectId(base.getProjectId());
        project.setCreateTime(base.getCreateTime());
        return project;
    }

    /**
     * mapper
     * @param project 项目
     * @param clazz clazz
     * @param <T> 泛型
     * @return BaseProject
     */
    public static <T> BaseProject<T> mapper(Project project, Class<T> clazz) {
        BaseProject<T> base = new BaseProject<T>();
        base.setId(project.getId());
        base.setProjectId(project.getProjectId());
        base.setKind(project.getKind());
        if (null != project.getUser()) {
            base.setOperator(project.getUser());
        }
        return base;
    }
}
