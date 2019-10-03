/**
 * 
 */
package com.smile.start.service.fund.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.smile.start.commons.LoggerUtils;
import com.smile.start.dao.FundItemDao;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.FundStatus;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.fund.FundTargetItem;
import com.smile.start.service.AbstractService;
import com.smile.start.service.fund.FundItemService;
import com.smile.start.service.fund.FundService;

/**
 * 实现
 * @author Administrator
 */
@Service
public class FundItemServiceImpl extends AbstractService implements FundItemService {

    /** fundItemDao */
    @Resource
    private FundItemDao fundItemDao;

    /** fundService */
    @Resource
    private FundService fundService;

    /**
     * @see com.smile.start.service.fund.FundItemService#save(com.smile.start.model.fund.FundTargetItem)
     */
    @Override
    public BaseResult save(FundTargetItem item) {
        long effect = fundItemDao.insert(item);
        LoggerUtils.info(logger, "新增附件effect={}", effect);
        return toResult(effect);
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#save(java.util.List)
     */
    @Override
    @Transactional
    public BaseResult save(List<FundTargetItem> items) {
        BaseResult result = new BaseResult();
        if (!CollectionUtils.isEmpty(items)) {
            FundTargetItem item = items.get(0);
            FundTarget target = new FundTarget();
            target.setId(item.getTarget().getId());
            target.setProjectStep(item.getItemType());
            result = fundService.modifyTarget(target);
        }
        if (result.isSuccess()) {
            for (FundTargetItem item : items) {
                result = this.save(item);
            }
        }
        return result;
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#delete(com.smile.start.model.fund.FundTargetItem)
     */
    @Override
    public BaseResult delete(FundTargetItem item) {
        int effect = fundItemDao.delete(item);
        return toResult(effect);
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#delete(com.smile.start.model.fund.FundTarget,
     *      com.smile.start.model.enums.FundStatus)
     */
    @Override
    public BaseResult delete(FundTarget target, FundStatus type) {
        int effect = fundItemDao.deleteByType(target.getId(), type);
        return toResult(effect);
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#getById(java.lang.Long)
     */
    @Override
    public FundTargetItem getById(Long id) {
        return fundItemDao.get(id);
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#getAll(com.smile.start.model.fund.FundTarget)
     */
    @Override
    public List<FundTargetItem> getAll(FundTarget target) {
        return fundItemDao.getByFund(target);
    }

    /**
     * @see com.smile.start.service.fund.FundItemService#getItemByType(com.smile.start.model.fund.FundTarget,
     *      com.smile.start.model.enums.FundStatus)
     */
    @Override
    public List<FundTargetItem> getItemByType(FundTarget target, FundStatus type) {
        FundTargetItem condition = new FundTargetItem();
        condition.setTarget(target);
        condition.setItemType(type);
        return fundItemDao.getByType(condition);
    }

}
