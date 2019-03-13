/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.event.listener;

import com.smile.start.event.AuditEvent;

/**
 * 审核监听接口
 * @author smile.jing
 * @version $Id: AuditListener.java, v 0.1 Mar 12, 2019 9:15:57 PM smile.jing Exp $
 */
public interface AuditListener {
    /**
     * 监听方法
     * @param event
     */
    void listener(AuditEvent event);
}
