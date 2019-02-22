/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageInfo;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.project.Project;
import com.smile.start.service.project.ProjectService;

/**
 * 项目服务测试桩
 * @author smile.jing
 * @version $Id: ProjectServcieTest.java, v 0.1 Jan 13, 2019 10:19:40 PM smile.jing Exp $
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServcieTest {
    /** projectService */
    @Resource
    private ProjectService projectService;

    /**
     * 测试分页
     */
    @Test
    public void testProjectPage() {
        Project query = new Project();
        PageRequest<Project> request = new PageRequest<Project>(query, 1, 4);
        PageInfo<Project> projects = projectService.queryPage(request);
        System.out.println(FastJsonUtils.toJSONString(projects));
    }
}
