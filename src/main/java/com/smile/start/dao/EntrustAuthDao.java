package com.smile.start.dao;

import com.smile.start.model.enums.Step;
import com.smile.start.model.project.EntrustAuth;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：xioutman
 * @description：流程流转Dao
 * @date ：Created in 2019/2/4 11:10
 * @modified By：
 * @version: $
 */
@Mapper
public interface EntrustAuthDao {

    /**
     * 新增归档申请
     *
     * @param entrustAuth
     * @return
     */
    @Insert("insert into entrust_auth (type,project_id,from_user_id,to_user_id,audit_id,record_id,remark) values (#{type},#{projectId},#{fromUserId},#{toUserId},#{auditId},#{recordId},#{remark})")
    long insert(EntrustAuth entrustAuth);

    /**
     * 更新
     *
     * @param entrustAuth
     * @return
     */
    @Update("<script>" + "update entrust_auth" + " set id=#{id}" + "<if test = 'type!=null'>, type = #{type}</if>"
            + "<if test = 'projectId!=null'> , project_id = #{projectId}</if>" + "<if test = 'fromUserId!=null'> , from_user_id = #{fromUserId}</if>"
            + "<if test = 'toUserId!=null'> , to_user_id = #{toUserId}</if>" + "<if test = 'auditId!=null'> , audit_id = #{auditId}</if>"
            + "<if test = 'recordId!=null'> , record_id = #{recordId}</if>" + "<if test = 'remark!=null'> , remark = #{remark}</if>" + " where project_id=#{projectId} "
            + "</script>")
    int update(EntrustAuth entrustAuth);

    /**
     * 删除
     *
     * @param projectId
     * @return
     */
    @Delete("delete from entrust_auth where project_id = #{projectId}")
    int delete(Long projectId);

    /**
     * 根据ID获取
     *
     * @param projectId
     * @return
     */
    @Select("select * from entrust_auth where project_id = #{projectId}")
    EntrustAuth get(Long projectId);

    /**
     * @param type
     * @return
     */
    @Results(id = "getEntrustAuthsMap")
    @Select("select project_id from entrust_auth where to_user_id = #{toUserId} and type= #{type} ")
    List<Long> getEntrustAuthProjectIds(Long toUserId, Step type);

}