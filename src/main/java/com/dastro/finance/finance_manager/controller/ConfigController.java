package com.dastro.finance.finance_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dastro.finance.finance_manager.entity.Config;
import com.dastro.finance.finance_manager.service.ConfigService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;


@Log4j2
@Controller
public class ConfigController {

    @Autowired
    ConfigService configService;

    @GetMapping(value = "/conf")
    public String conf(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request, Model model) {
        // 사용자가 로그인한 경우 사용자 이름을 모델에 추가
        if (principal != null) {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("csrfToken", csrfToken.getToken());
            model.addAttribute("csrfParameterName", csrfToken.getParameterName());

            String name = principal.getAttribute("name"); // 사용자 이름 가져오기
            model.addAttribute("name", name);
            model.addAttribute("isLoggedIn", true); // 로그인 상태
        } else {
            model.addAttribute("isLoggedIn", false); // 로그인 상태
        }

        List<Config> configInfos = configService.getAllConfig();
        model.addAttribute("confs", configInfos);

        configInfos.forEach(config-> {
            log.debug("id : {}, key : {}, value : {}, category : {}, comment : {}",
            config.getId(), config.getConfName(), config.getConfValue(), config.getCategory(), config.getComment());
        });

        return "conf";
    }
    
}
