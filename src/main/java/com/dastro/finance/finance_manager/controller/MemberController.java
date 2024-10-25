package com.dastro.finance.finance_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dastro.finance.finance_manager.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Log4j2
@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/")
	public String index(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request, Model model) {
        
        memberService.loginCheckAndInsertModel(principal, request, model);

        return "index";
	}

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        
        log.info(String.format("Call Log In Page, CsrfToken class getName : %s", CsrfToken.class.getName()));

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.addAttribute("csrfToken", csrfToken.getToken());
        model.addAttribute("csrfParameterName", csrfToken.getParameterName());
        log.info(String.format("CSRF Token : %s, csrfParameterName : %s", csrfToken.getToken().toString(), csrfToken.getParameterName()));

        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        
        log.info("Call Log Out Page");
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/oauth2/logout";
    }

    @PostMapping("/oauth2/logout")
    public String oauth2Logout() {
        // Google 로그아웃 URL
        String googleLogoutUrl = "https://accounts.google.com/Logout"; // Google 로그아웃 URL
        return "redirect:" + googleLogoutUrl;
    }
}
