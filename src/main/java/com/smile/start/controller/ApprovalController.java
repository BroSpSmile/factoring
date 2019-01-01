/**
 * 
 */
package com.smile.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jingyongxiang
 *
 */
@Controller
public class ApprovalController extends BaseController {

	/**
	 * 项目立项页
	 * 
	 * @return
	 */
	@GetMapping("/approval")
	public String index() {
		return "project/approval";
	}
}
