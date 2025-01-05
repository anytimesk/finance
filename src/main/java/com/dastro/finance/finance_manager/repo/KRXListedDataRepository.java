package com.dastro.finance.finance_manager.repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.dastro.finance.finance_manager.entity.KRXListedData;

@EnableElasticsearchRepositories
public interface KRXListedDataRepository extends ElasticsearchRepository<KRXListedData, String> {
    public List<KRXListedData> findByItmsNm(String itmsNm);

}
