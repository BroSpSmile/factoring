/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.smile.start.model.common.GenId;

/**
 * 
 * @author smile.jing
 * @version $Id: GenIdDao.java, v 0.1 Jan 13, 2019 6:27:54 PM smile.jing Exp $
 */
@Mapper
public interface GenIdDao {

	/**
	 * 查询
	 * 
	 * @param kind
	 * @return
	 */
	@Select("select * from gen_id where kind = #{kind}")
	GenId getIdNoByKind(String kind);

	/**
	 * 更新ID
	 * 
	 * @param genId
	 * @return
	 */
	@Update("update gen_id set gen_id_no = #{genIdNo},update_time=#{updateTime} where id=#{id}")
	int updateIdNo(GenId genId);
}
