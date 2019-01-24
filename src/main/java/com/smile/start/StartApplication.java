package com.smile.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 * 
 * @author Smile
 * @version $Id: StartApplication.java, v 0.1 2018年9月25日 下午3:00:35 Smile Exp $
 */
@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class StartApplication {

	/**
	 * 启动文件
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}
}
