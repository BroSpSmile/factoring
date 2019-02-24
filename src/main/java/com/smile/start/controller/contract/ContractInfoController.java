package com.smile.start.controller.contract;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.ContractInfoDTO;
import com.smile.start.dto.ContractInfoSearchDTO;
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
    public PageInfo<ContractInfoDTO> list(@RequestBody PageRequest<ContractInfoSearchDTO> searchDTO) {
        PageInfo<ContractInfoDTO> contractInfoList = contractInfoService.findAll(searchDTO);
        return contractInfoList;
    }
}
