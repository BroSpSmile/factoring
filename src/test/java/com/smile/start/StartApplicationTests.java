package com.smile.start;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smile.start.model.enums.project.ProjectKind;
import com.smile.start.service.project.IdGenService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartApplicationTests {

    /** idGenService */
    @Resource
    private IdGenService idGenService;

    @Test
    public void contextLoads() {
    }

    /**
     * testGenId
     */
    @Test
    public void testGenId() {
        String idString = idGenService.genId(ProjectKind.FACTORING);
        System.out.println(idString);
    }

}
