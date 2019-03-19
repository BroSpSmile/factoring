package com.smile.start.controller.contract;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.ContractBaseInfoDTO;
import com.smile.start.dto.ContractInfoSearchDTO;
import com.smile.start.dto.ContractSignDTO;
import com.smile.start.dto.ContractSignListDTO;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.service.contract.ContractInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 从项目列表中点击 签署 跳转页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("projectId", id);
        return "contract/contractSign";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<ContractBaseInfoDTO> list(@RequestBody PageRequest<ContractInfoSearchDTO> searchDTO) {
        return contractInfoService.findAll(searchDTO);
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

    @GetMapping(value = "/signList/{projectId}")
    @ResponseBody
    public ListResult<ContractSignListDTO> signList(@PathVariable Long projectId) {
        try {
            final List<ContractSignListDTO> signList = contractInfoService.findSignListByProjectId(projectId);
            ListResult<ContractSignListDTO> result = new ListResult<>();
            result.setSuccess(true);
            result.setErrorMessage("获取合同签署清单成功");
            result.setValues(signList);
            return result;
        } catch (Exception e) {
            logger.error("获取合同签署清单失败", e);
            return toListResult(e, ContractSignListDTO.class);
        }
    }
}
