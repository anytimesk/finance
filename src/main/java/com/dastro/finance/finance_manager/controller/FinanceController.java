package com.dastro.finance.finance_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dastro.finance.finance_manager.dto.OpenApiReqParam;
import com.dastro.finance.finance_manager.entity.BankAccount;
import com.dastro.finance.finance_manager.service.BankAccountService;
import com.dastro.finance.finance_manager.service.ConfigService;
import com.dastro.finance.finance_manager.service.MemberService;
import com.dastro.finance.finance_manager.service.OpenApiService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
public class FinanceController {

    @Autowired
    MemberService memberService;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    ConfigService configService;

    @Autowired
    OpenApiService openApiService;

    @GetMapping(value = "/finance")
    public String financeMain() {
        
        log.info("Call Finance Main ");

        return "finance";
    }

    @GetMapping(value = "/finance/account")
    public String getBankAccount() {
        BankAccount bankAccount = bankAccountService.geBankAccount(1L);
        log.info("geBankAccount Test " + bankAccount.getId() + ", " + bankAccount.getBankName());

        return bankAccount.getBankName();
    }

    @GetMapping(value = "/finance/getStockPriceInfo")
    @ResponseBody
    public String getStockPriceInfo(@RequestParam String itmsNm, @RequestParam int pageNo) {
        String baseUri = configService.getConfigByName("CALLBACK_URL").get().getConfValue();
        String serviceKeyDecode = configService.getConfigByName("AUTH_KEY").get().getConfValue();
        String serviceKey = openApiService.encodingString(serviceKeyDecode);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", serviceKey);
        params.add("numOfRows", Integer.toString(2));
        params.add("resultType", "json");
        params.add("itmsNm", openApiService.encodingString(itmsNm,"UTF-8") );

        OpenApiReqParam reqParam = new OpenApiReqParam();
        reqParam.setEndPointURL(baseUri);
        reqParam.setDetailService("/getStockPriceInfo");
        reqParam.setQueryParam(params);

        Object response = openApiService.getOpenApiData(reqParam);
        String jsonStr = response.toString();

        log.info("Response = {}", jsonStr);
        
        return jsonStr;
    }
 
    @GetMapping("/finance/test2")
    public String getTest2() {
        String baseUri = configService.getConfigByName("CALLBACK_URL").get().getConfValue();
        String serviceKeyDecode = configService.getConfigByName("AUTH_KEY").get().getConfValue();
        String serviceKey = openApiService.encodingString(serviceKeyDecode);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", serviceKey);
        params.add("numOfRows", Integer.toString(2));
        params.add("resultType", "json");
        params.add("itmsNm", openApiService.encodingString("삼성전자","UTF-8") );

        OpenApiReqParam reqParam = new OpenApiReqParam();
        reqParam.setEndPointURL(baseUri);
        reqParam.setDetailService("/getStockPriceInfo");
        reqParam.setQueryParam(params);

        Object response = openApiService.getOpenApiData(reqParam);
        String test = response.toString();
        log.info("Response = {}", test);
        
        return test;
    }

    
}
