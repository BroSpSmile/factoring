package com.smile.start.event.listener;

import com.smile.start.dao.FilingDao;
import com.smile.start.dao.InstallmentDao;
import com.smile.start.dao.ProjectDao;
import com.smile.start.event.AuditEvent;
import com.smile.start.event.InstallmentEvent;
import com.smile.start.service.finance.FinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 分期操作监听器
 *
 * @author smile.jing
 * @version $Id: FileAuditListener.java, v 0.1 Mar 12, 2019 9:50:44 PM smile.jing Exp $
 */
@Component
public class InstallmentListener {

    /**
     * logger
     */
    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 项目DAO
     */
    @Resource
    private ProjectDao projectDao;

    @Resource
    private InstallmentDao installmentDao;

    @Resource
    private FinanceService financeService;

    /**
     * @see AuditListener#listener(AuditEvent)
     */
    @EventListener
    public void listener(InstallmentEvent event) {

    }

}
