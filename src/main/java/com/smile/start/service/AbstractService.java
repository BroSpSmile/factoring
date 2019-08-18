/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smile.start.model.base.BaseResult;

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
     * 
     * @param effect
     * @return
     */
    protected BaseResult toResult(long effect) {
        if (effect > 0) {
            return new BaseResult();
        } else {
            BaseResult result = new BaseResult();
            result.setSuccess(false);
            result.setErrorCode("VP00011001");
            result.setErrorMessage("新增失败,请重试!");
            return result;
        }
    }

    /**
     * 
     * @param effect
     * @return
     */
    protected BaseResult toResult(int effect) {
        if (effect > 0) {
            return new BaseResult();
        } else {
            BaseResult result = new BaseResult();
            result.setSuccess(false);
            result.setErrorCode("VP00011001");
            result.setErrorMessage("新增失败,请重试!");
            return result;
        }
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

}
