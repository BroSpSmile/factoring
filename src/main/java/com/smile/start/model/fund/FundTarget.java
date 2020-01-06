/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.fund;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.smile.start.model.auth.User;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.enums.fund.FundType;
import com.smile.start.model.enums.fund.OutTypes;
import com.smile.start.model.project.Installment;

/**
 * 直投投资标的
 *
 * @author smile.jing
 * @version $Id: FundTarget.java, v 0.1 2019年8月10日 下午5:33:25 smile.jing Exp $
 */
public class FundTarget implements Serializable {

    /** UID */
    private static final long serialVersionUID = -4385779759021644514L;

    /** */
    private Long              id;

    /** 项目编号 */
    private String            projectId;

    /** 项目名称 */
    private String            projectName;

    /** 项目类型 */
    private FundType          fundType;

    /** 项目进度 */
    private FundStatus        projectStep;

    /** A角 */
    private User              memberA;

    /** B角 */
    private List<User>        memberBs;

    /** B角Str */
    private String            memberBStr;

    /** 投资区域 */
    private String            bound;

    /** 出资公司 */
    private String            investmentComp;

    /** 投资金额 */
    private Double            investment;

    /** 实缴出资 */
    private Double            realInvestment;

    /** 投资主体 */
    private String            investmentPart;

    /** 持股占比 */
    private String            shareHodingRate;

    /** 股价 */
    private Double            sharePrice;

    /** 股份数(万股) */
    private Double            shareNum;

    /** 投前估值 */
    private Double            preVal;

    /** 投后估值 */
    private Double            postVal;

    /** 投资时间 */
    private Date              investemntTime;

    /** 项目来源 */
    private String            projectChannel;

    /** 公司简称 */
    private String            companySortName;

    /** 公司全称 */
    private String            companyFullName;

    /** 实际控制人 */
    private String            controllerOwner;

    /** 注册资本 */
    private Double            registeredCapital;

    /** 实收资本 */
    private Double            paidCapital;

    /** 注册时间 */
    private String            registerTime;

    /** 公司地址 */
    private String            address;

    /** 所属行业*/
    private String            industry;

    /** 所在地 */
    private String            location;

    /** 主营业务 */
    private String            mainBusiness;

    /** 组织机构代码 */
    private String            orgNumber;

    /** 统一社会信用代码 */
    private String            creditCode;

    /** 更新时间 */
    private String            updatetime;

    /** 纳税人识别号 */
    private String            taxNumber;

    /** 联系电话 */
    private String            phoneNumber;

    /** 退出类型 */
    private OutTypes          outType;

    /** 放款分期信息 */
    private List<Installment> loanInstallments = Lists.newArrayList();

