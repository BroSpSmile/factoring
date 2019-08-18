/**
 * 
 */
package com.smile.start.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.smile.start.model.enums.FundStatus;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.fund.FundTargetItem;

/**
 * DAL
 * @author smile.jing
 * @version $Id: FundItemDao.java, v 0.1 2019年8月18日 下午9:36:03 smile.jing Exp $
 */
@Mapper
public interface FundItemDao {

    /**
     * 插入
     * 
     * @param item
     * @return
     */
    @Insert("insert fund_item (fund_id,item_type,item_name,item_value) values(#{target.id},#{itemType},#{itemName},#{itemValue})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    long insert(FundTargetItem item);

    /**
     * 删除
     * 
     * @param item
     * @return
     */
    @Delete("delete from fund_item where id = #{id}")
    int delete(FundTargetItem item);

    /**
     * 删除
     * 
     * @param fundId
     * @param itemType
     * @return
     */
    @Delete("delete from fund_item where fund_id = #{fundId} and item_type = #{itemType}")
    int deleteByType(Long fundId, FundStatus itemType);

    /**
     * 
     * @param id
     * @return
     */
    @Results(id = "getMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "target", property = "target.id") })
    @Select("select * from fund_item where id = #{id}")
    FundTargetItem get(Long id);

    /**
     * 
     * @param id
     * @return
     */
    @Results(id = "getByFund", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "target", property = "target.id") })
    @Select("select * from fund_item where fund_id = #{target.id} and item_type = #{itemType}")
    List<FundTargetItem> getByType(FundTargetItem condition);

    /**
     * 
     * @param target
     * @return
     */
    @Results(id = "getByFundMap", value = { @Result(id = true, column = "id", property = "id"), @Result(column = "target", property = "target.id") })
    @Select("select * from fund_item where target = #{id}")
    List<FundTargetItem> getByFund(FundTarget target);
}
