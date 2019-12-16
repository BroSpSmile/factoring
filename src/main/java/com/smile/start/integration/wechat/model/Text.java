/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.wechat.model;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: Text
 * @date Date : 2019年12月16日 20:58
 */
public class Text {

    /** 企业应用的id */
    private long   agentid;

    /** 内容 */
    private String content;

    /**
     * Getter method for property <tt>agentid</tt>.
     *
     * @return property value of agentid
     */
    public long getAgentid() {
        return agentid;
    }

    /**
     * Setter method for property <tt>agentid</tt>.
     *
     * @param agentid value to be assigned to property  agentid
     */
    public void setAgentid(long agentid) {
        this.agentid = agentid;
    }

    /**
     * Getter method for property <tt>content</tt>.
     *
     * @return property value of content
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter method for property <tt>content</tt>.
     *
     * @param content value to be assigned to property  content
     */
    public void setContent(String content) {
        this.content = content;
    }
}
