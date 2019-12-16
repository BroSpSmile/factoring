/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: MiniMessage
 * @date Date : 2019年12月16日 21:01
 */
public class MiniMessage extends WechatMessage {

    /** 小程序消息 */
    @JSONField(name = "miniprogram_notice")
    private Notice notice;

    /**
     * Getter method for property <tt>notice</tt>.
     *
     * @return property value of notice
     */
    public Notice getNotice() {
        return notice;
    }

    /**
     * Setter method for property <tt>notice</tt>.
     *
     * @param notice value to be assigned to property  notice
     */
    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
