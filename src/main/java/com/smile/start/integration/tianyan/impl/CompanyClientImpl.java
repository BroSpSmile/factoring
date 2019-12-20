/**
 * com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.smile.start.integration.tianyan.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.TypeReference;
import com.smile.start.commons.FastJsonUtils;
import com.smile.start.commons.LoggerUtils;
import com.smile.start.integration.tianyan.CompanyClient;
import com.smile.start.integration.tianyan.model.CompanyInfo;
import com.smile.start.integration.tianyan.model.TianyanResult;

/**
 * 实现
 * @author : Tiny.Jing
 * @version V1.0
 * @Description: CompanyClientImpl
 * @date Date : 2019年12月18日 19:19
 */
@Service
public class CompanyClientImpl implements CompanyClient {

    /** logger */
    private Logger        logger = LoggerFactory.getLogger(getClass());

    /** http */
    @Resource
    private RestTemplate  restTemplate;

    /** 地址 */
    @Value("${tianyan.query.url}")
    private String        url;

    /** token */
    @Value("${tianyan.query.token}")
    private String        token;

    private DecimalFormat df     = new DecimalFormat("#.00");

    /**
     * 查询企业信息
     *
     * @param id
     * @param companyName
     * @return
     */
    @Override
    public TianyanResult<CompanyInfo> query(String id, String companyName) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (StringUtils.isNotBlank(id)) {
            builder.queryParam("id", id);
        }
        if (StringUtils.isNotBlank(companyName)) {
            builder.queryParam("name", companyName);
        }
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
        LoggerUtils.info(logger, "调用天眼查返回结果:{}", responseEntity.getBody());
        TianyanResult<CompanyInfo> result = FastJsonUtils.fromJSONString(responseEntity.getBody(), new TypeReference<TianyanResult<CompanyInfo>>() {
        });
        if (null != result.getResult()) {
            CompanyInfo info = result.getResult();
            String regCapital = info.getRegCapital().replace("万人民币", "");
            BigDecimal capital = new BigDecimal(regCapital).multiply(new BigDecimal(10000));
            info.setRegCapital(df.format(capital));
        }
        return result;
    }
}
