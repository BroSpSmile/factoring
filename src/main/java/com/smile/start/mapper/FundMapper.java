/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.mapper;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

import com.smile.start.model.auth.User;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.smile.start.model.fund.FundExcelInfo;
import com.smile.start.model.fund.FundProject;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundMapper
 * @date Date : 2019年12月23日 14:43
 */
public class FundMapper {

    private static NumberFormat format = NumberFormat.getInstance();

    /**
     * mapper
     * @param fundProject
     * @return
     */
    public static final FundExcelInfo mapper(FundProject fundProject) {
        FundExcelInfo info = new FundExcelInfo();
        info.setProjectId(fundProject.getProjectId());
        info.setProjectName(fundProject.getProjectName());
        info.setCreateTime(fundProject.getCreateTime());
        info.setRegisterTime(fundProject.getDetail().getRegisterTime());
        info.setCompanyFullName(fundProject.getDetail().getCompanyFullName());
        info.setCompanySortName(fundProject.getDetail().getCompanySortName());
        info.setControllerOwner(fundProject.getDetail().getControllerOwner());
        info.setInvestemntTime(fundProject.getDetail().getInvestemntTime());
        info.setInvestment(format.format(fundProject.getDetail().getInvestment()));
        info.setInvestmentPart(fundProject.getDetail().getInvestmentPart());
        info.setPostVal(format.format(fundProject.getDetail().getPostVal()));
        info.setPreVal(format.format(fundProject.getDetail().getPreVal()));
        info.setProjectChannel(fundProject.getDetail().getProjectChannel());
        info.setProjectStep(fundProject.getDetail().getProjectStep().getDesc());
        info.setRegisteredCapital(format.format(fundProject.getDetail().getRegisteredCapital()));
        info.setShareHodingRate(fundProject.getDetail().getShareHodingRate() + "%");
        if (null != fundProject.getDetail().getMemberA() && StringUtils.isNotBlank(fundProject.getDetail().getMemberA().getUsername())) {
            info.setMembers(fundProject.getDetail().getMemberA().getUsername());
        }

        if (null != fundProject.getDetail().getMemberBs() && !fundProject.getDetail().getMemberBs().isEmpty()) {
            for (User user : fundProject.getDetail().getMemberBs()) {
                if (StringUtils.isNotBlank(info.getMembers())) {
                    info.setMembers(info.getMembers() + user.getUsername());
                } else {
                    info.setMembers(user.getUsername());
                }
            }

        }
        return info;
    }

    /**
     * mapper
     * @param projects
     * @return
     */
    public static final List<FundExcelInfo> mapper(List<FundProject> projects) {
        if (null == projects || projects.isEmpty()) {
            return Collections.emptyList();
        }
        List<FundExcelInfo> excels = Lists.newArrayListWithCapacity(projects.size());
        projects.forEach(project -> {
            excels.add(mapper(project));
        });
        return excels;
    }
}
