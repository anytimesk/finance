package com.dastro.finance.finance_manager.service.impl;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dastro.finance.finance_manager.entity.Config;
import com.dastro.finance.finance_manager.repo.ConfigRepository;
import com.dastro.finance.finance_manager.service.ConfigService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {
    
    @Autowired
    ConfigRepository configRepo;

    public Optional<Config> getConfigById(Long id) {
        return configRepo.findById(id);
    }

    public Optional<Config> getConfigByName(String confName) {
        return configRepo.findByConfName(confName);
    }

    public List<Config> getAllConfig() {
        return configRepo.findAll();
    }

    @Transactional
    public Config save(Config item) {
        return configRepo.save(item);
    }

    @Transactional
    public void delete(Long id) {
        configRepo.deleteById(id);
    }
}
