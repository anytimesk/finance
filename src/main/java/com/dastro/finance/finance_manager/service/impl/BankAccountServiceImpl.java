package com.dastro.finance.finance_manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dastro.finance.finance_manager.entity.BankAccount;
import com.dastro.finance.finance_manager.repo.BankAccountRepository;
import com.dastro.finance.finance_manager.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService{

    @Autowired
    BankAccountRepository bankAccountRepo;

    public BankAccount geBankAccount(Long id) {
        return bankAccountRepo.findById(id).get();
    }

    public int setBankAccount(BankAccount bankAccount) {
        bankAccountRepo.save(bankAccount);
        return 1;
    }
}
