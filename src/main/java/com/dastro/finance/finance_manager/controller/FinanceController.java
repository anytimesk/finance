package com.dastro.finance.finance_manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.dastro.finance.finance_manager.entity.Config;
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
        
        // data.go.kr에서 상장회사 리스트 가져옴(Sample이라 100개만)
        HashMap<String, String> data = getConfigData("ISIN_CODE");
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", data.get("AUTH_KEY"));
        params.add("resultType", "json");
        params.add("numOfRows", Integer.toString(50));

        OpenApiReqParam reqParam = new OpenApiReqParam();
        reqParam.setEndPointURL(data.get("CALLBACK_URL"));
        reqParam.setDetailService("/getItemInfo");
        reqParam.setQueryParam(params);

        String response = openApiService.getOpenApiData(reqParam);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode items = null;

        try {
            //long startTime = System.currentTimeMillis();
            JsonNode jsonNode = objectMapper.readTree(response);
            items = jsonNode.path("response").path("body").path("items").path("item");
            List<String> companyList = new ArrayList<>();

            items.forEach(item -> companyList.add(item.get("itmsNm").asText()));
            
            model.addAttribute("companies", companyList);
            //long stopTime = System.currentTimeMillis();
            //log.info("Json Parsing Time {}", (stopTime - startTime));
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage());
        } catch (Exception e) {
            log.error("기타 오류: {}", e.toString());
        }

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
    public ResponseEntity<JsonNode> getStockPriceInfo(@RequestParam String itmsNm, @RequestParam int pageNo, @RequestParam int numOfRows) {

        HashMap<String, String> data = getConfigData("STOCK_INFO");
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", data.get("AUTH_KEY"));
        params.add("numOfRows", Integer.toString(numOfRows));
        params.add("resultType", "json");
        params.add("itmsNm", openApiService.encodingString(itmsNm,"UTF-8") );

        OpenApiReqParam reqParam = new OpenApiReqParam();
        reqParam.setEndPointURL(data.get("CALLBACK_URL"));
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


    private HashMap<String, String> getConfigData(String category) {
        List<Config> confs = configService.getConfigByCategory(category);
        HashMap<String, String> data = new HashMap<>();

        confs.forEach(conf -> {
            if (conf.getConfName().equals("CALLBACK_URL")) {
                data.put(conf.getConfName(), conf.getConfValue());
            } else if (conf.getConfName().equals("AUTH_KEY")) {
                data.put(conf.getConfName(), openApiService.encodingString(conf.getConfValue()));
            }
        });
        log.info("baseUri : {}", data.get("CALLBACK_URL"));
        log.info("servicekey : {}", data.get("AUTH_KEY"));

        return data;
    }

    
}
