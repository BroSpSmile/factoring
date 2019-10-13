/**
 * VIP.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.fund;

import com.smile.start.model.project.BaseProject;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FundProject
 * @date Date : 2019年10月09日 23:11
 */
public class FundProject extends BaseProject<Object> {

    /** 项目明细 */
    private FundTarget detail;

    /**
     * Getter method for property <tt>detail</tt>.
     *
     * @return property value of detail
     */
    @Override
    public FundTarget getDetail() {
        return detail;
    }

    /**
     * Setter method for property <tt>detail</tt>.
     *
     * @param detail value to be assigned to property  detail
     */
    public void setDetail(FundTarget detail) {
        this.detail = detail;
    }
}
