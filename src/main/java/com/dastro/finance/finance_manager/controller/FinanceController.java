package com.dastro.finance.finance_manager.controller;

import java.util.Arrays;
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
        
        List<String> companies = Arrays.asList(
            "삼성전자", "현대자동차", "SK하이닉스", "LG화학", "포스코", "삼성바이오로직스",
            "카카오", "네이버", "현대모비스", "LG전자", "SK텔레콤", "신한금융지주",
            "KB금융", "LG디스플레이", "삼성생명", "현대중공업", "CJ제일제당", "롯데케미칼",
            "삼성물산", "SK이노베이션", "하나금융지주", "한국전력", "삼성SDI", "현대건설",
            "아모레퍼시픽", "셀트리온", "현대글로비스", "강원랜드", "KT", "고려아연",
            "호텔신라", "SK네트웍스", "현대제철", "OCI", "삼성화재", "대한항공",
            "한진중공업", "아시아나항공", "두산중공업", "현대미포조선", "현대위아",
            "대우조선해양", "에쓰오일", "기아자동차", "두산밥캣", "대림산업", "LS산전",
            "GS건설", "롯데쇼핑", "삼성증권", "현대그린푸드", "한화", "LG생활건강",
            "현대상선", "현대카드", "LG유플러스", "SK머티리얼즈", "삼성전기", "SK케미칼",
            "미래에셋증권", "한미약품", "삼성SDS", "롯데푸드", "GS리테일", "NHN",
            "넷마블", "SK가스", "코웨이", "KT&G", "카카오뱅크", "카카오페이",
            "네이버웹툰", "유한양행", "GC녹십자", "한화생명", "하이트진로", "LG이노텍",
            "두산퓨얼셀", "CJ ENM", "CJ대한통운", "대상", "세아제강", "셀트리온제약",
            "SK바이오팜", "한화에어로스페이스", "삼성카드", "대웅제약", "효성", "제일기획",
            "삼성웰스토리", "DB손해보험", "NH투자증권", "한국콜마", "SPC삼립", "GS홈쇼핑",
            "한진칼", "하이브", "빅히트엔터테인먼트", "코오롱인더", "한섬", "현대홈쇼핑",
            "신세계인터내셔날", "LF", "GS리테일", "NHN한국사이버결제", "하나투어",
            "이마트", "BGF리테일", "CJ프레시웨이", "LS전선", "두산인프라코어",
            "현대엘리베이터", "현대로템", "효성중공업", "아모레G", "삼양식품", "한일시멘트",
            "한국타이어", "동원F&B", "F&F", "현대위아", "삼성엔지니어링", "한화케미칼",
            "코스맥스", "락앤락", "휠라홀딩스", "한세실업", "제이에스코퍼레이션",
            "신세계푸드", "LG하우시스", "한솔케미칼", "풍산", "LG상사", "현대리바트",
            "에스원", "코오롱글로벌", "대우건설", "한일현대시멘트", "삼양홀딩스",
            "삼성에스디에스", "대상홀딩스", "LG헬로비전", "더블유게임즈", "한화시스템",
            "오리온", "오뚜기", "농심", "삼양사", "아모레G", "유니드", "일진머티리얼즈",
            "LG화학", "한온시스템", "일진다이아", "효성티앤씨", "아이마켓코리아",
            "녹십자홀딩스", "롯데칠성", "삼성중공업", "한국조선해양", "삼성제약", 
            "영원무역", "세아베스틸", "삼성정밀화학", "빙그레", "풀무원", "한미반도체",
            "락앤락", "현대백화점", "신세계", "대웅", "이노션", "메리츠화재",
            "현대약품", "휴온스", "보령제약", "한미사이언스", "CJ헬로비전",
            "종근당", "바디프랜드", "유비케어", "아프리카TV", "원익IPS", "에코프로",
            "세아홀딩스", "삼양홀딩스", "한화투자증권", "현대에너지솔루션", "신성이엔지",
            "SBI인베스트먼트", "아이에스동서", "KH바텍", "신일전자", "동양물산기업",
            "엘앤에프", "삼진제약", "대신증권", "삼성출판사", "화승인더스트리",
            "메디톡스", "녹십자랩셀", "오리온", "아모레퍼시픽", "동서식품", "SK바이오사이언스"
        );
        model.addAttribute("companies", companies);

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

        List<Config> conf = configService.getConfigByCategory("STOCK_INFO");
        String[] values = new String[2];

        conf.forEach(val -> {
            if (val.getConfName().equals("CALLBACK_URL")) {
                values[0] = val.getConfValue();
            } else if (val.getConfName().equals("AUTH_KEY")) {
                values[1] = openApiService.encodingString(val.getConfValue());
            }
        } );
        log.debug("baseUri : {}", values[0]);
        log.debug("servicekey : {}", values[1]);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", values[1]);
        params.add("numOfRows", Integer.toString(numOfRows));
        params.add("resultType", "json");
        params.add("itmsNm", openApiService.encodingString(itmsNm,"UTF-8") );

        OpenApiReqParam reqParam = new OpenApiReqParam();
        reqParam.setEndPointURL(values[0]);
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
