/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: WechatMessage
 * @date Date : 2019年12月16日 20:51
 */
public class WechatMessage {

    /** 接收消息的成员 */
    private String      touser;

    /** 接收消息的部门 */
    private String      toparty;

    /** 消息类型 */
    private MessageType msgtype;

    /** 是否是保密消息 */
    private long        safe                 = 0;

    /** 是否开启id转译 */
    @JSONField(name = "enable_id_trans")
    private long        enableIdTrans        = 0;

    /** 是否重复消息检查的时间间隔 */
    @JSONField(name = "enable_duplicate_check")
    private long        enableDuplicateCheck = 0;

    /**
     * Getter method for property <tt>touser</tt>.
     *
     * @return property value of touser
     */
    public String getTouser() {
        return touser;
    }

    /**
     * Setter method for property <tt>touser</tt>.
     *
     * @param touser value to be assigned to property  touser
     */
    public void setTouser(String touser) {
        this.touser = touser;
    }

    /**
     * Getter method for property <tt>toparty</tt>.
     *
     * @return property value of toparty
     */
    public String getToparty() {
        return toparty;
    }

    /**
     * Setter method for property <tt>toparty</tt>.
     *
     * @param toparty value to be assigned to property  toparty
     */
    public void setToparty(String toparty) {
        this.toparty = toparty;
    }

    /**
     * Getter method for property <tt>msgtype</tt>.
     *
     * @return property value of msgtype
     */
    public MessageType getMsgtype() {
        return msgtype;
    }

    /**
     * Setter method for property <tt>msgtype</tt>.
     *
     * @param msgtype value to be assigned to property  msgtype
     */
    public void setMsgtype(MessageType msgtype) {
        this.msgtype = msgtype;
    }

    /**
     * Getter method for property <tt>safe</tt>.
     *
     * @return property value of safe
     */
    public long getSafe() {
        return safe;
    }

    /**
     * Setter method for property <tt>safe</tt>.
     *
     * @param safe value to be assigned to property  safe
     */
    public void setSafe(long safe) {
        this.safe = safe;
    }

    /**
     * Getter method for property <tt>enableIdTrans</tt>.
     *
     * @return property value of enableIdTrans
     */
    public long getEnableIdTrans() {
        return enableIdTrans;
    }

    /**
     * Setter method for property <tt>enableIdTrans</tt>.
     *
     * @param enableIdTrans value to be assigned to property  enableIdTrans
     */
    public void setEnableIdTrans(long enableIdTrans) {
        this.enableIdTrans = enableIdTrans;
    }

    /**
     * Getter method for property <tt>enableDuplicateCheck</tt>.
     *
     * @return property value of enableDuplicateCheck
     */
    public long getEnableDuplicateCheck() {
        return enableDuplicateCheck;
    }

    /**
     * Setter method for property <tt>enableDuplicateCheck</tt>.
     *
     * @param enableDuplicateCheck value to be assigned to property  enableDuplicateCheck
     */
    public void setEnableDuplicateCheck(long enableDuplicateCheck) {
        this.enableDuplicateCheck = enableDuplicateCheck;
    }
}
