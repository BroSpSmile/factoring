/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.fund;

import com.smile.start.model.project.Project;

/**
 * 项目意见
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundOpinion
 * @date Date : 2020年01月06日 16:25
 */
public class FundOpinion {

    private Long    id;

    /** 所属项目 */
    private Project project;

    /** 是否迁入相城 */
    private Boolean transfer;

    /** 创始人/团队情况 */
    private String  team;

    /** 所属行业情况 */
    private String  industry;

    /** 核心技术 */
    private String  core;

    /** 盈利模式 */
    private String  profit;

    /** 业务开展情况 */
    private String  biz;

    /** 竞争格局 */
    private String  compete;

    /** 财务状况 */
    private String  finance;

    /** 项目需求 */
    private String  demand;

    /** 项目优势 */
    private String  advantage;

    /** 项目风险 */
    private String  risk;

    /** 初步意见 */
    private String  opinion;

    /** 备注 */
    private String  remark;

    @Override
    public String toString() {
        return "{\"FundOpinion\":{" + "\"id\":" + id + ",\"project\":" + project + ",\"transfer\":" + transfer + ",\"team\":\"" + team + '\"' + ",\"industry\":\"" + industry + '\"'
               + ",\"core\":\"" + core + '\"' + ",\"profit\":\"" + profit + '\"' + ",\"biz\":\"" + biz + '\"' + ",\"compete\":\"" + compete + '\"' + ",\"finance\":\"" + finance
               + '\"' + ",\"demand\":\"" + demand + '\"' + ",\"advantage\":\"" + advantage + '\"' + ",\"risk\":\"" + risk + '\"' + ",\"opinion\":\"" + opinion + '\"'
               + ",\"remark\":\"" + remark + '\"' + "}}";

    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property  id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>project</tt>.
     *
     * @return property value of project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Setter method for property <tt>project</tt>.
     *
     * @param project value to be assigned to property  project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Getter method for property <tt>transfer</tt>.
     *
     * @return property value of transfer
     */
    public Boolean getTransfer() {
        return transfer;
    }

    /**
     * Setter method for property <tt>transfer</tt>.
     *
     * @param transfer value to be assigned to property  transfer
     */
    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    /**
     * Getter method for property <tt>team</tt>.
     *
     * @return property value of team
     */
    public String getTeam() {
        return team;
    }

    /**
     * Setter method for property <tt>team</tt>.
     *
     * @param team value to be assigned to property  team
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * Getter method for property <tt>industry</tt>.
     *
     * @return property value of industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * Setter method for property <tt>industry</tt>.
     *
     * @param industry value to be assigned to property  industry
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * Getter method for property <tt>core</tt>.
     *
     * @return property value of core
     */
    public String getCore() {
        return core;
    }

    /**
     * Setter method for property <tt>core</tt>.
     *
     * @param core value to be assigned to property  core
     */
    public void setCore(String core) {
        this.core = core;
    }

    /**
     * Getter method for property <tt>profit</tt>.
     *
     * @return property value of profit
     */
    public String getProfit() {
        return profit;
    }

    /**
     * Setter method for property <tt>profit</tt>.
     *
     * @param profit value to be assigned to property  profit
     */
    public void setProfit(String profit) {
        this.profit = profit;
    }

    /**
     * Getter method for property <tt>biz</tt>.
     *
     * @return property value of biz
     */
    public String getBiz() {
        return biz;
    }

    /**
     * Setter method for property <tt>biz</tt>.
     *
     * @param biz value to be assigned to property  biz
     */
    public void setBiz(String biz) {
        this.biz = biz;
    }

    /**
     * Getter method for property <tt>compete</tt>.
     *
     * @return property value of compete
     */
    public String getCompete() {
        return compete;
    }

    /**
     * Setter method for property <tt>compete</tt>.
     *
     * @param compete value to be assigned to property  compete
     */
    public void setCompete(String compete) {
        this.compete = compete;
    }

    /**
     * Getter method for property <tt>finance</tt>.
     *
     * @return property value of finance
     */
    public String getFinance() {
        return finance;
    }

    /**
     * Setter method for property <tt>finance</tt>.
     *
     * @param finance value to be assigned to property  finance
     */
    public void setFinance(String finance) {
        this.finance = finance;
    }

    /**
     * Getter method for property <tt>demand</tt>.
     *
     * @return property value of demand
     */
    public String getDemand() {
        return demand;
    }

    /**
     * Setter method for property <tt>demand</tt>.
     *
     * @param demand value to be assigned to property  demand
     */
    public void setDemand(String demand) {
        this.demand = demand;
    }

    /**
     * Getter method for property <tt>advantage</tt>.
     *
     * @return property value of advantage
     */
    public String getAdvantage() {
        return advantage;
    }

    /**
     * Setter method for property <tt>advantage</tt>.
     *
     * @param advantage value to be assigned to property  advantage
     */
    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    /**
     * Getter method for property <tt>risk</tt>.
     *
     * @return property value of risk
     */
    public String getRisk() {
        return risk;
    }

    /**
     * Setter method for property <tt>risk</tt>.
     *
     * @param risk value to be assigned to property  risk
     */
    public void setRisk(String risk) {
        this.risk = risk;
    }

    /**
     * Getter method for property <tt>opinion</tt>.
     *
     * @return property value of opinion
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * Setter method for property <tt>opinion</tt>.
     *
     * @param opinion value to be assigned to property  opinion
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    /**
     * Getter method for property <tt>remark</tt>.
     *
     * @return property value of remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Setter method for property <tt>remark</tt>.
     *
     * @param remark value to be assigned to property  remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
