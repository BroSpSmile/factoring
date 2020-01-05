/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import com.smile.start.model.enums.InstallmentDetailType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 分期信息
 *
 * @author smile.jing
 * @version $Id: LoanInstallment.java, v 0.1 Mar 6, 2019 6:28:49 PM smile.jing Exp $
 */
public class InstallmentDetail implements Serializable {

    private static final long     serialVersionUID = -134333261845069621L;

    /**
     * 编号
     */
    private Long                  id               = -1L;

    private InstallmentDetailType type;

    /**
     * 分期编号
     */
    private Long                  installmentId;

    private Long                  bankInfoId;

    /**
     * 分期明细操作时间：开票日期或者收款日期
     */
    private Date                  detailDate;

    /**
     * 分期详情金额
     */
    private double                detailAmount;

    /**
     * 开票或收款凭证附件
     */
    private InstallmentDetailItem item;

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
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public InstallmentDetailType getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property  type
     */
    public void setType(InstallmentDetailType type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>installmentId</tt>.
     *
     * @return property value of installmentId
     */
    public Long getInstallmentId() {
        return installmentId;
    }

    /**
     * Setter method for property <tt>installmentId</tt>.
     *
     * @param installmentId value to be assigned to property  installmentId
     */
    public void setInstallmentId(Long installmentId) {
        this.installmentId = installmentId;
    }

    /**
     * Getter method for property <tt>bankInfoId</tt>.
     *
     * @return property value of bankInfoId
     */
    public Long getBankInfoId() {
        return bankInfoId;
    }

    /**
     * Setter method for property <tt>bankInfoId</tt>.
     *
     * @param bankInfoId value to be assigned to property  bankInfoId
     */
    public void setBankInfoId(Long bankInfoId) {
        this.bankInfoId = bankInfoId;
    }

    /**
     * Getter method for property <tt>detailDate</tt>.
     *
     * @return property value of detailDate
     */
    public Date getDetailDate() {
        return detailDate;
    }

    /**
     * Setter method for property <tt>detailDate</tt>.
     *
     * @param detailDate value to be assigned to property  detailDate
     */
    public void setDetailDate(Date detailDate) {
        this.detailDate = detailDate;
    }

    /**
     * Getter method for property <tt>detailAmount</tt>.
     *
     * @return property value of detailAmount
     */
    public double getDetailAmount() {
        return detailAmount;
    }

    /**
     * Setter method for property <tt>detailAmount</tt>.
     *
     * @param detailAmount value to be assigned to property  detailAmount
     */
    public void setDetailAmount(double detailAmount) {
        this.detailAmount = detailAmount;
    }

    /**
     * Getter method for property <tt>item</tt>.
     *
     * @return property value of item
     */
    public InstallmentDetailItem getItem() {
        return item;
    }

    /**
     * Setter method for property <tt>item</tt>.
     *
     * @param item value to be assigned to property  item
     */
    public void setItem(InstallmentDetailItem item) {
        this.item = item;
    }

}
