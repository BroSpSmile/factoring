package com.smile.start.controller.contract;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.ContractBaseInfoDTO;
import com.smile.start.dto.ContractInfoSearchDTO;
import com.smile.start.dto.ContractSignDTO;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.service.contract.ContractInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/2/27 21:44, ContractSignController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/contractSign")
public class ContractSignController extends BaseController {

    @Resource
    private ContractInfoService contractInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "contract/contractSign";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<ContractBaseInfoDTO> list(@RequestBody PageRequest<ContractInfoSearchDTO> searchDTO) {
        PageInfo<ContractBaseInfoDTO> contractInfoList = contractInfoService.findAll(searchDTO);
        return contractInfoList;
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public BaseResult save(@RequestBody ContractSignDTO contractSignDTO) {
        try {
            contractInfoService.saveSign(contractSignDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("保存签署信息成功");
            return result;
        } catch (Exception e) {
            logger.error("保存签署信息失败", e);
            return toResult(e);
        }
    }
}
