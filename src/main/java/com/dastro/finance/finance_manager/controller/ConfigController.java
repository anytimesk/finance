package com.dastro.finance.finance_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dastro.finance.finance_manager.entity.Config;
import com.dastro.finance.finance_manager.service.ConfigService;
import com.dastro.finance.finance_manager.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;


@Log4j2
@Controller
public class ConfigController {

    @Autowired
    MemberService memberService;

    @Autowired
    ConfigService configService;

    @GetMapping(value = "/conf")
    public String conf(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request, Model model) {
        
        memberService.loginCheckAndInsertModel(principal, request, model);

        List<Config> configInfos = configService.getAllConfig();
        model.addAttribute("confs", configInfos);

        configInfos.forEach(config-> {
            log.debug("id : {}, key : {}, value : {}, category : {}, comment : {}",
            config.getId(), config.getConfName(), config.getConfValue(), config.getCategory(), config.getComment());
        });

        return "conf";
    }
    
}
