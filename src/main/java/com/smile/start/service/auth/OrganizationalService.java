package com.smile.start.service.auth;

import java.util.List;

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
     * @param organizationalDTO 对象
     */
    void update(OrganizationalDTO organizationalDTO);

    /**
     * 删除组织架构信息
     * @param id
     */
    void delete(Long id);

    /**
     * 按条件查询
     * @param organizationalSearchDTO
     * @return
     */
    List<OrganizationalDTO> findByParam(OrganizationalSearchDTO organizationalSearchDTO);

    /**
     * 根据用户编号查询组织
     * @param userSerialNo
     * @return
     */
    List<OrganizationalDTO> findByUserSerialNo(String userSerialNo);

}
