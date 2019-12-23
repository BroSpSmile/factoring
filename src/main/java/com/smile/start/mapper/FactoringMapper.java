/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.mapper;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.smile.start.model.project.FactoringExcelInfo;
import com.smile.start.model.project.Project;

/**
 * 
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FactoringMapper
 * @date Date : 2019年12月23日 16:10
 */
public class FactoringMapper {

    private static NumberFormat format = NumberFormat.getInstance();

    /**
     * mapper
     * @param project
     * @return
     */
    public static final FactoringExcelInfo mapper(Project project) {
        FactoringExcelInfo info = new FactoringExcelInfo();
        info.setProjectId(project.getProjectId());
        info.setProjectName(project.getProjectName());
        info.setAssignee(format.format(project.getDetail().getAssignee()));
        info.setBaseContract(project.getDetail().getBaseContract());
        info.setCreditor(project.getDetail().getCreditor());
        info.setDebtor(project.getDetail().getCreditor());
        info.setDropAmount(format.format(project.getDetail().getDropAmount()));
        info.setDuration(project.getDetail().getDuration());
        info.setModel(project.getModel().getDesc());
        info.setReceivable(format.format(project.getDetail().getReceivable()));
        info.setRemark(project.getDetail().getRemark());
        info.setRemittanceDay(project.getDetail().getRemittanceDay());
        info.setReturnRate(format.format(project.getDetail().getReturnRate()));
        info.setSignDate(project.getDetail().getSignDate());
        info.setTotalFactoringFee(format.format(project.getDetail().getTotalFactoringFee()));
        return info;
    }

    /**
     * mapper
     * @param projects
     * @return
     */
    public static final List<FactoringExcelInfo> mapper(List<Project> projects) {
        if (null == projects || projects.isEmpty()) {
            return Collections.emptyList();
        }
        List<FactoringExcelInfo> excels = Lists.newArrayListWithCapacity(projects.size());
        projects.forEach(project -> {
            excels.add(mapper(project));
        });
        return excels;
    }
}
