package com.dastro.finance.finance_manager.service;

import com.dastro.finance.finance_manager.dto.OpenApiReqParam;


public interface OpenApiService {
    public String getOpenApiData(OpenApiReqParam reqParam);

    public String encodingString(String serviceKey);
    
    public String encodingString(String serviceKey, String encodeType);
}