    @Override
    public String toString() {
        return "{\"FundTarget\":{" + "\"id\":" + id + ",\"projectId\":\"" + projectId + '\"' + ",\"projectName\":\"" + projectName + '\"' + ",\"projectStep\":" + projectStep
               + ",\"memberA\":" + memberA + ",\"memberBs\":" + memberBs + ",\"investment\":" + investment + ",\"investmentPart\":\"" + investmentPart + '\"'
               + ",\"shareHodingRate\":\"" + shareHodingRate + '\"' + ",\"preVal\":" + preVal + ",\"postVal\":" + postVal + ",\"investemntTime\":\"" + investemntTime + '\"'
               + ",\"projectChannel\":\"" + projectChannel + '\"' + ",\"companySortName\":\"" + companySortName + '\"' + ",\"companyFullName\":\"" + companyFullName + '\"'
               + ",\"controllerOwner\":\"" + controllerOwner + '\"' + ",\"registeredCapital\":" + registeredCapital + ",\"registerTime\":\"" + registerTime + '\"'
               + ",\"address\":\"" + address + '\"' + ",\"industry\":\"" + industry + '\"' + ",\"location\":\"" + location + '\"' + ",\"mainBusiness\":\"" + mainBusiness + '\"'
               + ",\"orgNumber\":\"" + orgNumber + '\"' + ",\"creditCode\":\"" + creditCode + '\"' + ",\"updatetime\":\"" + updatetime + '\"' + ",\"taxNumber\":\"" + taxNumber
               + '\"' + ",\"phoneNumber\":\"" + phoneNumber + '\"' + ",\"outType\":" + outType + ",\"loanInstallments\":" + loanInstallments + "}}";

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
     * @param projectId value to be assigned to property  projectId
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
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
     * @param projectName value to be assigned to property  projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Getter method for property <tt>projectStep</tt>.
     *
     * @return property value of projectStep
     */
    public FundStatus getProjectStep() {
        return projectStep;
    }

    /**
     * Setter method for property <tt>projectStep</tt>.
     *
     * @param projectStep value to be assigned to property  projectStep
     */
    public void setProjectStep(FundStatus projectStep) {
        this.projectStep = projectStep;
    }

    /**
     * Getter method for property <tt>memberA</tt>.
     *
     * @return property value of memberA
     */
    public User getMemberA() {
        return memberA;
    }

    /**
     * Setter method for property <tt>memberA</tt>.
     *
     * @param memberA value to be assigned to property  memberA
     */
    public void setMemberA(User memberA) {
        this.memberA = memberA;
    }

    /**
     * Getter method for property <tt>memberBs</tt>.
     *
     * @return property value of memberBs
     */
    public List<User> getMemberBs() {
        return memberBs;
    }

    /**
     * Setter method for property <tt>memberBs</tt>.
     *
     * @param memberBs value to be assigned to property  memberBs
     */
    public void setMemberBs(List<User> memberBs) {
        this.memberBs = memberBs;
    }

    /**
     * Getter method for property <tt>investment</tt>.
     *
     * @return property value of investment
     */
    public Double getInvestment() {
        return investment;
    }

    /**
     * Setter method for property <tt>investment</tt>.
     *
     * @param investment value to be assigned to property  investment
     */
    public void setInvestment(Double investment) {
        this.investment = investment;
    }

    /**
     * Getter method for property <tt>investmentPart</tt>.
     *
     * @return property value of investmentPart
     */
    public String getInvestmentPart() {
        return investmentPart;
    }

    /**
     * Setter method for property <tt>investmentPart</tt>.
     *
     * @param investmentPart value to be assigned to property  investmentPart
     */
    public void setInvestmentPart(String investmentPart) {
        this.investmentPart = investmentPart;
    }

    /**
     * Getter method for property <tt>shareHodingRate</tt>.
     *
     * @return property value of shareHodingRate
     */
    public String getShareHodingRate() {
        return shareHodingRate;
    }

    /**
     * Setter method for property <tt>shareHodingRate</tt>.
     *
     * @param shareHodingRate value to be assigned to property  shareHodingRate
     */
    public void setShareHodingRate(String shareHodingRate) {
        this.shareHodingRate = shareHodingRate;
    }

    /**
     * Getter method for property <tt>preVal</tt>.
     *
     * @return property value of preVal
     */
    public Double getPreVal() {
        return preVal;
    }

    /**
     * Setter method for property <tt>preVal</tt>.
     *
     * @param preVal value to be assigned to property  preVal
     */
    public void setPreVal(Double preVal) {
        this.preVal = preVal;
    }

    /**
     * Getter method for property <tt>postVal</tt>.
     *
     * @return property value of postVal
     */
    public Double getPostVal() {
        return postVal;
    }

    /**
     * Setter method for property <tt>postVal</tt>.
     *
     * @param postVal value to be assigned to property  postVal
     */
    public void setPostVal(Double postVal) {
        this.postVal = postVal;
    }

    /**
     * Getter method for property <tt>investemntTime</tt>.
     *
     * @return property value of investemntTime
     */
    public Date getInvestemntTime() {
        return investemntTime;
    }

    /**
     * Setter method for property <tt>investemntTime</tt>.
     *
     * @param investemntTime value to be assigned to property  investemntTime
     */
    public void setInvestemntTime(Date investemntTime) {
        this.investemntTime = investemntTime;
    }

    /**
     * Getter method for property <tt>projectChannel</tt>.
     *
     * @return property value of projectChannel
     */
    public String getProjectChannel() {
        return projectChannel;
    }

    /**
     * Setter method for property <tt>projectChannel</tt>.
     *
     * @param projectChannel value to be assigned to property  projectChannel
     */
    public void setProjectChannel(String projectChannel) {
        this.projectChannel = projectChannel;
    }

    /**
     * Getter method for property <tt>companySortName</tt>.
     *
     * @return property value of companySortName
     */
    public String getCompanySortName() {
        return companySortName;
    }

    /**
     * Setter method for property <tt>companySortName</tt>.
     *
     * @param companySortName value to be assigned to property  companySortName
     */
    public void setCompanySortName(String companySortName) {
        this.companySortName = companySortName;
    }

    /**
     * Getter method for property <tt>companyFullName</tt>.
     *
     * @return property value of companyFullName
     */
    public String getCompanyFullName() {
        return companyFullName;
    }

    /**
     * Setter method for property <tt>companyFullName</tt>.
     *
     * @param companyFullName value to be assigned to property  companyFullName
     */
    public void setCompanyFullName(String companyFullName) {
        this.companyFullName = companyFullName;
    }

    /**
     * Getter method for property <tt>controllerOwner</tt>.
     *
     * @return property value of controllerOwner
     */
    public String getControllerOwner() {
        return controllerOwner;
    }

    /**
     * Setter method for property <tt>controllerOwner</tt>.
     *
     * @param controllerOwner value to be assigned to property  controllerOwner
     */
    public void setControllerOwner(String controllerOwner) {
        this.controllerOwner = controllerOwner;
    }

    /**
     * Getter method for property <tt>registeredCapital</tt>.
     *
     * @return property value of registeredCapital
     */
    public Double getRegisteredCapital() {
        return registeredCapital;
    }

    /**
     * Setter method for property <tt>registeredCapital</tt>.
     *
     * @param registeredCapital value to be assigned to property  registeredCapital
     */
    public void setRegisteredCapital(Double registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    /**
     * Getter method for property <tt>registerTime</tt>.
     *
     * @return property value of registerTime
     */
    public String getRegisterTime() {
        return registerTime;
    }

    /**
     * Setter method for property <tt>registerTime</tt>.
     *
     * @param registerTime value to be assigned to property  registerTime
     */
    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * Getter method for property <tt>address</tt>.
     *
     * @return property value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for property <tt>address</tt>.
     *
     * @param address value to be assigned to property  address
     */
    public void setAddress(String address) {
        this.address = address;
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
     * Getter method for property <tt>location</tt>.
     *
     * @return property value of location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter method for property <tt>location</tt>.
     *
     * @param location value to be assigned to property  location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter method for property <tt>mainBusiness</tt>.
     *
     * @return property value of mainBusiness
     */
    public String getMainBusiness() {
        return mainBusiness;
    }

    /**
     * Setter method for property <tt>mainBusiness</tt>.
     *
     * @param mainBusiness value to be assigned to property  mainBusiness
     */
    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    /**
     * Getter method for property <tt>orgNumber</tt>.
     *
     * @return property value of orgNumber
     */
    public String getOrgNumber() {
        return orgNumber;
    }

    /**
     * Setter method for property <tt>orgNumber</tt>.
     *
     * @param orgNumber value to be assigned to property  orgNumber
     */
    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    /**
     * Getter method for property <tt>creditCode</tt>.
     *
     * @return property value of creditCode
     */
    public String getCreditCode() {
        return creditCode;
    }

    /**
     * Setter method for property <tt>creditCode</tt>.
     *
     * @param creditCode value to be assigned to property  creditCode
     */
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    /**
     * Getter method for property <tt>updatetime</tt>.
     *
     * @return property value of updatetime
     */
    public String getUpdatetime() {
        return updatetime;
    }

    /**
     * Setter method for property <tt>updatetime</tt>.
     *
     * @param updatetime value to be assigned to property  updatetime
     */
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * Getter method for property <tt>taxNumber</tt>.
     *
     * @return property value of taxNumber
     */
    public String getTaxNumber() {
        return taxNumber;
    }

    /**
     * Setter method for property <tt>taxNumber</tt>.
     *
     * @param taxNumber value to be assigned to property  taxNumber
     */
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    /**
     * Getter method for property <tt>phoneNumber</tt>.
     *
     * @return property value of phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter method for property <tt>phoneNumber</tt>.
     *
     * @param phoneNumber value to be assigned to property  phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter method for property <tt>outType</tt>.
     *
     * @return property value of outType
     */
    public OutTypes getOutType() {
        return outType;
    }

    /**
     * Setter method for property <tt>outType</tt>.
     *
     * @param outType value to be assigned to property  outType
     */
    public void setOutType(OutTypes outType) {
        this.outType = outType;
    }

    /**
     * Getter method for property <tt>loanInstallments</tt>.
     *
     * @return property value of loanInstallments
     */
    public List<Installment> getLoanInstallments() {
        return loanInstallments;
    }

    /**
     * Setter method for property <tt>loanInstallments</tt>.
     *
     * @param loanInstallments value to be assigned to property  loanInstallments
     */
    public void setLoanInstallments(List<Installment> loanInstallments) {
        this.loanInstallments = loanInstallments;
    }

    /**
     * Getter method for property <tt>memberBStr</tt>.
     *
     * @return property value of memberBStr
     */
    public String getMemberBStr() {
        return memberBStr;
    }

    /**
     * Setter method for property <tt>memberBStr</tt>.
     *
     * @param memberBStr value to be assigned to property  memberBStr
     */
    public void setMemberBStr(String memberBStr) {
        this.memberBStr = memberBStr;
    }

    /**
     * Getter method for property <tt>bound</tt>.
     *
     * @return property value of bound
     */
    public String getBound() {
        return bound;
    }

    /**
     * Setter method for property <tt>bound</tt>.
     *
     * @param bound value to be assigned to property  bound
     */
    public void setBound(String bound) {
        this.bound = bound;
    }

    /**
     * Getter method for property <tt>investmentComp</tt>.
     *
     * @return property value of investmentComp
     */
    public String getInvestmentComp() {
        return investmentComp;
    }

    /**
     * Setter method for property <tt>investmentComp</tt>.
     *
     * @param investmentComp value to be assigned to property  investmentComp
     */
    public void setInvestmentComp(String investmentComp) {
        this.investmentComp = investmentComp;
    }

    /**
     * Getter method for property <tt>realInvestment</tt>.
     *
     * @return property value of realInvestment
     */
    public Double getRealInvestment() {
        return realInvestment;
    }

    /**
     * Setter method for property <tt>realInvestment</tt>.
     *
     * @param realInvestment value to be assigned to property  realInvestment
     */
    public void setRealInvestment(Double realInvestment) {
        this.realInvestment = realInvestment;
    }

    /**
     * Getter method for property <tt>sharePrice</tt>.
     *
     * @return property value of sharePrice
     */
    public Double getSharePrice() {
        return sharePrice;
    }

    /**
     * Setter method for property <tt>sharePrice</tt>.
     *
     * @param sharePrice value to be assigned to property  sharePrice
     */
    public void setSharePrice(Double sharePrice) {
        this.sharePrice = sharePrice;
    }

    /**
     * Getter method for property <tt>shareNum</tt>.
     *
     * @return property value of shareNum
     */
    public Double getShareNum() {
        return shareNum;
    }

    /**
     * Setter method for property <tt>shareNum</tt>.
     *
     * @param shareNum value to be assigned to property  shareNum
     */
    public void setShareNum(Double shareNum) {
        this.shareNum = shareNum;
    }

    /**
     * Getter method for property <tt>paidCapital</tt>.
     *
     * @return property value of paidCapital
     */
    public Double getPaidCapital() {
        return paidCapital;
    }

    /**
     * Setter method for property <tt>paidCapital</tt>.
     *
     * @param paidCapital value to be assigned to property  paidCapital
     */
    public void setPaidCapital(Double paidCapital) {
        this.paidCapital = paidCapital;
    }

    /**
     * Getter method for property <tt>fundType</tt>.
     *
     * @return property value of fundType
     */
    public FundType getFundType() {
        return fundType;
    }

    /**
     * Setter method for property <tt>fundType</tt>.
     *
     * @param fundType value to be assigned to property  fundType
     */
    public void setFundType(FundType fundType) {
        this.fundType = fundType;
    }
}
