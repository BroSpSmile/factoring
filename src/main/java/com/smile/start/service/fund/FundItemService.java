/**
 *
 */
package com.smile.start.service.fund;

import java.util.List;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.FundStatus;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.fund.FundTargetItem;

/**
 *
 * @author Administrator
 */
public interface FundItemService {

    /**
     * 保存附件
     * @param item
     * @return
     */
    BaseResult save(FundTargetItem item);

    /**
     * 保存附件
     * @param items
     * @return
     */
    BaseResult save(List<FundTargetItem> items);

    /**
     * 刪除附件
     * @param item
     * @return
     */
    BaseResult delete(FundTargetItem item);

    /**
     * 根据状态删除附件
     * @param target
     * @param  type
     * @return
     */
    BaseResult delete(FundTarget target, FundStatus type);

    /**
     * 根据附件ID获取附件信息
     * @param id
     * @return
     */
    FundTargetItem getById(Long id);

    /**
     * 获取全部附件
     * @param target
     * @return
     */
    List<FundTargetItem> getAll(FundTarget target);

    /**
     * 根据状态获取附件
     * @param target
     * @param type
     * @return
     */
    List<FundTargetItem> getItemByType(FundTarget target, FundStatus type);
}
