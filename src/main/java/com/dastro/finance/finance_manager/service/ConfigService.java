package com.dastro.finance.finance_manager.service;

import java.util.Optional;
import java.util.List;

import com.dastro.finance.finance_manager.entity.Config;

public interface ConfigService {
    public Optional<Config> getConfigById(Long id);

    public Optional<Config> getConfigByName(String confName);

    public Optional<Config> getConfigByNameAndCategory(String confName, String category);

    public List<Config> getConfigByCategory(String category);

    public List<Config> getAllConfig();

    public Config save(Config item);

    public void delete(Long id);
}
