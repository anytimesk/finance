package com.dastro.finance.finance_manager.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "krx_listed_data")
public class KRXListedData {
    @Id
    private String srtnCd; // _id 필드와 매핑

    private String basDt;
    private String isinCd;
    private String mrktCtg;
    private String itmsNm;
    private String crno;
    private String corpNm;
}
