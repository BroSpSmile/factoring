package com.smile.start.controller.seal;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.SealSearchDTO;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.seal.ProjectSeal;
import com.smile.start.service.seal.SealService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/4/13 14:49, SealRecordController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/sealRecord")
public class SealRecordController extends BaseController {

    @Resource
    private SealService sealService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "project/sealRecord";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<ProjectSeal> record(@RequestBody PageRequest<SealSearchDTO> sealSearch) {
        return sealService.findAllRecord(sealSearch);
    }
}
