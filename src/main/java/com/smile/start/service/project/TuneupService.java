/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.project.Project;

/**
 * 立项服务
 * @author smile.jing
 * @version $Id: TuneupService.java, v 0.1 Mar 5, 2019 12:44:46 PM smile.jing Exp $
 */
public interface TuneupService {
    /**
    * 服务立项
    * @param project
    * @return
    */
    BaseResult tuneupApply(Project project);
}
