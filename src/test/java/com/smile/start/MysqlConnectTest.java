package com.smile.start;

import java.util.List;

import javax.annotation.Resource;

import com.smile.start.dao.ProjectDao;
import com.smile.start.model.project.Project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MysqlConnectTest {

    /** projectDao */
    @Resource
    private ProjectDao projectDao;

    @Test
    public void testConnect() {
        List<Project> projects = projectDao.findAll();
        System.out.println(projects.toString());
    }
}