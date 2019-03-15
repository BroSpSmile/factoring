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
    @Insert("insert into flow_config (serial_no,flow_name,flow_type,create_user,modify_user,gmt_create,gmt_modify) values (#{serialNo},#{flowName},#{flowType},#{createUser},#{modifyUser},#{gmtCreate},#{gmtModify})")
    long insert(FlowConfig flowConfig);

    /**
     * 新增流程配置与状态的关联关系
     * @param flowStatus
     * @return
     */
    @Insert("insert into flow_status (serial_no,flow_serial_no,flow_status,flow_status_desc,role_serial_no) values (#{serialNo},#{flowSerialNo},#{flowStatus},#{flowStatusDesc},#{roleSerialNo})")
    long insertFlowStatus(FlowStatus flowStatus);

    /**
     * 新增状态与角色的关联关系
     * @param flowStatusRole
     * @return
     */
    @Insert("insert into flow_status_role (serial_no,flow_serial_no,status_serial_no,role_serial_no) values (#{serialNo},#{flowSerialNo},#{statusSerialNo},#{roleSerialNo})")
    long insertStatusRole(FlowStatusRole flowStatusRole);

    /**
     * 更新流程配置
     * @param flowConfig
     * @return
     */
    @Update("update flow_config set serial_no=#{serialNo},flow_name=#{flowName},flow_type=#{flowType},modify_user=#{modifyUser},gmt_modify=#{gmtModify} where id=#{id}")
    int update(FlowConfig flowConfig);

    /**
     * 根据ID获取流程配置
     * @param id
     * @return
     */
    @Select("select * from flow_config where id = #{id}")
    FlowConfig get(Long id);

    /**
     * 根据流程类型查询，此方法用于新增校验用，一个类型只能存在一条记录
     * @param flowType
     * @return
     */
    @Select("select * from flow_config where flow_type = #{flowType}")
    List<FlowConfig> findByFlowType(Integer flowType);

    /**
     * 根据类型获取流程配置
     * @param flowType
     * @return
     */
    @Select("select * from flow_config where flow_type = #{flowType}")
    FlowConfig getByType(Integer flowType);

    /**
     * 根据流程配置编号查询状态信息
     * @param flowSerialNo
     * @return
     */
    @Select("select * from flow_status where flow_serial_no=#{flowSerialNo}")
    List<FlowStatus> findStatusByFlowSerialNo(String flowSerialNo);

    /**
     * 根据流程状态编号查询对应角色编号
     * @param statusSerialNo
     * @return
     */
    @Select("select * from flow_status_role where status_serial_no=#{statusSerialNo}")
    List<FlowStatusRole> findRoleByStatusSerialNo(String statusSerialNo);

    /**
     * 分页查询
     * @param flowConfigSearchDTO
     * @return
     */
    @Select("<script>" + "select * from flow_config fc where 1=1 "
            + "<if test = 'flowName!=null'> and flow_name like CONCAT('%',#{flowName},'%')</if>"
            + "<if test = 'flowType!=null'> and flow_type = #{flowType}</if>" + "</script>")
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

    /**
     * 根据流程类型、状态值获取对应状态配置信息
     * @param flowType
     * @param flowStatus
     * @return
     */
    @Select("select fs.* from flow_config fc,flow_status fs where fc.serial_no = fs.flow_serial_no and fc.flow_type = #{flowType} and fs.flow_status = #{flowStatus}")
    FlowStatus findByFlowTypeAndStatus(Integer flowType, Integer flowStatus);
}
