package com.dastro.finance.finance_manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dastro.finance.finance_manager.entity.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
