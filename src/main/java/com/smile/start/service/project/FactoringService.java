/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.project.FactoringDetail;

/**
 * 保理服务
 * @author smile.jing
 * @version $Id: FactoringService.java, v 0.1 Feb 24, 2019 7:27:19 PM smile.jing Exp $
 */
public interface FactoringService {
    /**
     * 创建项目
     * @param detail
     * @return
     */
    BaseResult create(FactoringDetail detail);
}
