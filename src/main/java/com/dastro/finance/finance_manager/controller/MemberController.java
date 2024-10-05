package com.dastro.finance.finance_manager.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;


@Log4j2
@Controller
public class MemberController {

    @GetMapping("/login")
    public String login() {
        
        log.info("Call Log In Page");

        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        
        log.info("Call Log Out Page");

        return "logout";
    }

}
