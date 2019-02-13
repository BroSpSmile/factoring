package com.smile.start.dao;

import com.smile.start.model.filing.FilingApplyInfo;
import com.smile.start.model.filing.FilingFileItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：xioutman
 * @description：归档申请Dao
 * @date ：Created in 2019/2/4 11:10
 * @modified By：
 * @version: $
 */
@Mapper
public interface FilingDao {

    /**
     * 新增归档申请
     *
     * @param filingApplyInfo
     * @return
     */
    @Insert("insert into filing_apply_info (project_id,applicant,apply_time,filing_list_str,progress) values (#{projectId},#{applicant},#{applyTime},#{filingListStr},#{progress})")
    long insert(FilingApplyInfo filingApplyInfo);

    /**
     * 更新
     *
     * @param filingApplyInfo
     * @return
     */
    @Update("<script>" + "update filing_apply_info" + " set id=#{id}" + "<if test = 'projectId!=null'>,project_id = #{projectId}</if>"
            + "<if test = 'applicant!=null'>,applicant = #{applicant}</if>" + "<if test = 'applyTime!=null'> , apply_time = #{applyTime}</if>"
            + "<if test = 'filingListStr!=null'> , filing_list_str = #{filingListStr}</if>" + "<if test = 'progress!=null'> , progress = #{progress}</if>" + " where id=#{id} "
            + "</script>")
    int update(FilingApplyInfo filingApplyInfo);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Delete("delete from filing_apply_info where id = #{id}")
    int delete(Long id);

    /**
     * 根据ID获取归档申请信息
     *
     * @param id
     * @return
     */
    @Select("select * from filing_apply_info where id = #{id}")
    FilingApplyInfo get(Long id);

    /**
     * 根据项目ID获取归档申请信息
     *
     * @param projectId
     * @return
     */
    @Select("select * from filing_apply_info where project_id=#{projectId}")
    List<FilingApplyInfo> findByProjectId(String projectId);

    /**
     * 根据项目ID获取归档申请信息文件信息
     *
     * @param projectId
     * @return
     */
    @Select("select * from filing_file_item where project_id=#{projectId}")
    List<FilingFileItem> findItemByProjectId(String projectId);

    /**
     * 查询全部项目
     *
     * @return
     */
    @Select("select * from filing_apply_info")
    List<FilingApplyInfo> findAll();

    /**
     * 分页查询
     *
     * @param filingApplyInfo
     * @return
     */
    @Select("<script>" + "select * from filing_apply_info where 1=1 " + "<if test = 'projectId!=null'> and project_id = #{projectId}</if>"
            + "<if test = 'applicant!=null'> and applicant = #{applicant}</if>" + "<if test = 'applyTime!=null'> and apply_time = #{applyTime}</if>"
            + "<if test = 'filingListStr!=null'> and filing_list_str = #{filingListStr}</if>" + "<if test = 'progress!=null'> and progress = #{progress}</if></script>")
    List<FilingApplyInfo> findByParam(FilingApplyInfo filingApplyInfo);

    /**
     * 插入
     *
     * @param item
     * @return
     */
    @Insert("insert filing_file_item (project_id,item_type,item_name,item_value) values(#{projectId},#{itemType},#{itemName},#{itemValue})")
    long insertFileItem(FilingFileItem item);

    /**
     * 删除归档申请文件
     *
     * @param projectId
     * @return
     */
    @Insert("delete filing_file_item  where project_id = #{projectId})")
    long delFileItemByProjectId(String projectId);

}