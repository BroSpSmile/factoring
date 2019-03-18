package com.smile.start.controller.seal;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.SealSearchDTO;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.seal.ProjectSeal;
import com.smile.start.service.seal.SealService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用印管理
 * @author Joseph
 * @version v1.0 2019/3/16 23:57, SealController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/seal")
public class SealController extends BaseController {

    @Resource
    private SealService sealService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "project/seal";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<ProjectSeal> list(@RequestBody PageRequest<SealSearchDTO> sealSearch) {
        return sealService.findAll(sealSearch);
    }

    @PostMapping(value = "/sealFinish/{projectId}")
    @ResponseBody
    public BaseResult sealFinish(@PathVariable Long projectId) {
        try {
            sealService.sealFinish(projectId);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("用印完成保存成功");
            return result;
        } catch (Exception e) {
            logger.error("用印完成保存失败", e);
            return toResult(e);
        }
    }
}
