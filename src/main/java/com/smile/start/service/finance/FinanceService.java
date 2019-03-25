/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.finance;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.InstallmentType;
import com.smile.start.model.project.Installment;
import com.smile.start.model.project.Project;

import java.util.List;

/**
 * 财务操作
 * @author Jacky
 * @version $Id: FinanceService.java, v 0.1 2019年3月6日 下午3:25:42 Jacky Exp $
 */
public interface FinanceService {

    /**
     * 保存放款信息
     * @param installment
     * @return
     */
    Long saveLoanInstallment(Installment installment);

    BaseResult saveInstallments(Project project, InstallmentType installmentType);

    PageInfo<Project> queryPageProject(PageRequest<Project> page);

}
