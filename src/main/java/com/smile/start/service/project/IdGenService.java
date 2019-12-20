/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.project;

import com.smile.start.model.enums.project.ProjectKind;

/**
 * id生成服务
 * 
 * @author smile.jing
 * @version $Id: IdGenService.java, v 0.1 Jan 13, 2019 6:10:32 PM smile.jing Exp
 *          $
 */
public interface IdGenService {

    /**
     * 生成ID
     * 
     * @param kind
     * @return
     */
    String genId(ProjectKind kind);

}
