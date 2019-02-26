/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author smile.jing
 * @version $Id: App.java, v 0.1 Jan 8, 2019 10:13:20 PM smile.jing Exp $
 */
public abstract class AbstractService {
	/** formatter */
	protected SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

	/** logger */
	public Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * formatTime
	 * 
	 * @return
	 */
	protected String formatTime() {
		Date now = new Date();
		return formatter.format(now);
	}
}
