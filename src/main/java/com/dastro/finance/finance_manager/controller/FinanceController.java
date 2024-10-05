package com.dastro.finance.finance_manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dastro.finance.finance_manager.entity.BankAccount;
import com.dastro.finance.finance_manager.service.BankAccountService;
import com.dastro.finance.finance_manager.service.MemberService;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Log4j2
@Controller
public class FinanceController {

    @Autowired
    MemberService memberService;

    @Autowired
    BankAccountService bankAccountService;

    @GetMapping("/")
	public String index(@AuthenticationPrincipal OAuth2User principal, Model model) {
        // 사용자가 로그인한 경우 사용자 이름을 모델에 추가
        if (principal != null) {
            String name = principal.getAttribute("name"); // 사용자 이름 가져오기
            model.addAttribute("name", name);
        }
        
        return "index";
	}

    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal, Model model) {
        Map<String, Object> userInfo = new HashMap<>();
        
        userInfo.put("name", principal.getAttribute("name"));
        userInfo.put("email", principal.getAttribute("email"));

        return userInfo;
    }
    

    @RequestMapping(value = "/bankaccount", method=RequestMethod.GET)
    public String getBankAccount() {
        BankAccount bankAccount = bankAccountService.geBankAccount(1L);
        log.info("geBankAccount Test " + bankAccount.getId() + ", " + bankAccount.getBankName());

        return bankAccount.getBankName();
    }
    
}