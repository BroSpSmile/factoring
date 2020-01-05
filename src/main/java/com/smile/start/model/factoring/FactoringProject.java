/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.model.factoring;

import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.BaseProject;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: FactoringProject
 * @date Date : 2019年12月30日 16:15
 */
public class FactoringProject extends BaseProject<Object> {

    /** 项目明细 */
    private FundTarget detail;

    @Override
    public String toString() {
        return "{\"FactoringProject\":{" + "\"detail\":" + detail + "},\"super-FactoringProject\":" + super.toString() + "}";

    }

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
