/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.commons.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.aliyun.oss.OSSClient;

/**
 * Integration配置
 * @author smile.jing
 * @version $Id: IntegrationConfig.java, v 0.1 Jan 23, 2019 8:34:11 PM smile.jing Exp $
 */
@Configuration
public class IntegrationConfig {

    /** 域名 */
    @Value("${aliyun.oss.endpoint}")
    String endpoint;

    /** accessKeyId */
    @Value("${aliyun.oss.accessKeyId}")
    String accessKeyId;

    /** accessKeySecret */
    @Value("${aliyun.oss.accessKeySecret}")
    String accessKeySecret;

    /**
     * OSSClient bean
     * @return
     */
    @Bean
    public OSSClient getOSSClient() {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return client;
    }

    /**
     * RestTemplateClient
     * @return
     */
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}
