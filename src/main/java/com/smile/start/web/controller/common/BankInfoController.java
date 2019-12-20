package com.smile.start.web.controller.common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.model.dto.BankInfoDTO;
import com.smile.start.model.dto.BankInfoSearchDTO;
import com.smile.start.service.common.BankInfoService;
import com.smile.start.web.controller.BaseController;

/**
 * @author Joseph
 * @version v1.0 2019/3/9 14:38, BankInfoController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/bankInfo")
public class BankInfoController extends BaseController {

    @Resource
    private BankInfoService bankInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "system/bankInfo";
    }

    /**
     * 根据主键获取银行信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public SingleResult<BankInfoDTO> get(@PathVariable Long id) {
        try {
            SingleResult<BankInfoDTO> result = new SingleResult<>();
            BankInfoDTO bankInfoDTO = bankInfoService.get(id);
            result.setSuccess(true);
            result.setData(bankInfoDTO);
            return result;
        } catch (Exception e) {
            logger.error("查询银行信息信息失败", e);
            return toResult(e, BankInfoDTO.class);
        }
    }

    /**
     * 分页查询银行信息
     * @param searchDTO
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<BankInfoDTO> list(@RequestBody PageRequest<BankInfoSearchDTO> searchDTO) {
        return bankInfoService.findAll(searchDTO);
    }

    /**
     * 查询所有银行信息
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public ListResult<BankInfoDTO> list() {
        try {
            final List<BankInfoDTO> bankList = bankInfoService.findAll();
            ListResult<BankInfoDTO> result = new ListResult<>();
            result.setSuccess(true);
            result.setErrorMessage("获取银行信息成功");
            result.setValues(bankList);
            return result;
        } catch (Exception e) {
            logger.error("获取银行信息失败", e);
            return toListResult(e, BankInfoDTO.class);
        }
    }

    /**
     * 新增银行信息
     * @param bankInfoDTO
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(@RequestBody BankInfoDTO bankInfoDTO) {
        try {
            bankInfoService.insert(bankInfoDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("新增银行信息成功");
            return result;
        } catch (Exception e) {
            logger.error("新增银行信息失败", e);
            return toResult(e);
        }
    }

    /**
     * 更新银行信息
     * @param bankInfoDTO
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(@RequestBody BankInfoDTO bankInfoDTO) {
        try {
            BaseResult result = new BaseResult();
            bankInfoService.update(bankInfoDTO);
            result.setSuccess(true);
            result.setErrorMessage("更新银行信息成功");
            return result;
        } catch (Exception e) {
            logger.error("更新银行信息失败", e);
            return toResult(e);
        }
    }

    /**
     * 删除银行信息
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public BaseResult delete(@PathVariable Long id) {
        try {
            bankInfoService.delete(id);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("删除银行信息成功");
            return result;
        } catch (Exception e) {
            logger.error("删除银行信息失败", e);
            return toResult(e);
        }
    }
}
