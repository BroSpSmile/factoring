package com.smile.start.dao;

import com.smile.start.dto.FlowConfigSearchDTO;
import com.smile.start.model.common.FlowConfig;
import com.smile.start.model.common.FlowStatus;
import com.smile.start.model.common.FlowStatusRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 12:55, FlowConfigDao.java
 * @since 1.8
 */
@Mapper
public interface FlowConfigDao {

    /**
     * 新增流程配置信息
     *
     * @param flowConfig
     * @return
     */
    @Insert("")
    long insert(FlowConfig flowConfig);

    /**
     * 新增流程配置与状态的关联关系
     * @param flowStatus
     * @return
     */
    @Insert("")
    long insertFlowStatus(FlowStatus flowStatus);

    /**
     * 新增状态与角色的关联关系
     * @param flowStatusRole
     * @return
     */
    @Insert("")
    long insertStatusRole(FlowStatusRole flowStatusRole);

    /**
     * 更新流程配置
     * @param flowConfig
     * @return
     */
    @Update("")
    int update(FlowConfig flowConfig);

    /**
     * 根据ID获取流程配置
     * @param id
     * @return
     */
    @Select("select * from flow_config where id = #{id}")
    FlowConfig get(Long id);

    /**
     * 根据流程配置编号查询状态信息
     * @param flowSerialNo
     * @return
     */
    @Select("select * from flow_config_status where flow_serial_no=#{flowSerialNo}")
    List<FlowStatus> findByFlowSerialNo(String flowSerialNo);

    /**
     * 分页查询
     * @param flowConfigSearchDTO
     * @return
     */
    @Select("<script>" + "select * from flow_config where 1=1 "
        + "<if test = 'flowName!=null'> and flow_name like CONCAT('%',#{flowName},'%')</if>"
        + "<if test = 'flowType!=null'> and flow_type = #{flowType}</if>"
        + "</script>")
    List<FlowConfig> findByParam(FlowConfigSearchDTO flowConfigSearchDTO);

    /**
     * 删除流程信息
     * @param id
     */
    @Delete("delete from flow_config where id = #{id}")
    void delete(Long id);

    /**
     * 删除流程配置与状态关联信息
     * @param flowSerialNo
     */
    @Delete("delete from flow_status where flow_serial_no = #{flowSerialNo}")
    void deleteFlowStatus(String flowSerialNo);

    /**
     * 删除状态与角色关联信息
     * @param flowSerialNo
     */
    @Delete("delete from flow_status_role where flow_serial_no = #{flowSerialNo}")
    void deleteStatusRole(String flowSerialNo);
}
