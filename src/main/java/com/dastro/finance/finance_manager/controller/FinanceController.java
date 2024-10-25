package com.dastro.finance.finance_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
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
    public String financeMain(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request, Model model) {
        
        memberService.loginCheckAndInsertModel(principal, request, model);

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
    public ResponseEntity<JsonNode> getStockPriceInfo(@RequestParam String itmsNm, @RequestParam int pageNo) {
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

        String response = openApiService.getOpenApiData(reqParam);
        log.debug("response : {}", response);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode items = null;

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            items = jsonNode.path("response").path("body").path("items").path("item");
            log.debug("items string : {}", items.toString());

            if (items.isMissingNode()) {
                log.warn("items 노드를 찾을 수 없습니다.");
                return ResponseEntity.notFound().build(); // 404 Not Found 응답
            }
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null); // 400 Bad Request 응답
        } catch (Exception e) {
            log.error("기타 오류: {}", e.toString());
            return ResponseEntity.internalServerError().body(null); // 500 Internal Server Error 응답
        }

        return ResponseEntity.ok(items);
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
