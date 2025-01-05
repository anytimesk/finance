package com.dastro.finance.finance_manager.service.impl;

import java.util.Optional;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dastro.finance.finance_manager.entity.Config;
import com.dastro.finance.finance_manager.repo.ConfigRepository;
import com.dastro.finance.finance_manager.service.ConfigService;
import com.dastro.finance.finance_manager.service.OpenApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository configRepo;

    @Autowired
    OpenApiService openApiService;

    public Optional<Config> getConfigById(Long id) {
        return configRepo.findById(id);
    }

    public Optional<Config> getConfigByName(String confName) {
        return configRepo.findByConfName(confName);
    }

    public Optional<Config> getConfigByNameAndCategory(String confName, String category) {
        return configRepo.findByConfNameAndCategory(confName, category);
    }

    public HashMap<String, String> getConfigData(String category) {
        List<Config> list = configRepo.findByCategory(category);
        HashMap<String, String> data = new HashMap<>();

        list.forEach(conf -> {
            if (conf.getConfName().equals("CALLBACK_URL")) {
                data.put(conf.getConfName(), conf.getConfValue());
            } else if (conf.getConfName().equals("AUTH_KEY")) {
                data.put(conf.getConfName(), openApiService.encodingString(conf.getConfValue()));
            }
        });
        log.debug("baseUri : {}", data.get("CALLBACK_URL"));
        log.debug("servicekey : {}", data.get("AUTH_KEY"));

        return data;
    }

    public List<Config> getConfigByCategory(String category) {
        return configRepo.findByCategory(category);
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
