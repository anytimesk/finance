package com.dastro.finance.finance_manager.dto;

import org.springframework.util.LinkedMultiValueMap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OpenApiReqParam {

    private String endPointURL;

    private String detailService;

    private LinkedMultiValueMap<String, String> queryParam;
}
