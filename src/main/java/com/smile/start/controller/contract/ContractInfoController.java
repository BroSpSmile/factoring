package com.smile.start.controller.contract;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.smile.start.dto.*;
import com.smile.start.model.base.ListResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.service.contract.ContractInfoService;

import java.util.List;

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

    /**
     * 从项目列表中点击 合同草拟 跳转页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("projectId", id);
        return "contract/contractInfoAdd";
    }

    /**
     *
     * @param projectId
     * @return
     */
    @GetMapping(value = "/{projectId}")
    @ResponseBody
    public SingleResult<ContractInfoDTO> get(@PathVariable Long projectId) {
        try {
            ContractInfoDTO contractInfoDTO = contractInfoService.getByProjectId(projectId);
            logger.info(JSON.toJSONString(contractInfoDTO));
            SingleResult<ContractInfoDTO> result = new SingleResult<>();
            result.setSuccess(true);
            result.setData(contractInfoDTO);
            return result;
        } catch (Exception e) {
            logger.error("查询合同信息失败", e);
            return toResult(e, ContractInfoDTO.class);
        }
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<ContractBaseInfoDTO> list(@RequestBody PageRequest<ContractInfoSearchDTO> searchDTO) {
        return contractInfoService.findAll(searchDTO);
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
     * @param contractInfoDTO
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(@RequestBody ContractInfoDTO contractInfoDTO) {
        try {
            BaseResult result = new BaseResult();
            contractInfoService.update(contractInfoDTO);
            result.setSuccess(true);
            result.setErrorMessage("更新合同成功");
            return result;
        } catch (Exception e) {
            logger.error("更新合同失败", e);
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

    /**
     *
     * @param id
     * @return
     */
    @PutMapping(value = "/submitAudit/{id}")
    @ResponseBody
    public BaseResult submitAudit(@PathVariable Long id) {
        try {
            contractInfoService.submitAudit(id);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("状态流转成功");
            return result;
        } catch (Exception e) {
            logger.error("状态流转失败", e);
            return toResult(e);
        }
    }

    @GetMapping(value = "/signList/{contractSerialNo}")
    @ResponseBody
    public ListResult<ContractSignListDTO> signList(@PathVariable String contractSerialNo) {
        try {
            final List<ContractSignListDTO> signList = contractInfoService.findSignListByContractSerialNo(contractSerialNo);
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
