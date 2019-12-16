/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.wechat.model;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: TextMessage
 * @date Date : 2019年12月16日 20:57
 */
public class TextMessage extends WechatMessage {

    /** 文本 */
    private Text text;

    /**
     * Getter method for property <tt>text</tt>.
     *
     * @return property value of text
     */
    public Text getText() {
        return text;
    }

    /**
     * Setter method for property <tt>text</tt>.
     *
     * @param text value to be assigned to property  text
     */
    public void setText(Text text) {
        this.text = text;
    }
}
