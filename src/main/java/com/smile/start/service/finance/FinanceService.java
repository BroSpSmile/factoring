/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.finance;

import com.smile.start.model.project.Installment;

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
}
