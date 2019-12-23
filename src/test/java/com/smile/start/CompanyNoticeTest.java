/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start;

import com.smile.start.service.fund.FundService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: CompanyNoticeTest
 * @date Date : 2019年12月23日 18:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyNoticeTest {

    /** */
    @Resource
    private FundService fundService;

    @Test
    public void testNotice() {
        this.fundService.checkCompaniesInfo();
    }
}
