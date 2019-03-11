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
    @Insert("insert into filing_apply_info (project,applicant,apply_time,filing_list_str,progress) values (#{project},#{applicant},#{applyTime},#{filingListStr},#{progress})")
    long insert(FilingApplyInfo filingApplyInfo);

    /**
     * 更新
     *
     * @param filingApplyInfo
     * @return
     */
    @Update("<script>" + "update filing_apply_info" + " set id=#{id}"
        + "<if test = 'applicant!=null'>,applicant = #{applicant}</if>" +
        "<if test = 'applyTime!=null'> , apply_time = #{applyTime}</if>"
        + "<if test = 'filingListStr!=null'> , filing_list_str = #{filingListStr}</if>" +
        "<if test = 'progress!=null'> , progress = #{progress}</if>" + " where project=#{project} "
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
     * 根据项目ID 主键  获取归档申请信息
     *
     * @param project
     * @return
     */
    @Select("select * from filing_apply_info where project=#{project}")
    List<FilingApplyInfo> findByProjectId(Long project);

    /**
     * 根据项目ID主键  获取归档申请信息文件信息
     *
     * @param project project.id
     * @return 归档文件信息
     */
    @Select("select * from project_item where project_id=#{project} and item_type = #{itemType}")
    List<FilingFileItem> findItemByProjectId(Long project, String itemType);

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
     * @param filingApplyInfo 归档信息查询条件
     * @return 归档信息
     */
    @Select("<script>" + "select * from filing_apply_info where 1=1 " +
        "<if test = 'projectId!=null'> and project = #{project}</if>"
        + "<if test = 'applicant!=null'> and applicant = #{applicant}</if>" +
        "<if test = 'applyTime!=null'> and apply_time = #{applyTime}</if>"
        + "<if test = 'filingListStr!=null'> and filing_list_str = #{filingListStr}</if>" +
        "<if test = 'progress!=null'> and progress = #{progress}</if></script>")
    List<FilingApplyInfo> findByParam(FilingApplyInfo filingApplyInfo);

    /**
     * 插入
     *
     * @param item
     * @return
     */
    @Insert("insert project_item (project_id,item_type,item_name,item_value) values(#{project},#{itemType},#{itemName},#{itemValue})")
    long insertFileItem(FilingFileItem item);

    /**
     * 删除归档申请文件
     *
     * @param project
     * @return
     */
    @Delete("delete from project_item  where project_id = #{project} and item_type = #{itemType}")
    int delFileItemByProjectId(Long project, String itemType);

}