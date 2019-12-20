/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.tianyan;

import com.smile.start.integration.tianyan.model.CompanyInfo;
import com.smile.start.integration.tianyan.model.TianyanResult;

/**
 * 天眼查接口
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: CompanyClient
 * @date Date : 2019年12月18日 18:40
 */
public interface CompanyClient {

    /**
     * 查询企业信息
     * @param id
     * @param companyName
     * @return
     */
    TianyanResult<CompanyInfo> query(String id, String companyName);
}
