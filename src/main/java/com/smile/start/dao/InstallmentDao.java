/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import java.util.List;

import com.smile.start.model.project.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

/**
 * InstallmentDao
 * @author smile.jing
 * @version $Id: InstallmentDao.java, v 0.1 Mar 6, 2019 8:51:36 PM smile.jing Exp $
 */
@Mapper
public interface InstallmentDao {
    /**
     * 插入
     * @param installment
     * @return
     */
    @Insert("insert into project_installment (detail,type,amount,installment_date,paied,invoiced)"
            + "values(#{detail.id},#{type},#{amount},#{installmentDate},#{paied},#{invoiced})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(Installment installment);

    /**
     * 更新
     * @param installment
     * @return
     */
    @Update("<script>" + "update project_installment set id = #{id}" + "<if test = 'detail!=null and detail.id!=null'>,detail = #{detail.id}</if>"
            + "<if test = 'type!=null'>,type = #{type}</if>" + "<if test = 'amount!=null'>,amount = #{amount}</if>"
            + "<if test = 'installmentDate!=null'>,installment_date = #{installmentDate}</if>" + "<if test = 'paied!=null'>,paied = #{paied}</if>"
            + "<if test = 'invoiced!=null'>,invoiced = #{invoiced}</if>" + "where id = #{id}" + "</script>")
    int update(Installment installment);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from project_installment where id = #{id}")
    int delete(Long id);
    
    /**
     * deleteByType
     * @param detailId
     * @param type
     * @return
     */
    @Delete("delete from project_installment where detail = #{detail.id} and type = #{type}")
    int deleteByType(Installment installment);

    /**
     * 查询
     * @param detailId
     * @return
     */
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "detail", property = "detail.id") })
    @Select("select * from project_installment where detail = #{detailId}")
    List<Installment> queryByDetail(Long detailId);


    //add by xioutman  分期详情、附件操作
    /**
     * 根据project_id和分期类型查询分期信息列表
     * @param detailId
     * @return
     */
    @Results(id = "getMapByType", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "detail", property = "detail.id") })
    @Select("select * from project_installment where detail = #{detailId} and type =#{type} ")
    List<Installment> queryByProjectAndType(Long detailId,String type);

    /**
     * 插入分期附件
     * @param item
     * @return
     */
    @Insert("insert installment_item (installment_id,item_name,item_value,attach_type) values(#{installmentId},#{itemName},#{itemValue},#{attachType})")
    long insertInstallmentItem(InstallmentItem item);

    /**
     * 插入分期详情
     * @param installmentDetail
     * @return
     */
    @Insert("insert into installment_detail (type,installment_id,bankInfo_id,detail_amount,detail_date)"
        + "values(#{type},#{installmentId},#{bankInfoId},#{detailAmount} ,#{detailDate} )")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insertInstallmentDetail(InstallmentDetail installmentDetail);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from installment_detail where id = #{id}")
    int deleteInstallmentDetail(Long id);

    /**
     * 更新
     * @param installmentDetail
     * @return
     */
    @Update("<script>" + "update installment_detail set id = #{id}"
        + "<if test = 'type!=null'>,type = #{type}</if>"
        + "<if test = 'bankInfo!=null'>,bankInfo_id = #{bankInfoId}</if>"
        + "<if test = 'detailDate!=null'>,detail_date = #{detailDate}</if>"
        + "<if test = 'detailAmount!=null'>,detail_amount = #{detailAmount}</if>"
        + "where installment_id = #{installmentId}" + "</script>")
    int updateInstallmentDetail(InstallmentDetail installmentDetail);

    /**
     * 插入分期详情附件
     * @param item
     * @return
     */
    @Insert("insert installment_detail_item (installment_detail_id,item_name,item_value,attach_type) values(#{installmentDetailId},#{itemName},#{itemValue},#{attachType})")
    long insertInstallmentDetailItem(InstallmentDetailItem item);

    /**
     * 根据ID获取项目
     * @param installmentId
     * @return
     */
    @Results(id = "getInstallmentDetailMap", value = { @Result(id = true, column = "id", property = "id") })
    @Select("select * from installment_detail where installment_id = #{installmentId}")
    List<InstallmentDetail> getInstallmentDetail(Long installmentId);

    /**
     * 获取
     * @param installment
     * @return
     */
    @Select("select * from installment_item where installment_id = #{id}")
    InstallmentItem getInstallmentItem(Installment installment);
    /**
     * 获取
     * @param installmentDetail
     * @return
     */
    @Select("select * from installment_detail_item where installment_detail_id = #{id}")
    InstallmentDetailItem getInstallmentDetailItem(InstallmentDetail installmentDetail);

    /**
     *
     * @param item
     * @return
     */
    @Delete("delete from installment_item where installment_id = #{installmentId}")
    int deleteInstallmentItem(InstallmentItem item);

    /**
     *
     * @param item
     * @return
     */
    @Delete("delete from installment_detail_item where id = #{id}")
    int deleteInstallmentDetailItem(InstallmentDetailItem item);

}
