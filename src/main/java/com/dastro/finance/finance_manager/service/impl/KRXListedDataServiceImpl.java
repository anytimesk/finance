package com.dastro.finance.finance_manager.service.impl;

import com.dastro.finance.finance_manager.entity.KRXListedData;
import com.dastro.finance.finance_manager.repo.KRXListedDataRepository;
import com.dastro.finance.finance_manager.service.KRXListedDataService;

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

    public List<KRXListedData> findAll() {
        Iterable<KRXListedData> iterable = KRXListRepo.findAll();

        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}