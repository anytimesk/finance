package com.dastro.finance.finance_manager.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dastro.finance.finance_manager.entity.Config;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {
    Optional<Config> findByConfName(String confName);
}
