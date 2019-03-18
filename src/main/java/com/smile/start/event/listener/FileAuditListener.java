package com.smile.start.event.listener;

import com.smile.start.dao.FilingDao;
import com.smile.start.dao.ProjectDao;
import com.smile.start.event.AuditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 归档审核监听器
 *
 * @author smile.jing
 * @version $Id: FileAuditListener.java, v 0.1 Mar 12, 2019 9:50:44 PM smile.jing Exp $
 */
@Component
public class FileAuditListener implements AuditListener {

    /**
     * logger
     */
    public Logger      logger = LoggerFactory.getLogger(getClass());

    /**
     * 项目DAO
     */
    @Resource
    private ProjectDao projectDao;

    /**
     * 归档DAO
     */
    @Resource
    private FilingDao  filingDao;

    /**
     * @see com.smile.start.event.listener.AuditListener#listener(com.smile.start.event.AuditEvent)
     */
    @Override
    @EventListener
    public void listener(AuditEvent event) {
        //        

    }

}
