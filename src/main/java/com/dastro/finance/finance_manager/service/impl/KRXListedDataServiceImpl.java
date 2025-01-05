package com.dastro.finance.finance_manager.service.impl;

import com.dastro.finance.finance_manager.entity.KRXListedData;
import com.dastro.finance.finance_manager.repo.KRXListedDataRepository;
import com.dastro.finance.finance_manager.service.KRXListedDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class KRXListedDataServiceImpl implements KRXListedDataService {
    @Autowired
    private final KRXListedDataRepository KRXListRepo;

    public KRXListedDataServiceImpl(KRXListedDataRepository KRXListRepo) {
        this.KRXListRepo = KRXListRepo;
    }

    public void save(KRXListedData KRXListedData) {
        KRXListRepo.save(KRXListedData);
    }

    public void saveAll(JsonNode items) throws Exception {
        List<KRXListedData> listData = StreamSupport.stream(items.spliterator(), false)
                .map(item -> {
                    try {
                        return new ObjectMapper().treeToValue(item, KRXListedData.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .peek(data -> log
                        .debug(() -> String.format("KRXListedData: %s %s", data.getIsinCd(), data.getItmsNm())))
                .collect(Collectors.toList());

        if (!listData.isEmpty()) {
            KRXListRepo.saveAll(listData);
        } else {
            log.info("No data to save");
        }
    }

    public List<KRXListedData> findAll() {
        Iterable<KRXListedData> iterable = KRXListRepo.findAll();

        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}