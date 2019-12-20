/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.web.controller.common;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smile.start.web.controller.BaseController;
import com.smile.start.model.enums.project.ProjectKind;
import com.smile.start.service.project.IdGenService;

/**
 * genId
 * 
 * @author smile.jing
 * @version $Id: GenIdApi.java, v 0.1 Jan 13, 2019 8:00:40 PM smile.jing Exp $
 */
@RestController
@RequestMapping("/id")
public class GenIdApi extends BaseController {

    /** idGenService */
    @Resource
    private IdGenService idGenService;

    /**
     * getGenId
     * 
     * @param code
     * @return
     */
    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public String getGenId(@PathVariable String code) {
        ProjectKind kind = ProjectKind.getByCode(code);
        return idGenService.genId(kind);
    }
}
