package com.dastro.finance.finance_manager.controller;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Log4j2
@Controller
public class MemberController {

    @GetMapping("/")
	public String index(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request, Model model) {
        // 사용자가 로그인한 경우 사용자 이름을 모델에 추가
        if (principal != null) {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("csrfToken", csrfToken.getToken());
            model.addAttribute("csrfParameterName", csrfToken.getParameterName());

            log.info(String.format("CSRF Token : %s, csrfParameterName : %s", csrfToken.getToken().toString(), csrfToken.getParameterName()));

            String name = principal.getAttribute("name"); // 사용자 이름 가져오기
            model.addAttribute("name", name);
            model.addAttribute("isLoggedIn", true); // 로그인 상태
        } else {
            model.addAttribute("isLoggedIn", false); // 로그인 상태
        }

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