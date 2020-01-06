/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.smile.start.commons.ErrorMessageEnum;
import com.smile.start.exception.ServiceException;
import com.smile.start.exception.error.ServiceErrorFactory;
import com.smile.start.model.auth.User;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.SingleResult;

/**
 * 
 * @author smile.jing
 * @version $Id: App.java, v 0.1 Jan 8, 2019 10:13:20 PM smile.jing Exp $
 */
public abstract class AbstractService {
    /** formatter */
    protected SimpleDateFormat formatter      = new SimpleDateFormat("yyyy");

    /** formatter */
    protected SimpleDateFormat monthFormatter = new SimpleDateFormat("yyyy-MM");

    /** logger */
    public Logger              logger         = LoggerFactory.getLogger(getClass());

    /**
     * formatTime
     * 
     * @return
     */
    protected String formatTime() {
        Date now = new Date();
        return formatter.format(now);
    }

    /**
     * 转换标准结果
     * @param effect 影响行
     * @return BaseResult
     */
    protected BaseResult toResult(long effect) {
        return toResult(effect > 0);
    }

    /**
     * 转换标准结果
     * @param effect 影响行
     * @return BaseResult
     */
    protected BaseResult toResult(int effect) {
        return toResult(effect > 0);
    }

    /**
     * 转换标准结果
     * @param success 结果
     * @return BaseResult
     */
    protected BaseResult toResult(boolean success) {
        if (success) {
            return new BaseResult();
        } else {
            return new SingleResult(defaultErrorResult());
        }
    }

    /**
     * 转换标准结果
     * @param success 结果
     * @param result data
     * @return SingleResult
     */
    protected <T> SingleResult<T> toResult(boolean success, T result) {
        if (success) {
            return new SingleResult<T>(result);
        } else {
            return new SingleResult(defaultErrorResult());
        }
    }

    /**
     * 默认错误对象
     * @return BaseResult
     */
    private BaseResult defaultErrorResult() {
        BaseResult result = new BaseResult();
        ServiceException exception = new ServiceException(ServiceErrorFactory.getInstance().createError(ErrorMessageEnum.DEFUALT));
        result.setSuccess(false);
        result.setErrorCode(exception.getCode());
        result.setErrorMessage(exception.getMessage());
        return result;
    }

    /**
     * 获取当年第一天
     * @return
     */
    @SuppressWarnings("deprecation")
    protected Date getYear() {
        Date now = new Date();
        now.setMonth(0);
        now.setDate(1);
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        return now;
    }

    /**
     * 获取当月最后一天
     * @param month
     * @return
     */
    protected Date getMonthLastDay(String month) {
        try {
            Date date = monthFormatter.parse(month);
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
            return ca.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 用户转换
     * @param userStr
     * @return
     */
    protected List<User> toUser(String userStr) {
        String[] ids = userStr.split(",");
        List<User> users = Lists.newArrayListWithCapacity(ids.length);
        for (String id : ids) {
            User user = new User();
            user.setId(Long.valueOf(id));
            users.add(user);
        }
        return users;
    }

    protected String toStr(List<User> users) {
        String userStr = StringUtils.EMPTY;
        if (null == users) {
            return userStr;
        }
        for (User user : users) {
            userStr += "," + user.getId();
        }
        if (StringUtils.isNotBlank(userStr)) {
            userStr = userStr.replaceFirst(",", "");
        }
        return userStr;
    }
}
