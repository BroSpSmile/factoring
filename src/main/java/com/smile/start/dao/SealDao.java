package com.smile.start.dao;

import com.smile.start.dto.SealSearchDTO;
import com.smile.start.model.seal.ProjectSeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/3/17 19:46, SealDao.java
 * @since 1.8
 */
@Mapper
public interface SealDao {

    /**
     * 分页查询
     * @param sealSearchDTO
     * @return
     */
    @Select("<script>"
            + "select fp.id project_id,a.id audit_id,fp.project_id project_code,fp.project_name,ui.username projectPerson,ci.seal_status from factoring_project fp,contract_info ci,auth_user_info ui,audit a "
            + " where fp.id=ci.project_id and fp.person=ui.id and fp.id=a.project and fp.step = 6 and a.audit_type='DRAWUP'"
            + "<if test = 'projectCode!=null'> and fp.project_id like CONCAT('%',#{projectCode},'%')</if>"
            + "<if test = 'projectName!=null'> and fp.project_name like CONCAT('%',#{projectName},'%')</if>"
            + "<if test = 'sealStatus!=null'> and ci.seal_status = #{sealStatus}</if>" + "</script>")
    List<ProjectSeal> findByParam(SealSearchDTO sealSearchDTO);
}
