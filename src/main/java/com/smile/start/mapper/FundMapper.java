/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.mapper;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

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
        info.setRegisterTime(fundProject.getDetail().getRegisterTime());
        info.setCompanySortName(fundProject.getDetail().getCompanySortName());
        info.setControllerOwner(fundProject.getDetail().getControllerOwner());
        info.setInvestemntTime(fundProject.getDetail().getInvestemntTime());
        info.setInvestment(format.format(fundProject.getDetail().getInvestment()));
        info.setRegisteredCapital(format.format(fundProject.getDetail().getRegisteredCapital()));
        info.setShareHodingRate(fundProject.getDetail().getShareHodingRate() + "%");
        info.setRegisteredCapital(format.format(fundProject.getDetail().getRegisteredCapital()));
        info.setAddress(fundProject.getDetail().getAddress());
        info.setInvestmentComp(fundProject.getDetail().getInvestmentComp());
        if (null != fundProject.getDetail().getRealInvestment()) {
            info.setRealInvestment(format.format(fundProject.getDetail().getRealInvestment()));
        }
        info.setMainBusiness(fundProject.getDetail().getMainBusiness());
        if (null != fundProject.getDetail().getSharePrice()) {
            info.setSharePrice(format.format(fundProject.getDetail().getSharePrice()));
        }
        if (null != fundProject.getDetail().getShareNum()) {
            info.setShareNum(format.format(fundProject.getDetail().getShareNum()));
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
