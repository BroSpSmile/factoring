/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.wechat.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.smile.start.commons.Constants;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: Notice
 * @date Date : 2019年12月16日 21:01
 */
public class Notice {

    /** 小程序appid */
    private String        appid    = Constants.MINI_PROGRAME_APP_ID;

    /** 小程序页面 */
    private String        page;

    /** 消息标题 */
    private String        title;

    /** 消息描述 */
    private String        description;

    /** 是否放大 */
    @JSONField(name = "emphasis_first_item")
    private boolean       emphasis = false;

    /** 消息内容 */
    @JSONField(name = "content_item")
    private List<Content> items    = Lists.newArrayList();

    /**
     * 添加消息内容
     * @param content
     */
    public void addItem(Content content) {
        this.items.add(content);
    }

    /**
     * Getter method for property <tt>appid</tt>.
     *
     * @return property value of appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * Setter method for property <tt>appid</tt>.
     *
     * @param appid value to be assigned to property  appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * Getter method for property <tt>page</tt>.
     *
     * @return property value of page
     */
    public String getPage() {
        return page;
    }

    /**
     * Setter method for property <tt>page</tt>.
     *
     * @param page value to be assigned to property  page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Getter method for property <tt>title</tt>.
     *
     * @return property value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for property <tt>title</tt>.
     *
     * @param title value to be assigned to property  title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for property <tt>description</tt>.
     *
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for property <tt>description</tt>.
     *
     * @param description value to be assigned to property  description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for property <tt>items</tt>.
     *
     * @return property value of items
     */
    public List<Content> getItems() {
        return items;
    }

    /**
     * Setter method for property <tt>items</tt>.
     *
     * @param items value to be assigned to property  items
     */
    public void setItems(List<Content> items) {
        this.items = items;
    }

    /**
     * Getter method for property <tt>emphasis</tt>.
     *
     * @return property value of emphasis
     */
    public boolean isEmphasis() {
        return emphasis;
    }

    /**
     * Setter method for property <tt>emphasis</tt>.
     *
     * @param emphasis value to be assigned to property  emphasis
     */
    public void setEmphasis(boolean emphasis) {
        this.emphasis = emphasis;
    }

}
