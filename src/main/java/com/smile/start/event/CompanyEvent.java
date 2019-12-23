/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.event;

import java.util.Map;

import com.smile.start.model.fund.FundTarget;
import org.springframework.context.ApplicationEvent;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: CompanyEvent
 * @date Date : 2019年12月23日 17:47
 */
public class CompanyEvent extends ApplicationEvent {

    /** 项目 */
    private FundTarget          target;

    /** */
    private Map<String, String> def;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CompanyEvent(Object source, FundTarget target, Map<String, String> def) {
        super(source);
        this.target = target;
        this.def = def;
    }

    /**
     * Getter method for property <tt>target</tt>.
     *
     * @return property value of target
     */
    public FundTarget getTarget() {
        return target;
    }

    /**
     * Setter method for property <tt>target</tt>.
     *
     * @param target value to be assigned to property  target
     */
    public void setTarget(FundTarget target) {
        this.target = target;
    }

    /**
     * Getter method for property <tt>def</tt>.
     *
     * @return property value of def
     */
    public Map<String, String> getDef() {
        return def;
    }

    /**
     * Setter method for property <tt>def</tt>.
     *
     * @param def value to be assigned to property  def
     */
    public void setDef(Map<String, String> def) {
        this.def = def;
    }
}
