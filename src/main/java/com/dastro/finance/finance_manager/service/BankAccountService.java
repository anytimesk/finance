package com.dastro.finance.finance_manager.service;

import com.dastro.finance.finance_manager.entity.BankAccount;

public interface BankAccountService {
    public BankAccount geBankAccount(Long id);

    public int setBankAccount(BankAccount bankAccount);
}
