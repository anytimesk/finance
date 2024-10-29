package com.dastro.finance.finance_manager.service;

import com.dastro.finance.finance_manager.dto.OpenApiReqParam;


public interface OpenApiService {
    public String getOpenApiData(OpenApiReqParam reqParam);

    /**
     * 
     * targetString을 UTF-8방식으로 Encoding한다.
     * @param targetString Encoding 대상 원본 {@code String}
     * @return 인코딩한 문자열
     */
    public String encodingString(String targetString);
    
    /**
     * 
     * targetString을 encodeType 방식으로 Encoding한다.
     * @param targetString Encoding 대상 원본 {@code String}
     * @param encodeType Encoding 방식
     * @return 인코딩한 문자열
     */
    public String encodingString(String targetString, String encodeType);
}
