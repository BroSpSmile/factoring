/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.smile.start.model.project.FactoringDetail;

/**
 * 保理项目明细Dao
 * @author smile.jing
 * @version $Id: FactoringDetailDao.java, v 0.1 Feb 24, 2019 7:32:41 PM smile.jing Exp $
 */
@Mapper
public interface FactoringDetailDao {

    /**
     * 新增项目
     * @param detail
     * @return
     */
    @Insert("insert into factoring_detail " + "(project_id,creditor,debtor,assignee,receivable,drop_amount,duration,remittance_day,real_back_day,back_amount,"
            + "money_back,total_factoring_fee,factoring_stages,return_rate,account_day,invoicing,paied,remark) "
            + "values(#{project.id},#{creditor},#{debtor},#{assignee},#{receivable},#{dropAmount},#{duration},"
            + "#{remittanceDay},#{realBackDay},#{backAmount},#{moneyBack},#{totalFactoringFee},#{factoringStages},"
            + "#{returnRate},#{accountDay},#{invoicing},#{paied},#{remark})")
    long insert(FactoringDetail detail);
}
