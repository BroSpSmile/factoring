/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.loan;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.loan.Loan;
import com.smile.start.model.project.Project;

/**
 * 放款服务
 * @author smile.jing
 * @version $Id: LoanService.java, v 0.1 Feb 27, 2019 5:00:58 PM smile.jing Exp $
 */
public interface LoanService {

    /**
     * 保存
     * @param loan
     * @return
     */
    BaseResult save(Loan loan);

    /**
     * 提交
     * @param loan
     * @return
     */
    BaseResult commit(Loan loan);

    /**
     * 获取项目放款信息
     * @param project
     * @return
     */
    Loan getLoan(Project project);
}
