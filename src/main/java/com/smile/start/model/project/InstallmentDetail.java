/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.model.project;

import com.smile.start.model.enums.InstallmentDetailType;

import java.io.Serializable;
import java.util.Date;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InstallmentDetailType getType() {
        return type;
    }

    public void setType(InstallmentDetailType type) {
        this.type = type;
    }

    public Long getInstallmentId() {
        return installmentId;
    }

    public void setInstallmentId(Long installmentId) {
        this.installmentId = installmentId;
    }

    public Long getBankInfoId() {
        return bankInfoId;
    }

    public void setBankInfoId(Long bankInfoId) {
        this.bankInfoId = bankInfoId;
    }

    public Date getDetailDate() {
        return detailDate;
    }

    public void setDetailDate(Date detailDate) {
        this.detailDate = detailDate;
    }

    public InstallmentDetailItem getItem() {
        return item;
    }

    public void setItem(InstallmentDetailItem item) {
        this.item = item;
    }

    public double getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(double detailAmount) {
        this.detailAmount = detailAmount;
    }
}
