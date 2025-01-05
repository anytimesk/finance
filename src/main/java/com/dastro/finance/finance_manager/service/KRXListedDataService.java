package com.dastro.finance.finance_manager.service;

import java.util.List;

import com.dastro.finance.finance_manager.entity.KRXListedData;
import com.fasterxml.jackson.databind.JsonNode;

public interface KRXListedDataService {

    public void save(KRXListedData KRXListedData);

    public void saveAll(JsonNode items) throws Exception;

    public List<KRXListedData> findAll();
}
