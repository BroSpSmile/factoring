package com.smile.start.service.contract;

import com.github.pagehelper.PageInfo;
import com.smile.start.dto.SignListTemplateDTO;
import com.smile.start.dto.SignListTemplateSearchDTO;
import com.smile.start.model.base.PageRequest;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/13 20:45, SignListTemplateService.java
 * @since 1.8
 */
public interface SignListTemplateService {
    /**
     * 根据主键查询签署清单
     *
     * @param id
     * @return
     */
    SignListTemplateDTO get(Long id);

    /**
     * 查询所有签署清单
     * @return
     */
    PageInfo<SignListTemplateDTO> findAll(PageRequest<SignListTemplateSearchDTO> page);

    /**
     * 查询所有签署清单
     * @return
     */
    List<SignListTemplateDTO> findAll();

    /**
     * 新增签署清单
     *
     * @param signListTemplateDTO
     * @return
     */
    Long insert(SignListTemplateDTO signListTemplateDTO);

    /**
     * 更新签署清单
     *
     * @param signListTemplateDTO
     */
    void update(SignListTemplateDTO signListTemplateDTO);

    /**
     * 删除签署清单
     *
     * @param id
     */
    void delete(Long id);
}
