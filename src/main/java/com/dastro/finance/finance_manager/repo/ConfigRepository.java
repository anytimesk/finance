package com.dastro.finance.finance_manager.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dastro.finance.finance_manager.entity.Config;
import java.util.List;


@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {
    Optional<Config> findByConfName(String confName);

    Optional<Config> findByConfNameAndCategory(String confName, String category);

    List<Config> findByCategory(String category);
}
