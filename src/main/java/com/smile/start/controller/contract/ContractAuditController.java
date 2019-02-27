package com.smile.start.controller.contract;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.ContractAuditDTO;
import com.smile.start.dto.ContractAuditRecordDTO;
import com.smile.start.dto.ContractAuditSearchDTO;
import com.smile.start.dto.ContractBaseInfoDTO;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.service.contract.ContractInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import javax.annotation.Resource;

/**
 * @author Joseph
 * @version v1.0 2019/2/25 15:22, ContractAuditController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/contractAudit")
public class ContractAuditController extends BaseController {

    @Resource
    private ContractInfoService contractInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "contract/contractAudit";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<ContractBaseInfoDTO> list(@RequestBody PageRequest<ContractAuditSearchDTO> searchDTO) {
        return contractInfoService.findAuditList(searchDTO);
    }

    @PostMapping(value = "/audit")
    @ResponseBody
    public BaseResult audit(@RequestBody ContractAuditDTO contractAuditDTO) {
        try {
            contractInfoService.audit(contractAuditDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("合同审核成功");
            return result;
        } catch (Exception e) {
            logger.error("合同审核失败", e);
            return toResult(e);
        }
    }

    @GetMapping(value = "/auditRecordList/{contractSerialNo}")
    @ResponseBody
    public ListResult<ContractAuditRecordDTO> auditRecordList(@PathVariable String contractSerialNo) {
        try {
            final List<ContractAuditRecordDTO> auditRecordList = contractInfoService.findAuditRecord(contractSerialNo);
            ListResult<ContractAuditRecordDTO> result = new ListResult<>();
            result.setSuccess(true);
            result.setErrorMessage("获取审核记录成功");
            result.setValues(auditRecordList);
            return result;
        } catch (Exception e) {
            logger.error("获取审核记录失败", e);
            return toListResult(e, ContractAuditRecordDTO.class);
        }
    }
}
