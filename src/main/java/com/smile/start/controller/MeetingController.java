/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author smile.jing
 * @version $Id: MeetingController.java, v 0.1 Jan 9, 2019 5:15:06 PM smile.jing
 *          Exp $
 */
@Controller
public class MeetingController extends BaseController {

	/**
	 * index
	 * 
	 * @return
	 */
	@RequestMapping("/meeting")
	public String index() {
		return "project/meeting";
	}
}
