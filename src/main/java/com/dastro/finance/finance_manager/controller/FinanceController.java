package com.dastro.finance.finance_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dastro.finance.finance_manager.entity.BankAccount;
import com.dastro.finance.finance_manager.service.BankAccountService;
import com.dastro.finance.finance_manager.service.MemberService;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
public class FinanceController {

    @Autowired
    MemberService memberService;

    @Autowired
    BankAccountService bankAccountService;

    @RequestMapping(value = "/bankaccount", method=RequestMethod.GET)
    public String getBankAccount() {
        BankAccount bankAccount = bankAccountService.geBankAccount(1L);
        log.info("geBankAccount Test " + bankAccount.getId() + ", " + bankAccount.getBankName());

        return bankAccount.getBankName();
    }
    
}