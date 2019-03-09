package com.smile.start.controller.common;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.*;
import com.smile.start.exception.ValidateException;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.service.common.FlowConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 21:54, FlowConfigController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/flowConfig")
public class FlowConfigController extends BaseController {

    @Resource
    private FlowConfigService flowConfigService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "system/flowConfig";
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public SingleResult<FlowConfigDTO> get(@PathVariable Long id) {
        try {
            SingleResult<FlowConfigDTO> result = new SingleResult<>();
            FlowConfigDTO flowConfigDTO = flowConfigService.get(id);
            result.setSuccess(true);
            result.setData(flowConfigDTO);
            return result;
        } catch (Exception e) {
            logger.error("查询流程配置信息失败", e);
            return toResult(e, FlowConfigDTO.class);
        }
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<FlowConfigDTO> list(@RequestBody PageRequest<FlowConfigSearchDTO> searchDTO) {
        PageInfo<FlowConfigDTO> flowConfigList = flowConfigService.findAll(searchDTO);
        return flowConfigList;
    }

    /**
     *
     * @param flowConfigDTO
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(@RequestBody FlowConfigDTO flowConfigDTO) {
        try {
            flowConfigService.insert(flowConfigDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("新增流程配置成功");
            return result;
        } catch (ValidateException e) {
            logger.error(e.getMessage(), e);
            return toResult(e);
        } catch (Exception e) {
            logger.error("新增流程配置失败", e);
            return toResult(e);
        }
    }

    /**
     *
     * @param flowConfigDTO
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(@RequestBody FlowConfigDTO flowConfigDTO) {
        try {
            BaseResult result = new BaseResult();
            flowConfigService.update(flowConfigDTO);
            result.setSuccess(true);
            result.setErrorMessage("更新流程配置成功");
            return result;
        } catch (Exception e) {
            logger.error("更新流程配置失败", e);
            return toResult(e);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public BaseResult delete(@PathVariable Long id) {
        try {
            flowConfigService.delete(id);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("删除流程配置成功");
            return result;
        } catch (Exception e) {
            logger.error("删除流程配置失败", e);
            return toResult(e);
        }
    }

    @GetMapping(value = "/status/{flowType}")
    @ResponseBody
    public ListResult<FlowStatusDTO> status(@PathVariable int flowType) {
        try {
            final List<FlowStatusDTO> statusList = flowConfigService.getStatusList(flowType);
            ListResult<FlowStatusDTO> result = new ListResult<>();
            result.setSuccess(true);
            result.setErrorMessage("获取状态列表成功");
            result.setValues(statusList);
            return result;
        } catch (Exception e) {
            logger.error("获取状态列表失败", e);
            return toListResult(e, FlowStatusDTO.class);
        }
    }
}
