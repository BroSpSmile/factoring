package com.smile.start.service;

import com.github.pagehelper.PageInfo;
import com.smile.start.dto.OrganizationalDTO;
import com.smile.start.dto.OrganizationalSearchDTO;
import com.smile.start.model.base.PageRequest;

/**
 * @author Joseph
 * @version v1.0 2019/1/29 16:52, OrganizationalService.java
 * @since 1.8
 */
public interface OrganizationalService {

    /**
     * 根据主键查询组织架构信息
     * @param id
     * @return
     */
    OrganizationalDTO get(Long id);

    /**
     * 查询所有组织架构
     * @return
     */
    PageInfo<OrganizationalDTO> findAll(PageRequest<OrganizationalSearchDTO> page);

    /**
     * 新增组织架构信息
     * @param organizationalDTO
     * @return
     */
    Long insert(OrganizationalDTO organizationalDTO);

    /**
     * 更新组织架构信息
     * @param organizationalDTO
     */
    void update(OrganizationalDTO organizationalDTO);

    /**
     * 删除组织架构信息
     * @param id
     */
    void delete(Long id);

}
