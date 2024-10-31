package com.dastro.finance.finance_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.dastro.finance.finance_manager.entity.Config;
import com.dastro.finance.finance_manager.service.ConfigService;
import com.dastro.finance.finance_manager.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;




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

    @PostMapping("/conf/add")
    @ResponseBody
    public ResponseEntity<String> addNewConfig(@RequestBody Config config) {
        log.info("ConfName {}", config.getConfName());
        log.info("ConfValue {}", config.getConfValue());
        log.info("Category {}", config.getCategory());
        log.info("Comment {}", config.getComment());
        configService.save(config);

        return ResponseEntity.ok("Config saved successfully");
    }
    
    @DeleteMapping("/conf/delete/{id}")
    public ResponseEntity<String> deleteConfig(@PathVariable Long id) {
        configService.delete(id);

        return ResponseEntity.ok("Config deleted successfully"); // 성공 메시지를 JSON 형태로 반환
    }
}
