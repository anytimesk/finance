package com.dastro.finance.finance_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dastro.finance.finance_manager.entity.KRXListedData;
import com.dastro.finance.finance_manager.service.KRXListedDataService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class ElasticsearchController {

    @Autowired
    KRXListedDataService krxListedDataService;

    @GetMapping("/krx-list")
    public List<KRXListedData> getKrxList() {
        List<KRXListedData> data = krxListedDataService.findAll();

        for (KRXListedData krxListedData : data) {
            log.info("KRX Name : {}, Code : {}", krxListedData.getItmsNm(), krxListedData.getIsinCd());
        }

        return data;
    }
}