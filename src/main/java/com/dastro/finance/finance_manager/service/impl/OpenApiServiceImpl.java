package com.dastro.finance.finance_manager.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.dastro.finance.finance_manager.dto.OpenApiReqParam;
import com.dastro.finance.finance_manager.service.OpenApiService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OpenApiServiceImpl implements OpenApiService{

    public String getOpenApiData(OpenApiReqParam reqParam) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(reqParam.getEndPointURL()); 
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient = WebClient.builder()
                                .uriBuilderFactory(factory)
                                .baseUrl(reqParam.getEndPointURL())
                                .build();
        log.debug("getOpenApiData getEndPointURL : {}", reqParam.getEndPointURL());
        log.debug("getOpenApiData getDetailService : {}", reqParam.getDetailService());
        log.debug("getOpenApiData serviceKey : {}", reqParam.getQueryParam().get("serviceKey"));

        
        // 호출 시간 측정한 결과 데이터 양과 관계없이 1.4 ~ 2.0초 사이
        //log.info("Call webClient Req Param : {}", reqParam.getQueryParam().get("itmsNm"));
        //long startTime = System.currentTimeMillis();
        String ret = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path(reqParam.getDetailService())
                    .queryParams(reqParam.getQueryParam())
                    .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //long stopTime = System.currentTimeMillis();
        //log.info("webClient Req Param : {}. Get Response Time : {}", reqParam.getQueryParam().get("itmsNm"), (stopTime - startTime));

        return ret;
    }

    public String encodingString(String targetString) {
        return encodingString(targetString, "UTF-8");
    }

    public String encodingString(String targetString, String encodeType) {
        String encodingString = "";
        
        try {
            encodingString = URLEncoder.encode(targetString, encodeType);
        } catch(UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException {}", e.toString());
        } catch(Exception e) {
            log.error(e.toString());
        }
        return encodingString;
    }

}
