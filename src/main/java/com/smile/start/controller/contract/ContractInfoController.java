package com.smile.start.controller.contract;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.*;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.service.contract.ContractInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/2/20 20:29, ContractInfoController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/contractInfo")
public class ContractInfoController extends BaseController {

    @Resource
    private ContractInfoService contractInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "contract/contractInfo";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<ContractBaseInfoDTO> list(@RequestBody PageRequest<ContractInfoSearchDTO> searchDTO) {
        PageInfo<ContractBaseInfoDTO> contractInfoList = contractInfoService.findAll(searchDTO);
        return contractInfoList;
    }

    /**
     *
     * @param contractInfoDTO
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(@RequestBody ContractInfoDTO contractInfoDTO) {
        try {
            contractInfoService.insert(contractInfoDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("新增合同成功");
            return result;
        } catch (Exception e) {
            logger.error("新增合同失败", e);
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
            contractInfoService.delete(id);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("删除合同成功");
            return result;
        } catch (Exception e) {
            logger.error("删除合同失败", e);
            return toResult(e);
        }
    }
}
