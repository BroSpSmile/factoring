package com.smile.start.controller.contract;

import com.github.pagehelper.PageInfo;
import com.smile.start.controller.BaseController;
import com.smile.start.dto.FlowStatusDTO;
import com.smile.start.dto.SignListTemplateDTO;
import com.smile.start.dto.SignListTemplateSearchDTO;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.ListResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.base.SingleResult;
import com.smile.start.service.contract.SignListTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/13 21:01, SignListTemplateController.java
 * @since 1.8
 */
@Controller
@RequestMapping(value = "/signListTemplate")
public class SignListTemplateController extends BaseController {

    @Resource
    private SignListTemplateService signListTemplateService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "contract/signListTemplate";
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public SingleResult<SignListTemplateDTO> get(@PathVariable Long id) {
        try {
            SignListTemplateDTO signListTemplateDTO = signListTemplateService.get(id);
            SingleResult<SignListTemplateDTO> result = new SingleResult<>();
            result.setSuccess(true);
            result.setData(signListTemplateDTO);
            return result;
        } catch (Exception e) {
            logger.error("查询签署清单失败", e);
            return toResult(e, SignListTemplateDTO.class);
        }
    }

    /**
     *
     * @param searchDTO
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public PageInfo<SignListTemplateDTO> list(@RequestBody PageRequest<SignListTemplateSearchDTO> searchDTO) {
        PageInfo<SignListTemplateDTO> signListTemplateList = signListTemplateService.findAll(searchDTO);
        return signListTemplateList;
    }

    /**
     *
     * @param signListTemplateDTO
     * @return
     */
    @PostMapping
    @ResponseBody
    public BaseResult add(@RequestBody SignListTemplateDTO signListTemplateDTO) {
        try {
            signListTemplateService.insert(signListTemplateDTO);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("新增签署清单成功");
            return result;
        } catch (Exception e) {
            logger.error("新增签署清单失败", e);
            return toResult(e);
        }
    }

    /**
     *
     * @param signListTemplateDTO
     * @return
     */
    @PutMapping
    @ResponseBody
    public BaseResult update(@RequestBody SignListTemplateDTO signListTemplateDTO) {
        try {
            BaseResult result = new BaseResult();
            signListTemplateService.update(signListTemplateDTO);
            result.setSuccess(true);
            result.setErrorMessage("更新签署清单成功");
            return result;
        } catch (Exception e) {
            logger.error("更新签署清单失败", e);
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
            signListTemplateService.delete(id);
            BaseResult result = new BaseResult();
            result.setSuccess(true);
            result.setErrorMessage("删除签署清单成功");
            return result;
        } catch (Exception e) {
            logger.error("删除签署清单失败", e);
            return toResult(e);
        }
    }

    /**
     *
     * @param projectMode
     * @return
     */
    @GetMapping(value = "/list/{projectMode}")
    @ResponseBody
    public ListResult<SignListTemplateDTO> list(@PathVariable Integer projectMode) {
        try {
            final List<SignListTemplateDTO> signlist = signListTemplateService.findByProjectMode(projectMode);
            ListResult<SignListTemplateDTO> result = new ListResult<>();
            result.setSuccess(true);
            result.setErrorMessage("获取签署清单成功");
            result.setValues(signlist);
            return result;
        } catch (Exception e) {
            logger.error("获取签署清单失败", e);
            return toListResult(e, SignListTemplateDTO.class);
        }
    }
}
